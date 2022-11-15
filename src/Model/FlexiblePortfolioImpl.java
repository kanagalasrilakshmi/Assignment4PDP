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
        if(recentDate.equals(date) || checkIfBeforeDate(date, recentDate)){
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
   * @param date1 first input date
   * @param date2 second input date
   * @param differenceDays number of days difference between date1 and date2
   * @return array list of values of the portfolio
   */
  @Override
  public ArrayList<Float> getValuesPortfolio(String date1, String date2, int differenceDays){
    // if the difference is more than 5 and <= 30 print day wise.

    // if the difference is more than 30 days and <=150 days print for every 5 days.

    // if the difference is more than 150 days and 900 days.

    // if difference is more than 900 days and less than equal to 1800 days.

    // if difference is more than 1800 days than yearly.

    return new ArrayList<Float>();
  }

  /**
   * List of days or months or years that needs to be displayed while checking the performance
   * @param date1 first input date
   * @param date2 second input date
   * @param differenceDays number of days difference between date1 and date2
   * @return array list of dates to be printed for recording performance of the portfolio
   */
  @Override
  public ArrayList<String>getDatesDisplay(String date1,String date2,int differenceDays){
    // if the difference is more than 5 and <= 30 print day wise.

    // if the difference is more than 30 days and <=150 days print for every 5 days.

    // if the difference is more than 150 days and 900 days.

    // if difference is more than 900 days and less than equal to 1800 days.

    // if difference is more than 1800 days than yearly.

    return new ArrayList<String>();
  }

  /**
   * Compute the scale for the portfolio.
   * @param values list of values obtained on a given date or month or year
   * @return scale of type float
   */
  @Override
  public float getScale(ArrayList<Float>values){
    float scaleVal = 0;
    for(int i =0;i<values.get(i);i++){
      scaleVal += values.get(i);
    }
    float finalScaleVal = (scaleVal/values.size());
    return finalScaleVal;
  }

  /**
   * Get the number of points to be pointed in form of asterisks.
   * for getting the performance of portfolio.
   * @param scaleVal scale of the performance portfolio
   * @param values list of performance portfolio values
   * @return list of asterisks that needs to be printed
   */
  @Override
  public ArrayList<String> getPoints(float scaleVal,ArrayList<Float>values){
    ArrayList<String>points = new ArrayList<>();
    for(int i =0;i<values.size();i++){
      int numAsterisks = (int)(values.get(i)/scaleVal);
      String asteriskString = "";
      for(int j = 0;j<numAsterisks;j++){
        asteriskString +="*";
      }
      points.add(asteriskString);
    }
    return points;
  }

  /**
   * Difference between dates.
   * @param date1 first input date
   * @param date2 second input date
   * @return difference value between dates date1 and date2
   */
  @Override
  public int checkDifference(String date1,String date2) {
    int difference = 0;
    try{
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
      Date newdate1 = formatter.parse(date1);
      Date newdate2 = formatter.parse(date2);
      difference = (int) (newdate2.getTime()-newdate1.getTime());
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return difference;
  }

  /**
   * Check if input date1 is prior to the date2.
   * @param date1 first input date
   * @param date2 second input date
   * @return true if date1 is prior to the date2 else return false
   */
  @Override
  public boolean checkValidDates(String date1,String date2){
    try{
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
      Date newdate1 = formatter.parse(date1);
      Date newdate2 = formatter.parse(date2);
      if (newdate1.before(newdate2)) {
        return true;
      }
      return false;
    }
    catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }
}

