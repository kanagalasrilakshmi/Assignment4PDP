import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Model Interface that consists of all the methods to be implemented by the model.
 */
public interface Portfolio {

  /**
   * Method for creating new portfolio by the user.
   *
   * @param rootDir  root directory of type string format
   * @param fileName of type string
   * @param listObj  arryay list of StocksObj type
   */
  public void createPortfolio(String rootDir, String fileName, ArrayList<StocksObj> listObj);

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
                                  String date) throws FileNotFoundException;

  /**
   * Check if the given output folder has any portfolios.
   *
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  public boolean checkOutputFolder(String rootDir);

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
}
