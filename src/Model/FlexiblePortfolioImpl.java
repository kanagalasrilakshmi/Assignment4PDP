package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
      if(!checkTickrExists(pfPath,tickr)){
        JSONObject newEntry = new JSONObject();
        float stockPrice = (float)newEntry.get("Stock Price");
        // make account of cost basis.
        float newcostBasis = Float.valueOf(fees)+ (stockPrice*num);
        newEntry.put("Date", date);
        newEntry.put("Commission Fee", fees);
        newEntry.put("NumStocks Sold/Purchased", num);
        newEntry.put("Stock Price", stockPrice);
        newEntry.put("TotalStocks", num);
        newEntry.put("CostBasis", newcostBasis);
        portfolio.put(tickr,newEntry);
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
      else{
        JSONArray tickrRecord = (JSONArray) portfolio.get(tickr);
        JSONObject lastEntry = (JSONObject) tickrRecord.get(tickrRecord.size()-1);
        int totStocks = (Integer)(lastEntry.get("TotalStocks"));
        int newtotStocks = totStocks + num;
        // get stock price.
        float stockPrice = (float)lastEntry.get("Stock Price");
        // make account of cost basis.
        float newcostBasis = Float.valueOf(fees)+ (float)lastEntry.get("CostBasis");
        if(num>0){
          newcostBasis += (stockPrice*num);
        }
        // add them to the json.
        JSONObject newEntry = new JSONObject();
        newEntry.put("Date", date);
        newEntry.put("Commission Fee", fees);
        newEntry.put("NumStocks Sold/Purchased", num);
        newEntry.put("Stock Price", stockPrice);
        newEntry.put("TotalStocks", newtotStocks);
        newEntry.put("CostBasis", newcostBasis);
        // add back to the json.
        tickrRecord.add(newEntry);
        portfolio.put(tickr,tickrRecord);
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
   * @param pfPath input portfolio path
   * @param date input string date
   * @return cost basis value
   */
  @Override
  public float getCostBasis(String pfPath,String date) {
    float finalCostBasis = 0;
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      for (Object tickrsym : portfolio.keySet()) {
        String checkTickr = (String) tickrsym;
        JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
        for(int i = arrayObj.size()-1 ;i>=0;i--){
          JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
          if(checkPriorDate(date,(String)tickrRecord.get("Date"),pfPath)){
            finalCostBasis +=  (float)tickrRecord.get("CostBasis");
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
    }
  }

  /**
   * create a json portfolio.
   * @param pfPath portfolio path where json needs to be saved
   * @param addEntry add json entry
   */
  @Override
  public void createPortfolioJson(String pfPath,JSONObject addEntry){
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
                                  String date) throws FileNotFoundException{
    float finalVal = 0;
    // load the json file.
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(rootDir+fileName+".json")) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      for (Object tickrsym : portfolio.keySet()) {
        ApiKey apiObj = new ApiKey((String)tickrsym);
        JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
        for(int i = arrayObj.size()-1 ;i>=0;i--){
          JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
          // check if the date is prior.
          // get the stocks.
          // multiply by the stock value and number of total stocks on that day.
          if(checkPriorDate(date,(String)tickrRecord.get("Date"),rootDir+fileName+".json")){
            finalVal +=  (int)tickrRecord.get("TotalStocks")* apiObj.callPriceDate(date);
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
        int val = (Integer) lastEntry.get("Total Stock");
        if (val >= numStocks) {
          return true;
        }
      }
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
        Date newdate = formatter.parse(date);
        Date checkrecentDate = formatter.parse(recentDate);
        if (checkrecentDate.before(newdate)) {
          return true;
        }
      }
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
   * @param tickrList of type JSONObject
   * @param tickr company tickrsymbol
   * @return false if not found else return true
   */
  public boolean checkTickrJSONArray(JSONObject tickrList,String tickr){
    for(Object ticksSym : tickrList.keySet()){
      String tickerSymbol = (String)ticksSym;
      if(tickerSymbol.equals(tickr)){
        return true;
      }
    }
    return false;
  }

  /**
   * check if date is prior to the most recent date entry.
   * @param date input date entry
   * @param existingDate most recent date
   * @return true if prior else false
   */
  public boolean checkDateinJSONObject(String date, String existingDate){
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-DD", Locale.ENGLISH);
    try{
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
   * @param date input date on which portfolio value is needed
   * @param tickrSymbol company tickr symbol
   * @return float value of the price
   */
  public float getCallPriceDate(String date,String tickrSymbol){
    ApiKey apiObj = new ApiKey(tickrSymbol);
    return apiObj.callPriceDate(date);
  }

  /* Create
  JSONObject obj = new JSONObject();
  JSONObject arrayElementOne = new JSONObject();
  JSONArray arrayElementOneArray = new JSONArray();
  arrayElementOne.put("Date", 1);
  arrayElementOne.put("Commission", 1);
  arrayElementOne.put("no_of_stocks", 1);
  arrayElementOne.put("status", 1);
  arrayElementOne.put("remaining_stocks", 1);
  arrayElementOne.put("current_value", 1);
  arrayElementOneArray.add(arrayElementOne);
  obj.put("GOOG",arrayElementOneArray);
  System.out.print(obj);

  try {
    FileWriter file = new FileWriter(System.getProperty("user.home")+"/Desktop/"+"output.json");
    file.write(obj.toJSONString());
    file.close();
  } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();

  }
  */
}

