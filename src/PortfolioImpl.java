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
    JSONObject portfolio = new JSONObject();
    for (StocksObj Object : this.ListObj) {
      portfolio.put("Stock Name", Object.getTickr());
      portfolio.put("Number of Stocks", Object.getNumStocks());
    }
    JSONArray StocksObjList = new JSONArray();
    StocksObjList.add(portfolio);
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
      JSONArray portfolioValues = (JSONArray) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for (StocksObj obj : (ArrayList<StocksObj>) portfolioValues) {
        // return present day stock price for the particular stock.
        // call api key function that returns the present the stock value.
        ApiKey apiObj = new ApiKey(obj.getTickr());
        viewPortfolioObj.add(new PortfolioObj(obj.getTickr(), obj.getNumStocks(), apiObj.callPresentPrice()));
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
      JSONArray portfolioValues = (JSONArray) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for (StocksObj obj : (ArrayList<StocksObj>) portfolioValues) {
        // return stock price for the particular stock on the give input date.
        // call api key function that returns the stock value.
        ApiKey apiObj = new ApiKey(obj.getTickr());
        finalSum += obj.getNumStocks() * apiObj.callPriceDate(this.date);
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
