package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;


public class FlexiblePortfolioImpl extends PortfolioImpl {

  private ArrayList<String> objString = new ArrayList<>();
  private ArrayList<String> objNumStocks = new ArrayList<>();

  /**
   * Creates a stock object.
   *
   * @param tickr        is company tickr symbol
   * @param numberStocks is number of stocks purchased
   * @return StocksObj type object
   */
  public StocksObj makeStockObj(String tickr, String numberStocks) {
    return new StocksObj(tickr, Integer.valueOf(numberStocks));
  }

  public JSONObject makeTransactionRecord(String date, float commission, int no_of_stocks,
                                          float investment_till_date, int remaining_stocks, float current_value) {
    JSONObject record = new JSONObject();
    record.put("date", date);
    record.put("commission", commission);
    record.put("no_of_stocks", no_of_stocks);
    record.put("investment_till_date", investment_till_date);
    record.put("remaining_stocks", remaining_stocks);
    record.put("current_value", current_value);
    return record;
  }

  /**
   * Method for creating new portfolio by the user.
   * Dumps all the data entered by user, stores in ListObj to a .txt file.
   */
  public void createPortfolio(String rootDir, String fileName, ArrayList<Object> listObj) {
    JSONObject portfolio = new JSONObject();
    for (Object object : listObj) {
      JSONArray arrayRecord = new JSONArray();
      StocksObj obj = (StocksObj) object;
      int stocks = obj.getNumStocks();
      String today = "01-01-2020";
      JSONObject record = makeTransactionRecord(today, 222222,stocks , 11111,
              stocks,  11111);
      arrayRecord.add(record);
      portfolio.put(obj.getTickr(),arrayRecord);
      }
    savePortfolio( rootDir+fileName+".json", portfolio);
  }

  public void modifyPortfolio(String portfolioPath, String date, float commissionFee,
                              int numStocks, String tickrSymbol) {
    JSONObject portfolio =  readPortfolio(portfolioPath);
    JSONArray tickr_record = (JSONArray) portfolio.get(tickrSymbol);
    JSONObject lastTransaction = (JSONObject) tickr_record.get(tickr_record.size() - 1);
    System.out.println(tickr_record);
    JSONObject newTransactionRecord = new JSONObject();
    // if purchased add to investment
    double investment;
    investment = (double)lastTransaction.get("investment_till_date");
    System.out.println(investment);
    if (numStocks > 0) {
      investment = (float) investment+ 111;
    } else {
      investment =  (float) investment + commissionFee;
    }
    String totalStocks = String.valueOf(lastTransaction.get("remaining_stocks"));
    newTransactionRecord = makeTransactionRecord(date, 222222F,numStocks , (float) investment,
            Integer.valueOf(totalStocks) + numStocks,  11111);

    tickr_record.add(newTransactionRecord);
    System.out.println("Tikr_Record: "+tickr_record);
    portfolio.put(tickrSymbol,tickr_record);

    savePortfolio(portfolioPath, portfolio);
  }

  public void savePortfolio(String path, JSONObject data) {
    try {
      FileWriter file = new FileWriter(path);
      file.write(data.toJSONString());
      file.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public JSONObject readPortfolio(String path) {
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(path)) {
      Object parseObj = parser.parse(reader);
      JSONObject portfolio = (JSONObject) parseObj;
      return portfolio;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void costBasis() {

  }

  public void getValueDate() {

  }

  public void getBarGraph() {

  }

}

