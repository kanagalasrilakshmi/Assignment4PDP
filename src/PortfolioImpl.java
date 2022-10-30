import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
public class PortfolioImpl implements Portfolio{
  public void createPortfolio(String fileName, ArrayList<StocksObj> ListObj){
    // go through all the elements in the ListObj.
    JSONObject portfolio = new JSONObject();
    for (StocksObj Object:ListObj){
      portfolio.put("Stock Name",Object.getTickr());
      portfolio.put("Number of Stocks",Object.getNumStocks());
    }
    JSONArray StocksObjList = new JSONArray();
    StocksObjList.add(portfolio);
    // create a json type file.
    try (FileWriter file = new FileWriter(fileName+".json")){
      file.write(StocksObjList.toJSONString());
      file.flush();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }

  public ArrayList<PortfolioObj> viewPortfolio(String fileName){
    // load the portfolio of the given input file name.
    JSONParser parserPortfolio = new JSONParser();
    try (FileReader reader = new FileReader(fileName+".json")){
      Object parseObj = parserPortfolio.parse(reader);
      JSONArray portfolioValues = (JSONArray) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for (StocksObj obj : (ArrayList<StocksObj>)portfolioValues){
        // return present day stock price for the particular stock.
        // call api key function that returns the present the stock value.
        ApiKey apiObj = new ApiKey(obj.getTickr());
        viewPortfolioObj.add(new PortfolioObj(obj.getTickr(), obj.getNumStocks(),apiObj.callPresentPrice()));
      }
      return viewPortfolioObj;
    }
    catch (FileNotFoundException e){
      e.printStackTrace();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    catch(ParseException e){
      e.printStackTrace();
    }
    return null;
  }
  public float portfolioValueDate(String fileName, String date){
    float finalSum = 0;
    // load the portfolio of the given input file name.
    JSONParser parserPortfolio = new JSONParser();
    try (FileReader reader = new FileReader(fileName+".json")){
      Object parseObj = parserPortfolio.parse(reader);
      JSONArray portfolioValues = (JSONArray) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for (StocksObj obj : (ArrayList<StocksObj>)portfolioValues){
        // return stock price for the particular stock on the give input date.
        // call api key function that returns the stock value.
        ApiKey apiObj = new ApiKey(obj.getTickr());
        finalSum += obj.getNumStocks()*apiObj.callPriceDate(date);
      }
    }
    catch (FileNotFoundException e){
      e.printStackTrace();
    }
    catch(IOException e){
      e.printStackTrace();
    }
    catch(ParseException e){
      e.printStackTrace();
    }
    return finalSum;
  }
}
