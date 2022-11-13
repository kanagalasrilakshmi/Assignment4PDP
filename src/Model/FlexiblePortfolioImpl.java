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
        // make an api call.
        ApiKey apiObj = new ApiKey(tickr);
        float stockPrice = apiObj.callPriceDate(date);
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
        // make an api call.
        ApiKey apiObj = new ApiKey(tickr);
        // get stock price.
        float stockPrice = apiObj.callPriceDate(date);
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
  public float getCostBasis(String pfPath,String date) {
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
            return (float)tickrRecord.get("CostBasis");
          }
        }
      }
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
    return 0;
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
  public void savePortFolio(String pathPfName) {

  }

  public void getValueDate() {

  }

  public void getBarGraph() {

  }



}

