import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementing the Portfolio Interface and coded the implementation.
 */
public class PortfolioImpl implements Portfolio {
  private ArrayList<StocksObj> ListObj;
  private String fileName;
  private String date;

  public PortfolioImpl() {
  }

  public PortfolioImpl(ArrayList<StocksObj> ListObj, String fileName) {
    this.fileName = fileName;
    this.ListObj = ListObj;
  }

  public PortfolioImpl(String fileName) {
    this.fileName = fileName;
  }

  public PortfolioImpl(String fileName, String date) {
    this.fileName = fileName;
    this.date = date;
  }

  /**
   * Method for creating new portfolio by the user.
   */
  public void createPortfolio(String rootDir) {
    // go through all the elements in the ListObj.
    JSONObject StocksObjList = new JSONObject();
    for (StocksObj Object : this.ListObj) {
      //JSONArray temp = new JSONArray();
      JSONObject StocksObjListTemp = new JSONObject();
      StocksObjListTemp.put("Num Stocks", Object.getNumStocks());
      //temp.add(StocksObjListTemp);
      StocksObjList.put(Object.getTickr(), StocksObjListTemp);
    }
    // create a json type file.
    try (FileWriter file = new FileWriter(rootDir + this.fileName + ".json")) {
      file.write(StocksObjList.toJSONString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for displaying the portfolio.
   */
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir) {
    // load the portfolio of the given input file name.
    JSONParser parserPortfolio = new JSONParser();
    try (FileReader reader = new FileReader(rootDir + this.fileName + ".json")) {
      Object parseObj = parserPortfolio.parse(reader);
      JSONObject portfolioValues = (JSONObject) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for (Object key : portfolioValues.keySet()) {
        JSONObject value = (JSONObject) portfolioValues.get(key);
        Double NumStocks = (Double) value.get("Num Stocks");
        String tickrSymbol = (String) key;
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
   */
  public float portfolioValueDate(String rootDir) {
    float finalSum = 0;
    // load the portfolio of the given input file name.
    JSONParser parserPortfolio = new JSONParser();
    try (FileReader reader = new FileReader(rootDir + this.fileName + ".json")) {
      Object parseObj = parserPortfolio.parse(reader);
      JSONObject portfolioValues = (JSONObject) parseObj;
      ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
      for (Object key : portfolioValues.keySet()) {
        JSONObject value = (JSONObject) portfolioValues.get(key);
        Double NumStocks = (Double) value.get("Num Stocks");
        String tickrSymbol = (String) key;
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

  /**
   * Check if the file exists in the given directory.
   *
   * @param pfNamePath path of the file
   * @return true if exists else false
   */
  public boolean checkExists(String pfNamePath) {
    File tempFile = new File(pfNamePath);
    return tempFile.exists();
  }

  /**
   * Check if the given output folder has any portfolios.
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  public boolean checkOutputFolder(String rootDir){
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for(File file:filesList){
      if(file.isFile()){
        if (file.getName().contains(".json")){
          return true;
        }
      }
    }
    return false;
  }

  /**
   * check for future date.
   * @param date input string type date
   * @return true if future else return false
   */
  public boolean checkFutureDate(String date){
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try{
      LocalDate givenDate = LocalDate.parse(date,format);
      LocalDate todayDate = LocalDate.now();
      if(givenDate.isAfter(todayDate)){
        return true;
      }
      return false;
    }
    catch (Exception e){
      e.printStackTrace();
      return false;
    }
  }

  /**
   * check if date is today's date and time is before 9:30am.
   * @param date input string type date
   * @return true if date is today's date and time is before 9:30am else false
   */
  public boolean checkTodayDateAndTime(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate givenDate = LocalDate.parse(date, format);
      LocalDate todayDate = LocalDate.now();
      // check today date.
      if (givenDate.isEqual(todayDate)) {
        // check if time is less than 9:30 am.
        LocalTime timeNow = LocalTime.now();
        String valTimeNow = String.valueOf(timeNow);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
          Date dateFormat = timeFormat.parse(valTimeNow);
          if (dateFormat.before(timeFormat.parse("09:30"))) {
            return true;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        return false;
      }
      return false;
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return false;
  }

  /**
   * CHeck if the date given by the user follows the user input.
   * @param date input string type date
   * @return true if right format is given else false
   */
  public boolean checkIfRightFormat(String date){
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try{
      format.parse(date);
      return true;
    }
    catch (java.text.ParseException e){
      return false;
    }
  }
}
