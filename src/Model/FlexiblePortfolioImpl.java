package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class entryJson {
  private String date;
  private float commissionFee;
  private int numStocks;
  private float stockPrice;
  private float investmentTillDate;
  private int stocksTillDate;
  private String tickrSymbol;

  entryJson(String date, float commissionFee, int numStocks, float stockPrice,
            float investmentTillDate, int stocksTillDate, String tickrSymbol) {
    this.date = date;
    this.numStocks = numStocks;
    this.commissionFee = commissionFee;
    this.stockPrice = stockPrice;
    this.investmentTillDate = investmentTillDate;
    this.stocksTillDate = stocksTillDate;
    this.tickrSymbol = tickrSymbol;
  }

  public float getCommissionFee() {
    return this.commissionFee;
  }

  public int getNumStocks() {
    return this.numStocks;
  }

  public String getDate() {
    return this.date;
  }

  public float getStockPrice() {
    return this.stockPrice;
  }

  public float getInvestmentTillDate() {
    return this.investmentTillDate;
  }

  public int getStocksTillDate() {
    return this.stocksTillDate;
  }

  public String getTickrSymbol() {
    return this.tickrSymbol;
  }
}

public class FlexiblePortfolioImpl extends PortfolioImpl {

  public void modifyPortfolio(String portfolioPath, String date, float commissionFee,
                              int numStocks, String tickrSymbol) {

  }

  public ArrayList<entryJson> loadPortfolio(String portfolioPath) {
    ArrayList<entryJson> listJson = new ArrayList<>();
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(portfolioPath)) {
      Object parseObj = parser.parse(reader);

      System.out.println(parseObj);
      JSONObject portfolio = (JSONObject) parseObj;
      JSONArray tickr_record = (JSONArray) portfolio.get("GOOG");
      System.out.println(tickr_record);

      JSONObject arrayElementOne = new JSONObject();
      arrayElementOne.put("Date", 2);
      arrayElementOne.put("Commission", 2);
      arrayElementOne.put("no_of_stocks", 2);
      arrayElementOne.put("status", 3);
      arrayElementOne.put("remaining_stocks", 3);
      arrayElementOne.put("current_value", 2);
      tickr_record.add(arrayElementOne);
      System.out.println(tickr_record);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return listJson;
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
  public void savePortFolio(String pathPfName){

  }

  public void costBasis(){

  }

  public void getValueDate(){

  }

  public void getBarGraph(){
    
  }
  /**
   * check if the number of stocks entered to be sold is valid or not.
   * @param numStocks is number stocks to be sold
   * @param tickr is tickr symbol for which stocks need to be sold
   * @return true if sale can be made else false
   */
  @Override
  public boolean checkValidSell(String pfPath, int numStocks, String tickr){
    // load the json file.
    // check if the for the given tickr symbol and number of stocks is sale possible.
    // check if number stock to be sold are valid.
    JSONParser parser = new JSONParser();

    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      if (this.checkTickrExists(pfPath,tickr)){
        JSONArray tickr_record = (JSONArray) portfolio.get(tickr);
        // go to the last entry.
        JSONObject lastEntry = (JSONObject) tickr_record.get(tickr_record.size()-1);
        int val = (Integer)lastEntry.get("Total Stock");
        if(val>=numStocks){
          return true;
        }
      }
      for (Object tickrsym :portfolio.keySet()){
        String checkTickr = (String)tickrsym;
        if(checkTickr.equals(tickr)){
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
   * @param pfPath path for the portfolio
   * @param tickr is company tickr symbol
   * @return true if tickr exists else false
   */
  @Override
  public boolean checkTickrExists(String pfPath, String tickr){
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(pfPath)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      for (Object tickrsym :portfolio.keySet()){
        String checkTickr = (String)tickrsym;
        if(checkTickr.equals(tickr)){
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

}

