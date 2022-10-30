import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
    try (FileWriter file = new FileWriter(fileName+".json")){
      file.write(StocksObjList.toJSONString());
      file.flush();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
  public void loadPortfolio(String fileName){

  }

  public PortfolioObj viewPortfolio(String fileName){
    return null;
  }
  public float portfolioValueDate(String fileName, String date){
    return 0;
  }
}
