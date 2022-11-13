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

}

