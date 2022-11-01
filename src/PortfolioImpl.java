import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Implementing the Portfolio Interface and coded the implementation.
 */
public class PortfolioImpl implements Portfolio {
  private ArrayList<StocksObj> ListObj;
  private String fileName;
  private String date;

  public PortfolioImpl(){

  }
  public PortfolioImpl(ArrayList<StocksObj> ListObj,String fileName){
    this.fileName = fileName;
    this.ListObj = ListObj;
  }
  public PortfolioImpl(String fileName){
    this.fileName = fileName;
  }
  public PortfolioImpl(String fileName,String date){
    this.fileName = fileName;
    this.date = date;
  }
  /**
   * Method for creating new portfolio by the user.
   *
   */
  public void createPortfolio() {
    // go through all the elements in the ListObj.
    JSONObject StocksObjList = new JSONObject();
    for (StocksObj Object : this.ListObj) {
      //JSONArray temp = new JSONArray();
      JSONObject StocksObjListTemp = new JSONObject();
      StocksObjListTemp.put("Num Stocks",Object.getNumStocks());
      //temp.add(StocksObjListTemp);
      StocksObjList.put(Object.getTickr(),StocksObjListTemp);
    }
    // create a json type file.
    try (FileWriter file = new FileWriter(this.fileName + ".json")) {
      file.write(StocksObjList.toJSONString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for displaying the portfolio.
   *
   */
  public ArrayList<PortfolioObj> viewPortfolio() {
    // load the portfolio of the given input file name.
    JSONParser parserPortfolio = new JSONParser();
    try (FileReader reader = new FileReader(this.fileName + ".json")) {
      Object parseObj = parserPortfolio.parse(reader);
      JSONObject portfolioValues = (JSONObject) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for(Object key : portfolioValues.keySet() ){
        JSONObject value = (JSONObject)portfolioValues.get(key);
        Double NumStocks = (Double)value.get("Num Stocks");
        String tickrSymbol = (String)key;
        ApiKey apiObj = new ApiKey(tickrSymbol);
        viewPortfolioObj.add(new PortfolioObj(tickrSymbol, NumStocks.floatValue(), apiObj.callPresentPrice()));
      }
      return viewPortfolioObj;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get portfolio value for a given date
   *
   */
  public float portfolioValueDate() {
    float finalSum = 0;
    // load the portfolio of the given input file name.
    JSONParser parserPortfolio = new JSONParser();
    try (FileReader reader = new FileReader(this.fileName + ".json")) {
      Object parseObj = parserPortfolio.parse(reader);
      JSONObject portfolioValues = (JSONObject) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for(Object key : portfolioValues.keySet() ){
        JSONObject value = (JSONObject)portfolioValues.get(key);
        Double NumStocks = (Double)value.get("Num Stocks");
        String tickrSymbol = (String)key;
        ApiKey apiObj = new ApiKey(tickrSymbol);
        finalSum += NumStocks.floatValue() * apiObj.callPriceDate(this.date);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return finalSum;
  }
}
