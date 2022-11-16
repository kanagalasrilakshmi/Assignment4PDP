package Model;


import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Model Interface that consists of all the methods to be implemented by the model.
 */
public interface Portfolio {
  public ArrayList<String> getTickrs();

  public ArrayList<String> getNumberStocks();

  public void viewPortfolioDisplay(String rootDir, String filename) throws IOException;

  /**
   * Creates a stock object.
   *
   * @param tickr        is company tickr symbol
   * @param numberStocks is number of stocks purchased
   * @return StocksObj type object
   */
  public StocksObj makeStockObj(String tickr, String numberStocks);

  /**
   * Method for creating new portfolio by the user.
   *
   * @param rootDir  root directory of type string format
   * @param fileName of type string
   * @param listObj  arryay list of StocksObj type
   */
  public void createPortfolio(String rootDir, String fileName, ArrayList<Object> listObj);

  /**
   * Method for displaying the portfolio.
   *
   * @param rootDir  root directory of type string format
   * @param fileName of type string
   * @return array list of portfolio objects of type PortfolioObject
   * @throws IOException if invalid input is given
   */
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir, String fileName) throws IOException;

  /**
   * Get portfolio value for a given date.
   *
   * @param rootDir  root directory of type string format
   * @param fileName of type string
   * @param date     valid format of type String
   * @return value of portfolio in float type
   * @throws FileNotFoundException if the input portfolio filename is not found
   */
  public float portfolioValueDate(String rootDir, String fileName,
                                  String date) throws FileNotFoundException, ParseException;

  /**
   * Check if the given output folder has any portfolios.
   *
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  public boolean hasAtleastOnePortfolio(String rootDir, String extension);

  /**
   * check for future date.
   *
   * @param date input string type date
   * @return true if future else return false
   */
  public boolean checkFutureDate(String date);

  /**
   * check if date is today's date and time is before 9:30am.
   *
   * @param date input string type date
   * @return true if date is today's date and time is before 9:30am else false
   */
  public boolean checkTodayDateAndTime(String date);

  /**
   * CHeck if the date given by the user follows the user input.
   *
   * @param date input string type date
   * @return true if right format is given else false
   */

  public boolean checkIfRightFormat(String date);

  /**
   * Convert text file to array list consisting of valid tickr symbols.
   *
   * @return array list consisting of all valid tickr symbols
   * @throws FileNotFoundException if file is not available
   */
  public ArrayListObj convertTXT() throws FileNotFoundException;

  /**
   * Validate if the given tickr symbol is valid or not.
   *
   * @param tickrSymbol of type String
   * @return true if it is valid else false
   * @throws FileNotFoundException if file is not found
   */
  public boolean validateTickrSymbol(String tickrSymbol) throws FileNotFoundException;

  /**
   * Check if there are any spaces for the given portfolio name.
   *
   * @param pfName portfolio name of type string
   * @return true if there are any spaces else false
   */
  public boolean checkValidpfName(String pfName);

  /**
   * Check if the last ending character is : /.
   *
   * @param rootDirUser is the path given by user in string format
   * @return true if it ends with / else false
   */
  public boolean checkLastEndingCharacter(String rootDirUser);

  /**
   * Check if a given string is an integer.
   *
   * @param numberStocks is number of stocks purchased by user in string format
   * @return true if integer else false
   */
  public boolean checkValidInteger(String numberStocks);

  /**
   * check if the number of stocks entered to be sold is valid or not.
   *
   * @param numStocks is number stocks to be sold
   * @param tickr     is tickr symbol for which stocks need to be sold
   * @param date      is the date on which transaction is made
   * @return true if sale can be made else false
   */

  public boolean checkValidSell(String pfPath, int numStocks, String tickr, String date) throws ParseException;

  /**
   * check if the tickr symbol exists in the portfolio.
   *
   * @param pfPath path for the portfolio
   * @param tickr  is company tickr symbol
   * @return true if tickr exists else false
   */
  //isTickrinpf

  /**
   * Check if the given input date is prior to the given input date for a given tickr.
   *
   * @param date   is input date
   * @param tickr  is company tickr symbol
   * @param pfPath is portfolio path
   * @return true not prior else false
   */
  public boolean checkPriorDate(String date, String tickr, String pfPath) throws ParseException;

  boolean checkValidFloat(String stringToCheck);

  /**
   * modify the json.
   *
   * @param fees   is the commision fees
   * @param num    num stocks willing to sell
   * @param date   date on which sale is to be made
   * @param tickr  company tickr symbol
   * @param pfPath path for the location of the portfolio
   */

  public void modifyJson(Float fees, int num, String date, String tickr, String pfPath);

  /**
   * Get the cost basis of a portfolio till a date.
   *
   * @param pfPath input portfolio path
   * @param date   input string date
   * @return cost basis value
   */
  public float getCostBasis(String pfPath, String date) throws ParseException;

  /**
   * Save a portfolio.
   *
   * @param pfPath portfolio path where json needs to be saved
   * @param data   portfolio json object
   */
  public void savePortfolio(String pfPath, JSONObject data);

  /**
   * check if the tickr symbol exists in a json array
   *
   * @param tickrList of type JSONObject
   * @param tickr     company tickrsymbol
   * @return false if not found else return true
   */
  public boolean checkTickrJSONArray(JSONObject tickrList, String tickr);

  /**
   * check if date is prior to the most recent date entry.
   *
   * @param date         input date entry
   * @param existingDate most recent date
   * @return true if prior else false
   */
  public boolean checkDateinJSONObject(String date, String existingDate);

  /**
   * Get the price of a stock on a date.
   *
   * @param date        input date on which portfolio value is needed
   * @param tickrSymbol company tickr symbol
   * @return float value of the price
   */
  public float getCallPriceDate(String date, String tickrSymbol);

  /**
   * Get the values of the porfolio for a particular day, month, year.
   *
   * @param date1          first input date
   * @param date2          second input date
   * @param differenceDays number of days difference between date1 and date2
   * @param pfName         portfolio name for which performance need to be plotted
   * @param rootDir        root directory of portfolio
   * @return array list of values of the portfolio
   */
  public ArrayList<Float> getValuesPortfolio(String rootDir, String pfName,
                                             String date1, String date2, int differenceDays)
          throws ParseException, FileNotFoundException;


  /**
   * List of days or months or years that needs to be displayed while checking the performance.
   *
   * @param date1          first input date
   * @param date2          second input date
   * @param differenceDays number of days difference between date1 and date2
   * @return array list of dates to be printed for recording performance of the portfolio
   */

  public ArrayList<String> getDatesDisplay(String date1, String date2, int differenceDays);

  /**
   * Compute the scale for the portfolio.
   *
   * @param values list of values obtained on a given date or month or year
   * @return scale of type float
   */
  public float getScale(ArrayList<Float> values);

  /**
   * Get the number of points to be pointed in form of asterisks.
   * for getting the performance of portfolio.
   *
   * @param scaleVal scale of the performance portfolio
   * @param values   list of performance portfolio values
   * @return list of asterisks that needs to be printed
   */
  public ArrayList<String> getPoints(float scaleVal, ArrayList<Float> values);

  /**
   * Difference between dates.
   *
   * @param date1 first input date
   * @param date2 second input date
   * @return difference value between dates date1 and date2
   */
  public int checkDifference(String date1, String date2) throws ParseException;

  /**
   * Check if input date1 is prior to the date2.
   *
   * @param date1 first input date
   * @param date2 second input date
   * @return true if date1 is prior to the date2 else return false
   */
  public boolean checkValidDates(String date1, String date2) throws ParseException;

  public boolean ifTickrInPf(String pfPath, String tickr);

  public JSONObject readPortfolio(String path);

  public JSONObject makeTransactionRecord(String date, float commission, int no_of_stocks, String tickr);
}

