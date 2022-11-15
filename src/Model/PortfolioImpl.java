package Model;


import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
/**
 * A class that helps to create Portfolio Object.
 */
class PortfolioObj {
  private String tickr;
  private float numStocks;

  public PortfolioObj(String tickr, int numStocks) {
    this.tickr = tickr;
    this.numStocks = numStocks;
  }

  /**
   * Get the tickr symbol of the company.
   *
   * @return string type of the tickr symbol.
   */
  public String getTickr() {
    return this.tickr;
  }

  /**
   * Get number of stocks value.
   *
   * @return float type of number of stocks
   */
  public float getNumStocks() {
    return this.numStocks;
  }

}


class StocksObj {
  private String tickr;
  private int numStocks;

  public StocksObj(String tickr, int numStocks) {
    this.tickr = tickr;
    this.numStocks = numStocks;
  }

  /**
   * Get the tickr symbol of the company.
   *
   * @return string type of the tickr symbol.
   */
  public String getTickr() {
    return this.tickr;
  }

  /**
   * Get number of stocks value.
   *
   * @return float type of number of stocks
   */
  public int getNumStocks() {
    return this.numStocks;
  }
}
/**
 * Class for creating an object for creating arraylist objects.
 */
class ArrayListObj {
  private ArrayList<String> tickrSymbols;
  private ArrayList<String> prices;

  /**
   * Constructor for ArrayLiostObj.
   *
   * @param tickrSymbols of type string array list of stock tickrs
   * @param prices       of type string array list of prices
   */
  public ArrayListObj(ArrayList<String> tickrSymbols, ArrayList<String> prices) {
    this.tickrSymbols = tickrSymbols;
    this.prices = prices;
  }

  /**
   * gets the array list of tickr symbols.
   *
   * @return arraylist of tickrsymbols of type string
   */
  public ArrayList<String> getTickrSymbols() {
    return this.tickrSymbols;
  }

  /**
   * gets the array list of corresponding prices for a tickr symbols.
   *
   * @return arraylist of price for tickrsymbols of type string
   */
  public ArrayList<String> getPrices() {
    return this.prices;
  }
}


/**
 * Implementing the Portfolio Interface and coded the implementation.
 */
public class PortfolioImpl implements Portfolio {
  private ArrayList<String> objString = new ArrayList<>();
  private ArrayList<String> objNumStocks = new ArrayList<>();
  /**
   * Creates a stock object.
   * @param tickr is company tickr symbol
   * @param numberStocks is number of stocks purchased
   * @return StocksObj type object
   */
  public StocksObj makeStockObj(String tickr, String numberStocks){
    return new StocksObj(tickr,Integer.valueOf(numberStocks));
  }
  /**
   * Method for creating new portfolio by the user.
   * Dumps all the data entered by user, stores in ListObj to a .txt file.
   */
  public void createPortfolio(String rootDir, String fileName, ArrayList<Object> listObj) {
    // create a txt type file.
    // Create a String type ArrayList.
    ArrayList<String> listAdded = new ArrayList<>();
    try (FileWriter file = new FileWriter(rootDir + fileName + ".txt")) {
      listAdded.add("Company Tickr Symbol,Num Of Stocks");
      for (Object object : listObj) {
        // go through all the elements in the ListObj.
        StocksObj obj = (StocksObj)object;
        String toBeAppended = obj.getTickr() + "," + String.valueOf(obj.getNumStocks());
        listAdded.add(toBeAppended);
      }
      for (int i = 0; i < listAdded.size(); i++) {
        file.write(listAdded.get(i));
        // add a new line.
        if (i < listAdded.size() - 1) {
          file.write("\n");
        }
      }
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for displaying the portfolio.
   */
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir, String filename)
          throws IOException {
    // load the portfolio of the given input file name.
    BufferedReader in = new BufferedReader(new FileReader(rootDir + filename + ".txt"));
    String inputLine;
    ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        if (!(inputLine.split(",")[0].equals("Company Tickr Symbol") &&
                inputLine.split(",")[1].equals("Num Of Stocks"))) {
          String tickrSymbol = inputLine.split(",")[0];
          //ApiKey apiObj = new ApiKey(tickrSymbol);
          int numStocks = Integer.valueOf(inputLine.split(",")[1]);
          // set a timer here.
          // allow api calls for every 25s only.

          viewPortfolioObj.add(new PortfolioObj(tickrSymbol, numStocks));
        }
      }
      return viewPortfolioObj;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<String> getTickrs(){
    return this.objString;
  }

  public ArrayList<String> getNumberStocks(){
    return this.objNumStocks;
  }

  public void viewPortfolioDisplay(String rootDir, String filename) throws IOException{
    ArrayList<PortfolioObj> obj = viewPortfolio(rootDir, filename);
    ArrayList<String> tickrSymbols = new ArrayList<>();
    ArrayList<String> numStocks = new ArrayList<>();
    for(PortfolioObj object: obj){
      tickrSymbols.add(object.getTickr());
      numStocks.add(String.valueOf(object.getNumStocks()));
    }
    ArrayListObj objreturn = new ArrayListObj(tickrSymbols,numStocks);
    this.objString = objreturn.getTickrSymbols();
    this.objNumStocks = objreturn.getPrices();
  }

  /**
   * Get portfolio value for a given date.
   */
  public float portfolioValueDate(String rootDir, String fileName,
                                  String date) throws FileNotFoundException {
    // initialize sum to 0.
    float finalSum = 0;
    // load the portfolio of the given input file name.
    BufferedReader in = new BufferedReader(new FileReader(rootDir + fileName + ".txt"));
    String inputLine;
    ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        if (!(inputLine.split(",")[0].equals("Company Tickr Symbol") &&
                inputLine.split(",")[1].equals("Num Of Stocks"))) {
          String tickrSymbol = inputLine.split(",")[0];
          ApiKey apiObj = new ApiKey(tickrSymbol);
          Float numStocks = Float.valueOf(inputLine.split(",")[1]);
          ArrayListObj tickerSymbolsPrice = this.convertTXT();
          // initialize it as zero
          Float price = 0.0f;
          for (int i = 0; i < tickerSymbolsPrice.getTickrSymbols().size(); i++) {
            if (tickerSymbolsPrice.getTickrSymbols().get(i).equals(tickrSymbol)) {
              price = Float.valueOf(tickerSymbolsPrice.getPrices().get(i));
              break;
            }
          }
          Float apiPrice = apiObj.callPriceDate(date);
          if (apiPrice != 0) {
            price = apiPrice;
          }
          // set a timer here.
          // allow api calls for every 25s only.
          finalSum += numStocks * price;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return finalSum;
  }

  /**
   * Check if the given output folder has any portfolios.
   *
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  public boolean checkOutputFolder(String rootDir) {
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for (File file : filesList) {
      if (file.isFile()) {
        if (file.getName().contains(".txt")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * check for future date.
   *
   * @param date input string type date
   * @return true if future else return false
   */
  public boolean checkFutureDate(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate.parse(date, format);
    } catch (DateTimeException e) {
      return false;
    }
    try {
      LocalDate givenDate = LocalDate.parse(date, format);
      LocalDate todayDate = LocalDate.now();
      return givenDate.isAfter(todayDate);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * check if date is today's date and time is before 9:30am.
   *
   * @param date input string type date
   * @return true if date is today's date and time is before 9:30am else false
   */
  public boolean checkTodayDateAndTime(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate givenDate = LocalDate.parse(date, format);
    } catch (DateTimeException e) {
      return false;
    }
    try {
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
          } catch (DateTimeException e) {
            e.printStackTrace();
            return false;
          }
        }
        return false;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * CHeck if the date given by the user follows the user input.
   *
   * @param date input string type date
   * @return true if right format is given else false
   */
  public boolean checkIfRightFormat(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try {
      format.parse(date);
      return true;
    } catch (java.text.ParseException e) {
      return false;
    }
  }

  /**
   * Convert text file to array list consisting of valid tickr symbols.
   *
   * @return array list consisting of all valid tickr symbols
   */
  public ArrayListObj convertTXT() throws FileNotFoundException {
    BufferedReader in = new BufferedReader(new FileReader(new
            File("tickrData.txt").getAbsolutePath()));
    String inputLine;
    ArrayList<String> tickrSymbolsList = new ArrayList<>();
    ArrayList<String> pricesList = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        String tickrSymbol = inputLine.split(",")[0];
        String prices = inputLine.split(",")[1];
        tickrSymbolsList.add(tickrSymbol);
        pricesList.add(prices);
      }
      return new ArrayListObj(tickrSymbolsList, pricesList);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Validate if the given tickr symbol is valid or not.
   *
   * @param tickrSymbol of type String.
   * @return true if it is valid else false.
   */
  public boolean validateTickrSymbol(String tickrSymbol) {
    // open the tickrlist.
    // check if given symbol is in the text file.
    try {
      ArrayListObj tickrSymbolsPriceList = this.convertTXT();
      return (tickrSymbolsPriceList.getTickrSymbols().contains(tickrSymbol));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Check if there are any spaces or null or empty or length > 25 for the given portfolio name.
   *
   * @param pfName portfolio name of type string
   * @return true if there are any spaces or null or empty or length > 25 else false
   */
  public boolean checkValidpfName(String pfName) {
    if (pfName == null || pfName.length() > 25 || pfName.isEmpty() || pfName.contains(" ")) {
      return false;
    }
    for (int i = 0; i < pfName.length(); i++) {
      if (!Character.isLetter(pfName.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the last ending character is : /.
   *
   * @param rootDirUser is the path given by user in string format
   * @return true if it ends with / else false
   */
  public boolean checkLastEndingCharacter(String rootDirUser) {
    return (rootDirUser.charAt(rootDirUser.length() - 1) == '/');
  }

  /**
   * Check if a given string is an integer.
   *
   * @param numberStocks is number of stocks purchased by user in string format
   * @return true if integer else false
   */
  public boolean checkValidInteger(String numberStocks) {
    for (int i = 0; i < numberStocks.length(); i++) {
      if (!Character.isDigit(numberStocks.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * check if the number of stocks entered to be sold is valid or not.
   * @param numStocks is number stocks to be sold
   * @param tickr is tickr symbol for which stocks need to be sold
   * @return true if sale can be made else false
   */
  public boolean checkValidSell(String pfPath, int numStocks, String tickr){
    return false;
  }

  /**
   * check if the tickr symbol exists in the portfolio.
   * @param pfPath path for the portfolio
   * @param tickr is company tickr symbol
   * @return true if tickr exists else false
   */
  public boolean checkTickrExists(String pfPath, String tickr){
    return false;
  }

  /**
   * Check if the given input date is prior to the given input date for a given tickr.
   * @param date is input date
   * @param tickr is company tickr symbol
   * @param pfPath is portfolio path
   * @return true not prior else false
   */
  public boolean checkPriorDate(String date,String tickr, String pfPath){
    return false;
  }

  /**
   * check if the given number either an integer or a decimal number
   * @param num input string parameter
   * @return true if valid else return false
   */
  public boolean checkValidNum(String num){
    return false;
  }

  /**
   * modify the json.
   *
   * @param fees   is the commision fees
   * @param num    num stocks willing to sell
   * @param date   date on which sale is to be made
   * @param tickr company tickr symbol
   * @param pfPath path for the location of the portfolio
   */

  public void modifyJson(String fees, int num, String date, String tickr,String pfPath){}

  /**
   * Get the cost basis of a portfolio till a date.
   * @param pfPath input portfolio path
   * @param date input string date
   * @return cost basis value
   */
  public float getCostBasis(String pfPath,String date){
    return 0;
  }

  /**
   * create a json portfolio.
   * @param pfPath portfolio path where json needs to be saved
   * @param addEntry add json entry
   */
  public void createPortfolioJson(String pfPath, JSONObject addEntry){

  }

  /**
   * check if the tickr symbol exists in a json array
   * @param tickrList of type JSONObject
   * @param tickr company tickrsymbol
   * @return false if not found else return true
   */
  public boolean checkTickrJSONArray(JSONObject tickrList,String tickr){
    return false;
  }

  public boolean checkDateinJSONObject(String date, String existingDate){
    return false;
  }

  /**
   * Get the price of a stock on a date.
   * @param date input date on which portfolio value is needed
   * @param tickrSymbol company tickr symbol
   * @return float value of the price
   */
  public float getCallPriceDate(String date,String tickrSymbol){
    return 0;
  }

  /**
   * Get the values of the porfolio for a particular day, month, year.
   * @param date1 first input date
   * @param date2 second input date
   * @param differenceDays number of days difference between date1 and date2
   * @param pfName portfolio name for which performance need to be plotted
   * @param rootDir root directory of portfolio
   * @return array list of values of the portfolio
   */
  @Override
  public ArrayList<Float>getValuesPortfolio(String rootDir,String pfName,
                                            String date1,String date2,int differenceDays)
          throws java.text.ParseException, FileNotFoundException {
    return new ArrayList<>();
  }

  /**
   * List of days or months or years that needs to be displayed while checking the performance.
   * @param date1 first input date
   * @param date2 second input date
   * @param differenceDays number of days difference between date1 and date2
   * @return array list of dates to be printed for recording performance of the portfolio
   */

  public ArrayList<String>getDatesDisplay(String date1,String date2,int differenceDays){
    return new ArrayList<String>();
  }

  /**
   * Compute the scale for the portfolio.
   * @param values list of values obtained on a given date or month or year
   * @return scale of type float
   */
  public float getScale(ArrayList<Float>values){
    return 0;
  }

  /**
   * Get the number of points to be pointed in form of asterisks.
   * for getting the performance of portfolio.
   * @param scaleVal scale of the performance portfolio
   * @param values list of performance portfolio values
   * @return list of asterisks that needs to be printed
   */
  public ArrayList<String> getPoints(float scaleVal,ArrayList<Float>values){
    return new ArrayList<String>();
  }

  /**
   * Difference between dates.
   * @param date1 first input date
   * @param date2 second input date
   * @return difference value between dates date1 and date2
   */
  public int checkDifference(String date1,String date2) {
    return 0;
  }

  /**
   * Check if input date1 is prior to the date2.
   * @param date1 first input date
   * @param date2 second input date
   * @return true if date1 is prior to the date2 else return false
   */
  public boolean checkValidDates(String date1,String date2){
    return false;
  }
}

