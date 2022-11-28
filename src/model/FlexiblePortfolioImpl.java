package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * This class creates flexible portfolios and lets user to ,
 * create new portfolio, modify existing portfolios,  get cost basis till a specific date,
 * get value of the portfolio on a specific date,
 * get whole composition of the portfolio.
 */
public class FlexiblePortfolioImpl extends PortfolioImpl {

  /**
   * Make a transaction of purchase or sell using the input values date,commission, no of stock,
   * and tickr symbol.
   *
   * @param date       is the date on which purchase or sale is made
   * @param commission is input commission for a transaction
   * @param noofstocks is number stock bought or sold
   * @param tickr      is company tickr symbol for which transaction needs to be done
   * @return a json object entry that needs to be added to the portfolio
   */
  public JSONObject makeTransactionRecord(String date, float commission, float noofstocks,
                                          String tickr) {
    JSONObject record = new JSONObject();
    record.put("date", date);
    record.put("commission_fee", commission);
    record.put("no_of_stocks", noofstocks);
    record.put("stock_price", getCallPriceDate(date, tickr));
    return record;
  }

  /**
   * Read the Portfolio for the given path for the portfolio.
   *
   * @param path portfolio absolute path
   * @return a json object that consists of all the entries in the input portfolio path
   */
  public JSONObject readPortfolio(String path) {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(path)) {
      Object parseObj = parser.parse(reader);
      return (JSONObject) parseObj;
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Save a portfolio.
   *
   * @param path portfolio path where json needs to be saved
   * @param data portfolio json object
   */
  public void savePortfolio(String path, JSONObject data) {
    try {
      FileWriter file = new FileWriter(path);
      file.write(data.toJSONString());
      file.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * modify the json.
   *
   * @param fees      is the commision fees
   * @param date      date on which sale is to be made
   * @param tickr     company tickr symbol
   * @param portfolio the json object of the portfolio
   */
  @Override
  public JSONObject modifyJson(float fees, float numOfStocks, String date, String tickr, JSONObject portfolio) {
    Float commissionFee = fees;
    // if ticker already exists in portfolio append to it else add new entry if it does not exist.
    if (portfolio.containsKey(tickr)) {
      JSONArray tickrRecord = (JSONArray) portfolio.get(tickr);
      JSONObject newTransactionRecord = makeTransactionRecord(date, commissionFee, numOfStocks,
              tickr);
      tickrRecord.add(newTransactionRecord);
      portfolio.put(tickr, tickrRecord);
    } else {
      JSONObject newTransactionRecord = makeTransactionRecord(date, commissionFee, numOfStocks,
              tickr);
      JSONArray newTickrRecord = new JSONArray();
      newTickrRecord.add(newTransactionRecord);
      portfolio.put(tickr, newTickrRecord);
    }
    return portfolio;
  }

  /**
   * Get the cost basis of a portfolio till a date.
   *
   * @param pfPath input portfolio path
   * @param date   input string date
   * @return cost basis value
   */
  @Override
  public float getCostBasis(String pfPath, String date) throws java.text.ParseException {
    float finalCostBasis = 0;
    JSONObject portfolio = readPortfolio(pfPath);
    for (Object tickrsym : portfolio.keySet()) {
      JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
      for (int i = 0; i < arrayObj.size(); i++) {
        JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
        if (checkIfBeforeDate(date, (String) tickrRecord.get("date")) ||
                date.equals(tickrRecord.get("date"))) {
          double commision_fee = (double) tickrRecord.get("commission_fee");
          double stocksVal = (double) tickrRecord.get("no_of_stocks");
          float intStocks = Float.valueOf(String.valueOf(stocksVal));
          finalCostBasis += commision_fee;
          double stockPrice = (double) tickrRecord.get("stock_price");
          if (intStocks > 0) {
            finalCostBasis += (stockPrice * intStocks);
          }
        }
      }
    }
    return finalCostBasis;
  }

  /**
   * check if the tickr symbol exists in the portfolio.
   *
   * @param pfPath path for the portfolio
   * @param tickr  is company tickr symbol
   * @return true if tickr exists else false
   */
  public boolean ifTickrInPf(String pfPath, String tickr) {
    JSONObject portfolio = readPortfolio(pfPath);
    return portfolio.containsKey(tickr);
  }

  /**
   * Get portfolio value for a given date.
   */
  @Override
  public float portfolioValueDate(String rootDir, String fileName,
                                  String date) throws java.text.ParseException {
    float finalVal = 0;
    int totStocks = 0;
    JSONObject portfolio = readPortfolio(rootDir + fileName + ".json");
    for (Object tickrsym : portfolio.keySet()) {
      ApiKey apiObj = new ApiKey((String) tickrsym);
      JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
      totStocks = getTotalStocks(arrayObj, date);
      finalVal += totStocks * apiObj.callPriceDate(date);
    }
    return finalVal;
  }

  private int getTotalStocks(JSONArray arrayObj, String date) throws java.text.ParseException {
    int totStocks = 0;
    for (Object o : arrayObj) {
      JSONObject tickrRecord = (JSONObject) o;
      totStocks += getStocks(tickrRecord, date);
    }
    return totStocks;
  }

  private Float getStocks(JSONObject entry, String date) throws java.text.ParseException {
    float valStocks = (float) 0;
    if (checkIfBeforeDate(date, (String) entry.get("date"))
            || date.equals(entry.get("date"))) {
      Double val = (Double) entry.get("no_of_stocks");
      valStocks = Float.valueOf(String.valueOf(val));
    }
    return valStocks;
  }


  /**
   * check if the number of stocks entered to be sold is valid or not.
   *
   * @param numStocks is number stocks to be sold
   * @param tickr     is tickr symbol for which stocks need to be sold
   * @param date      is the date on which transaction is made
   * @return true if sale can be made else false
   */

  public boolean checkValidSell(String pfPath, int numStocks, String tickr, String date)
          throws java.text.ParseException {
    int totStocks = 0;
    JSONObject portfolio = readPortfolio(pfPath);
    if (portfolio.containsKey(tickr)) {
      JSONArray tickr_record = (JSONArray) portfolio.get(tickr);
      boolean flag = false;
      for (Object o : tickr_record) {
        JSONObject record = (JSONObject) o;
        String dateCheck = (String) record.get("date");
        if (checkIfBeforeDate(date, dateCheck) || dateCheck.equals(date)) {
          flag = true;
        }
      }
      if (!flag) {
        return false;
      }
      totStocks = getTotalStocks(tickr_record, date);
      if (totStocks == 0) {
        return false;
      }
      if (totStocks >= numStocks) {
        return true;
      }
    }
    return false;
  }


  private boolean checkIfBeforeDate(String givenDate, String toBeChecked)
          throws java.text.ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    Date newdate = formatter.parse(givenDate);
    Date checkrecentDate = formatter.parse(toBeChecked);
    return checkrecentDate.before(newdate);
  }

  /**
   * Check if the given input date is prior to the given input date for a given tickr.
   *
   * @param date   is input date
   * @param tickr  is company tickr symbol
   * @param pfPath is portfolio path
   * @return true not prior else false
   */
  @Override
  public boolean checkPriorDate(String date, String tickr, String pfPath)
          throws java.text.ParseException {
    JSONObject portfolio = readPortfolio(pfPath);
    if (portfolio.containsKey(tickr)) {
      JSONArray tickrrecord = (JSONArray) portfolio.get(tickr);
      JSONObject obj = (JSONObject) tickrrecord.get(tickrrecord.size() - 1);
      String recentDate = (String) obj.get("date");
      if (recentDate.equals(date) || checkIfBeforeDate(date, recentDate)) {
        return true;
      }
    }
    return false;
  }


  /**
   * check if the tickr symbol exists in a json array.
   *
   * @param tickrList of type JSONObject
   * @param tickr     company tickrsymbol
   * @return false if not found else return true
   */
  public boolean checkTickrJSONArray(JSONObject tickrList, String tickr) {
    for (Object ticksSym : tickrList.keySet()) {
      String tickerSymbol = (String) ticksSym;
      if (tickerSymbol.equals(tickr)) {
        return true;
      }
    }
    return false;
  }

  /**
   * check if date is prior to the most recent date entry.
   *
   * @param date         input date entry
   * @param existingDate most recent date
   * @return true if prior else false
   */
  public boolean checkDateinJSONObject(String date, String existingDate) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    try {
      Date newdate = formatter.parse(date);
      Date checkrecentDate = formatter.parse(existingDate);
      if (checkrecentDate.before(newdate)) {
        return true;
      }
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  /**
   * Get the price of a stock on a date.
   *
   * @param date        input date on which portfolio value is needed
   * @param tickrSymbol company tickr symbol
   * @return float value of the price
   */
  public float getCallPriceDate(String date, String tickrSymbol) {
    ApiKey apiObj = new ApiKey(tickrSymbol);
    return apiObj.callPriceDate(date);
  }

  private ArrayList<Float> getValuesListHelper(String rootDir, String pfName,
                                               ArrayList<String> datesList)
          throws java.text.ParseException {
    ArrayList<Float> getValues = new ArrayList<>();
    for (String date : datesList) {
      getValues.add(portfolioValueDate(rootDir, pfName, date));
    }
    return getValues;
  }

  private ArrayList<Float> getValuesList(String rootDir, String pfName, String date1, String date2,
                                         int differenceDays, String checkLabel)
          throws java.text.ParseException {
    ArrayList<Float> getValues = new ArrayList<>();
    ArrayList<String> datesList = new ArrayList<>();
    if (checkLabel.equals("day")) {
      datesList = getDatesDisplay(date1, date2, differenceDays);
      getValues = getValuesListHelper(rootDir, pfName, datesList);
    } else if (checkLabel.equals("month")) {
      ArrayList<String> monYearList = getDatesDisplay(date1, date2, differenceDays);
      for (int i = 0; i < monYearList.size() - 1; i++) {
        String date = monYearList.get(i);
        String[] stringDate = date.split(" ");
        Date dateNew = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(stringDate[0]);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateNew);
        int month = cal.get(Calendar.MONTH) + 1;
        LocalDate finalDate = YearMonth.of(Integer.valueOf(stringDate[1]), month).atEndOfMonth();
        datesList.add(finalDate.toString());
      }
      datesList.add(date2);
      getValues = getValuesListHelper(rootDir, pfName, datesList);
    } else if (checkLabel.equals("year")) {
      ArrayList<String> yearList = getDatesDisplay(date1, date2, differenceDays);
      for (int i = 0; i < yearList.size() - 1; i++) {
        String date = yearList.get(i);
        datesList.add(date + "-12-31");
      }
      datesList.add(date2);
      getValues = getValuesListHelper(rootDir, pfName, datesList);
    }
    return getValues;
  }

  /**
   * Get the values of the portfolio for a particular day, month, year.
   *
   * @param date1          first input date
   * @param date2          second input date
   * @param differenceDays number of days difference between date1 and date2
   * @param pfName         portfolio name for which performance need to be plotted
   * @param rootDir        root directory of portfolio
   * @return array list of values of the portfolio
   */
  @Override
  public ArrayList<Float> getValuesPortfolio(String rootDir, String pfName,
                                             String date1, String date2, int differenceDays)
          throws java.text.ParseException {
    ArrayList<Float> getValues;
    // if the difference is more than 5 and <= 30 print day wise.
    // if the difference is more than 30 days and <=150 days print for every 5 days.
    if (differenceDays >= 5 && differenceDays <= 150) {
      getValues = getValuesList(rootDir, pfName, date1, date2, differenceDays, "day");
    }

    // if the difference is more than 150 days and 900 days print in months.
    // if difference is more than 900 days and less than equal to 1800 days print on 3 mon basis.
    else if (differenceDays > 150 && differenceDays <= 1800) {
      getValues = getValuesList(rootDir, pfName, date1, date2, differenceDays, "month");
    }

    // if difference is more than 1800 days then yearly.
    else {
      getValues = getValuesList(rootDir, pfName, date1, date2, differenceDays, "year");
    }
    return getValues;
  }

  private ArrayList<String> listOfDates(String date1, String date2, String labelCheck,
                                        int increment) {
    ArrayList<String> totalDates = new ArrayList<>();
    String[] stringDate1 = date1.split("-");
    String[] stringDate2 = date2.split("-");
    DateTimeFormatter formatter;
    LocalDate start = LocalDate.parse(date1);
    LocalDate end = LocalDate.parse(date2);
    if (labelCheck.equals("day")) {
      while (!start.isAfter(end)) {
        totalDates.add(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        start = start.plusDays(increment);
      }
    } else if (labelCheck.equals("month")) {
      formatter = DateTimeFormatter.ofPattern("yyyy-MM", Locale.ENGLISH);
      YearMonth startDate = YearMonth.parse(stringDate1[0] + "-" + stringDate1[1], formatter);
      YearMonth endDate = YearMonth.parse(stringDate2[0] + "-" + stringDate2[1], formatter);
      while (startDate.isBefore(endDate)) {
        String stringDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String[] dateString = stringDate.split("-");
        int valMon = Integer.parseInt(dateString[1]);
        totalDates.add(new DateFormatSymbols().getShortMonths()[valMon - 1] + " " + dateString[0]);
        startDate = startDate.plusMonths(increment);
      }
    } else if (labelCheck.equals("year")) {
      formatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH);
      Year startDate = Year.parse(stringDate1[0], formatter);
      Year endDate = Year.parse(stringDate2[0], formatter);
      while (startDate.isBefore(endDate)) {
        String stringDate = startDate.format(DateTimeFormatter.ofPattern("yyyy"));
        String[] dateString = stringDate.split("-");
        totalDates.add(dateString[0]);
        startDate = startDate.plusYears(increment);
      }
    }
    return totalDates;
  }

  /**
   * List of days or months or years that needs to be displayed while checking the performance.
   *
   * @param date1          first input date
   * @param date2          second input date
   * @param differenceDays number of days difference between date1 and date2
   * @return array list of dates to be printed for recording performance of the portfolio
   */
  @Override
  public ArrayList<String> getDatesDisplay(String date1, String date2, int differenceDays) {
    ArrayList<String> totalDates;
    // if the difference is more than 5 and <= 30 print day wise.
    if (differenceDays >= 5 && differenceDays <= 30) {
      totalDates = listOfDates(date1, date2, "day", 1);
    }
    // if the difference is more than 30 days and <=150 days print for every 5 days.
    else if (differenceDays > 30 && differenceDays <= 150) {
      totalDates = listOfDates(date1, date2, "day", 5);
    }
    // if the difference is more than 150 days and 900 days print in months.
    else if (differenceDays > 150 && differenceDays <= 900) {
      totalDates = listOfDates(date1, date2, "month", 1);
    }
    // if difference is more than 900 days and less than equal to 1800 days print on 3 mon basis.
    else if (differenceDays > 900 && differenceDays <= 1800) {
      totalDates = listOfDates(date1, date2, "month", 3);
    }
    // if difference is more than 1800 days then yearly.
    else {
      totalDates = listOfDates(date1, date2, "year", 1);
    }
    return totalDates;
  }

  /**
   * Compute the scale for the portfolio.
   *
   * @param values list of values obtained on a given date or month or year
   * @return scale of type float
   */
  @Override
  public float getScale(ArrayList<Float> values) {
    // get the highest and lowest value
    // find the difference between them.
    // divide this value with every point.
    float maxVal = Collections.max(values);
    float minVal = Collections.min(values);
    float diffValues = maxVal - minVal;
    // put a limit of maximum 50 asterisks per line.
    if (diffValues > 50) {
      diffValues = (diffValues) / 50;
    }
    return diffValues;

  }

  /**
   * Get the number of points to be pointed in form of asterisks.
   * for getting the performance of portfolio.
   *
   * @param scaleVal scale of the performance portfolio
   * @param values   list of performance portfolio values
   * @return list of asterisks that needs to be printed
   */
  @Override
  public ArrayList<String> getPoints(float scaleVal, ArrayList<Float> values) {
    ArrayList<String> points = new ArrayList<>();
    for (int i = 0; i < values.size(); i++) {
      int numAsterisks = (int) ((values.get(i) - Collections.min(values)) / scaleVal);
      StringBuilder asteriskString = new StringBuilder();
      for (int j = 0; j < numAsterisks; j++) {
        asteriskString.append("*");
      }
      points.add(asteriskString.toString());
    }
    return points;
  }

  /**
   * Difference between dates.
   *
   * @param date1 first input date
   * @param date2 second input date
   * @return difference value between dates date1 and date2
   */
  @Override
  public int checkDifference(String date1, String date2) {
    int difference = 0;
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
      Date newdate1 = formatter.parse(date1);
      Date newdate2 = formatter.parse(date2);
      Long diff = ChronoUnit.DAYS.between(newdate1.toInstant(), newdate2.toInstant());
      difference = diff.intValue();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return difference;
  }

  /**
   * Check if input date1 is prior to the date2.
   *
   * @param date1 first input date
   * @param date2 second input date
   * @return true if date1 is prior to the date2 else return false
   */
  @Override
  public boolean checkValidDates(String date1, String date2) {
    try {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
      Date newdate1 = formatter.parse(date1);
      Date newdate2 = formatter.parse(date2);
      return newdate1.before(newdate2);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}

