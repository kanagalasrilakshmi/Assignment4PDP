package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FlexiblePortfolioImpl extends PortfolioImpl {

  /**
   * modify the json.
   *
   * @param fees   is the commision fees
   * @param num    num stocks willing to sell
   * @param date   date on which sale is to be made
   * @param tickr  company tickr symbol
   * @param pfPath path for the location of the portfolio
   */
  @Override
  public void modifyJson(String fees, int num, String date, String tickr, String pfPath) {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      // add new entry if the tickr symbol does not exist.
      if (!checkTickrExists(pfPath, tickr)) {
        JSONObject newEntry = new JSONObject();
        // make an api call
        ApiKey apiObj = new ApiKey(tickr);
        float stockPrice = apiObj.callPriceDate(date);
        // make account of cost basis.
        float newcostBasis = Float.valueOf(fees) + (stockPrice * num);
        newEntry.put("Date", date);
        newEntry.put("Commission Fee", Float.valueOf(fees));
        newEntry.put("NumStocks Sold or Purchased", Integer.valueOf(num));
        newEntry.put("Stock Price", Float.valueOf(stockPrice));
        newEntry.put("TotalStocks", Integer.valueOf(num));
        newEntry.put("CostBasis", Float.valueOf(newcostBasis));
        JSONArray arrayObj = new JSONArray();
        arrayObj.add(newEntry);
        portfolio.put(tickr, arrayObj);
        try {
          FileWriter file = new FileWriter(pfPath);
          file.write(portfolio.toJSONString());
          file.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      // if exists modify it.
      else {
        JSONArray tickrRecord = (JSONArray) portfolio.get(tickr);
        JSONObject lastEntry = (JSONObject) tickrRecord.get(tickrRecord.size() - 1);
        Long valStocks = (Long) lastEntry.get("TotalStocks");
        int totStocks = valStocks.intValue();
        int newtotStocks = totStocks + num;
        // get stock price.
        double valStockPrice = (double) lastEntry.get("Stock Price");
        float stockPrice = (float) valStockPrice;
        // make account of cost basis.
        double valCostbasis = (double) lastEntry.get("CostBasis");
        float costbasis = (float) valCostbasis;
        float newcostBasis = Float.valueOf(fees) + costbasis;
        if (num > 0) {
          newcostBasis += (stockPrice * num);
        }
        // add them to the json.
        JSONObject newEntry = new JSONObject();
        newEntry.put("Date", date);
        newEntry.put("Commission Fee", Float.valueOf(fees));
        newEntry.put("NumStocks Sold or Purchased", Integer.valueOf(num));
        newEntry.put("Stock Price", Float.valueOf(stockPrice));
        newEntry.put("TotalStocks", Integer.valueOf(newtotStocks));
        newEntry.put("CostBasis", Float.valueOf(newcostBasis));
        // add back to the json.
        tickrRecord.add(newEntry);
        portfolio.put(tickr, tickrRecord);
        // write back to the json.
        try {
          FileWriter file = new FileWriter(pfPath);
          file.write(portfolio.toJSONString());
          file.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Get the cost basis of a portfolio till a date.
   *
   * @param pfPath input portfolio path
   * @param date   input string date
   * @return cost basis value
   */
  @Override
  public float getCostBasis(String pfPath, String date) {
    float finalCostBasis = 0;
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      for (Object tickrsym : portfolio.keySet()) {
        JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
        for (int i = arrayObj.size() - 1; i >= 0; i--) {
          JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
          if (checkIfBeforeDate(date, (String) tickrRecord.get("Date"))) {
            double presentCostBasis = (double) tickrRecord.get("CostBasis");
            finalCostBasis += presentCostBasis;
            break;
          }
        }
      }
      return finalCostBasis;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return 0;
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    } catch (ParseException e) {
      e.printStackTrace();
      return 0;
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * create a json portfolio.
   *
   * @param pfPath   portfolio path where json needs to be saved
   * @param addEntry add json entry
   */
  @Override
  public void createPortfolioJson(String pfPath, JSONObject addEntry) {
    try {
      FileWriter file = new FileWriter(pfPath);
      file.write(addEntry.toJSONString());
      file.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Get portfolio value for a given date.
   */
  @Override
  public float portfolioValueDate(String rootDir, String fileName,
                                  String date) throws FileNotFoundException {
    float finalVal = 0;
    // load the json file.
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(rootDir + fileName + ".json")) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      for (Object tickrsym : portfolio.keySet()) {
        ApiKey apiObj = new ApiKey((String) tickrsym);
        JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
        for (int i = arrayObj.size() - 1; i >= 0; i--) {
          JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
          // check if the date is prior.
          // get the stocks.
          // multiply by the stock value and number of total stocks on that day.
          if (checkIfBeforeDate(date, (String) tickrRecord.get("Date"))) {
            Long val = (Long) tickrRecord.get("TotalStocks");
            int totStocks = val.intValue();
            finalVal += totStocks * apiObj.callPriceDate(date);
            break;
          }
        }
      }

      // return that value.
      return finalVal;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return 0;
    } catch (IOException e) {
      e.printStackTrace();
      return 0;
    } catch (ParseException e) {
      e.printStackTrace();
      return 0;
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * check if the number of stocks entered to be sold is valid or not.
   *
   * @param numStocks is number stocks to be sold
   * @param tickr     is tickr symbol for which stocks need to be sold
   * @return true if sale can be made else false
   */
  @Override
  public boolean checkValidSell(String pfPath, int numStocks, String tickr) {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      if (this.checkTickrExists(pfPath, tickr)) {
        JSONArray tickr_record = (JSONArray) portfolio.get(tickr);
        // go to the last entry.
        JSONObject lastEntry = (JSONObject) tickr_record.get(tickr_record.size() - 1);
        Long val = (Long) lastEntry.get("TotalStocks");
        int valStocks = val.intValue();
        if (valStocks >= numStocks) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }

  /**
   * check if the tickr symbol exists in the portfolio.
   *
   * @param pfPath path for the portfolio
   * @param tickr  is company tickr symbol
   * @return true if tickr exists else false
   */
  @Override
  public boolean checkTickrExists(String pfPath, String tickr) {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      for (Object tickrsym : portfolio.keySet()) {
        String checkTickr = (String) tickrsym;
        if (checkTickr.equals(tickr)) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }


  private boolean checkIfBeforeDate(String givenDate, String toBeChecked)
          throws java.text.ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
    Date newdate = formatter.parse(givenDate);
    Date checkrecentDate = formatter.parse(toBeChecked);
    if (checkrecentDate.before(newdate)) {
      return true;
    }
    return false;
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
  public boolean checkPriorDate(String date, String tickr, String pfPath) {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      if (this.checkTickrExists(pfPath, tickr)) {
        JSONArray tickrrecord = (JSONArray) portfolio.get(tickr);
        JSONObject obj = (JSONObject) tickrrecord.get(tickrrecord.size() - 1);
        String recentDate = (String) obj.get("Date");
        if (recentDate.equals(date) || checkIfBeforeDate(date, recentDate)) {
          return true;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    } catch (ParseException e) {
      e.printStackTrace();
      return false;
    } catch (java.text.ParseException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  /**
   * check if the given number either an integer or a decimal number
   *
   * @param num input string parameter
   * @return true if valid else return false
   */
  @Override
  public boolean checkValidNum(String num) {
    if (checkValidInteger(num)) {
      return true;
    }
    // check if it is a float.
    // float should have only one . character and rest all as numbers.
    int countDot = 0;
    for (int i = 0; i < num.length(); i++) {
      if (num.charAt(i) == '.') {
        countDot += 1;
        if (countDot > 1) {
          return false;
        }
      } else if (!Character.isDigit(num.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * check if the tickr symbol exists in a json array
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
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
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

  /**
   * Check if the given output folder has any portfolios.
   *
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  @Override
  public boolean checkOutputFolder(String rootDir) {
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for (File file : filesList) {
      if (file.isFile()) {
        if (file.getName().contains(".json")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Get the values of the porfolio for a particular day, month, year.
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
          throws java.text.ParseException, FileNotFoundException {
    ArrayList<Float> getValues = new ArrayList<>();
    // if the difference is more than 5 and <= 30 print day wise.
    // if the difference is more than 30 days and <=150 days print for every 5 days.
    if (differenceDays >= 5 && differenceDays <= 150) {
      ArrayList<String> datesList = getDatesDisplay(date1, date2, differenceDays);
      for (int i = 0; i < datesList.size(); i++) {
        String date = datesList.get(i);
        getValues.add(portfolioValueDate(rootDir, pfName, date));
      }
    }

    // if the difference is more than 150 days and 900 days print in months.
    // if difference is more than 900 days and less than equal to 1800 days print on 3 mon basis.
    else if (differenceDays > 150 && differenceDays <= 1800) {
      ArrayList<String> monYearList = getDatesDisplay(date1, date2, differenceDays);
      ArrayList<String> datesList = new ArrayList<>();
      for (int i = 0; i < monYearList.size() - 1; i++) {
        String date = monYearList.get(i);
        String StringDate[] = date.split(" ");
        if (date.contains("Jan")) {
          datesList.add(StringDate[1] + "-01-31");
        } else if (date.contains("Feb")) {
          datesList.add(StringDate[1] + "-02-28");
        } else if (date.contains("Mar")) {
          datesList.add(StringDate[1] + "-03-31");
        } else if (date.contains("Apr")) {
          datesList.add(StringDate[1] + "-04-30");
        } else if (date.contains("May")) {
          datesList.add(StringDate[1] + "-05-31");
        } else if (date.contains("Jun")) {
          datesList.add(StringDate[1] + "-06-30");
        } else if (date.contains("Jul")) {
          datesList.add(StringDate[1] + "-07-31");
        } else if (date.contains("Aug")) {
          datesList.add(StringDate[1] + "-08-31");
        } else if (date.contains("Sep")) {
          datesList.add(StringDate[1] + "-09-30");
        } else if (date.contains("Oct")) {
          datesList.add(StringDate[1] + "-10-31");
        } else if (date.contains("Nov")) {
          datesList.add(StringDate[1] + "-11-30");
        } else if (date.contains("Dec")) {
          datesList.add(StringDate[1] + "-12-31");
        }
      }
      datesList.add(date2);
      for (int i = 0; i < datesList.size(); i++) {
        String date = datesList.get(i);
        getValues.add(portfolioValueDate(rootDir, pfName, date));
      }
    }

    // if difference is more than 1800 days then yearly.
    else {
      ArrayList<String> yearList = getDatesDisplay(date1, date2, differenceDays);
      ArrayList<String> datesList = new ArrayList<>();
      for (int i = 0; i < yearList.size() - 1; i++) {
        String date = yearList.get(i);
        datesList.add(date + "-12-31");
      }
      datesList.add(date2);
      for (int i = 0; i < datesList.size(); i++) {
        String date = datesList.get(i);
        getValues.add(portfolioValueDate(rootDir, pfName, date));
      }
    }
    return getValues;
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
    ArrayList<String> totalDates = new ArrayList<>();
    // if the difference is more than 5 and <= 30 print day wise.
    if (differenceDays >= 5 && differenceDays <= 30) {
      // get the date of the first entry in portfolio.
      LocalDate start = LocalDate.parse(date1);
      LocalDate end = LocalDate.parse(date2);
      while (!start.isAfter(end)) {
        totalDates.add(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        start = start.plusDays(1);
      }
    }
    // if the difference is more than 30 days and <=150 days print for every 5 days.
    else if (differenceDays > 30 && differenceDays <= 150) {
      // get the date of the first entry in portfolio.
      LocalDate start = LocalDate.parse(date1);
      LocalDate end = LocalDate.parse(date2);
      while (!start.isAfter(end)) {
        totalDates.add(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        start = start.plusDays(5);
      }
    }
    // if the difference is more than 150 days and 900 days print in months.
    else if (differenceDays > 150 && differenceDays <= 900) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM", Locale.ENGLISH);
      String stringDate1[] = date1.split("-");
      String stringDate2[] = date2.split("-");
      YearMonth startDate = YearMonth.parse(stringDate1[0] + "-" + stringDate1[1], formatter);
      YearMonth endDate = YearMonth.parse(stringDate2[0] + "-" + stringDate2[1], formatter);

      while (startDate.isBefore(endDate)) {
        String stringDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String dateString[] = stringDate.split("-");
        if (dateString[1].equals("01")) {
          totalDates.add("Jan " + dateString[0]);
        } else if (dateString[1].equals("02")) {
          totalDates.add("Feb " + dateString[0]);
        } else if (dateString[1].equals("03")) {
          totalDates.add("Mar " + dateString[0]);
        } else if (dateString[1].equals("04")) {
          totalDates.add("Apr " + dateString[0]);
        } else if (dateString[1].equals("05")) {
          totalDates.add("May " + dateString[0]);
        } else if (dateString[1].equals("06")) {
          totalDates.add("Jun " + dateString[0]);
        } else if (dateString[1].equals("07")) {
          totalDates.add("Jul " + dateString[0]);
        } else if (dateString[1].equals("08")) {
          totalDates.add("Aug " + dateString[0]);
        } else if (dateString[1].equals("09")) {
          totalDates.add("Sep " + dateString[0]);
        } else if (dateString[1].equals("10")) {
          totalDates.add("Oct " + dateString[0]);
        } else if (dateString[1].equals("11")) {
          totalDates.add("Nov " + dateString[0]);
        } else if (dateString[1].equals("12")) {
          totalDates.add("Dec " + dateString[0]);
        }
        startDate = startDate.plusMonths(1);
      }
    }
    // if difference is more than 900 days and less than equal to 1800 days print on 3 mon basis.
    else if (differenceDays > 900 && differenceDays <= 1800) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM", Locale.ENGLISH);
      String stringDate1[] = date1.split("-");
      String stringDate2[] = date2.split("-");
      YearMonth startDate = YearMonth.parse(stringDate1[0] + "-" + stringDate1[1], formatter);
      YearMonth endDate = YearMonth.parse(stringDate2[0] + "-" + stringDate2[1], formatter);
      while (startDate.isBefore(endDate)) {
        String stringDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String dateString[] = stringDate.split("-");
        if (dateString[1].equals("01")) {
          totalDates.add("Jan " + dateString[0]);
        } else if (dateString[1].equals("02")) {
          totalDates.add("Feb " + dateString[0]);
        } else if (dateString[1].equals("03")) {
          totalDates.add("Mar " + dateString[0]);
        } else if (dateString[1].equals("04")) {
          totalDates.add("Apr " + dateString[0]);
        } else if (dateString[1].equals("05")) {
          totalDates.add("May " + dateString[0]);
        } else if (dateString[1].equals("06")) {
          totalDates.add("Jun " + dateString[0]);
        } else if (dateString[1].equals("07")) {
          totalDates.add("Jul " + dateString[0]);
        } else if (dateString[1].equals("08")) {
          totalDates.add("Aug " + dateString[0]);
        } else if (dateString[1].equals("09")) {
          totalDates.add("Sep " + dateString[0]);
        } else if (dateString[1].equals("10")) {
          totalDates.add("Oct " + dateString[0]);
        } else if (dateString[1].equals("11")) {
          totalDates.add("Nov " + dateString[0]);
        } else if (dateString[1].equals("12")) {
          totalDates.add("Dec " + dateString[0]);
        }
        startDate = startDate.plusMonths(3);
      }
    }
    // if difference is more than 1800 days then yearly.
    else {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH);
      String stringDate1[] = date1.split("-");
      String stringDate2[] = date2.split("-");
      Year startDate = Year.parse(stringDate1[0], formatter);
      Year endDate = Year.parse(stringDate2[0], formatter);
      while (startDate.isBefore(endDate)) {
        String stringDate = startDate.format(DateTimeFormatter.ofPattern("yyyy"));
        String dateString[] = stringDate.split("-");
        totalDates.add(dateString[0]);
        startDate = startDate.plusYears(1);
      }
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
    float scaleVal = 0;
    for (int i = 0; i < values.get(i); i++) {
      scaleVal += values.get(i);
    }
    float finalScaleVal = (scaleVal / values.size());
    return finalScaleVal;
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
      int numAsterisks = (int) (values.get(i) / scaleVal);
      String asteriskString = "";
      for (int j = 0; j < numAsterisks; j++) {
        asteriskString += "*";
      }
      points.add(asteriskString);
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
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
      Date newdate1 = formatter.parse(date1);
      Date newdate2 = formatter.parse(date2);
      difference = (int) (newdate2.getTime() - newdate1.getTime());
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
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
      Date newdate1 = formatter.parse(date1);
      Date newdate2 = formatter.parse(date2);
      if (newdate1.before(newdate2)) {
        return true;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}

