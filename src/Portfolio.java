import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Model Interface that consists of all the methods to be implemented by the model.
 */
public interface Portfolio {

  /**
   * Method for creating new portfolio by the user.
   */
  public void createPortfolio(String rootDir);

  /**
   * Method for displaying the portfolio.
   */
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir) throws IOException;

  /**
   * Get portfolio value for a given date
   */
  public float portfolioValueDate(String rootDir) throws FileNotFoundException;

  /**
   * Check if the file exists in the given directory.
   *
   * @param pfNamePath path of the file
   * @return true if exists else false
   */
  public boolean checkExists(String pfNamePath);

  /**
   * Check if the given output folder has any portfolios.
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  public boolean checkOutputFolder(String rootDir);

  /**
   * check for future date.
   * @param date input string type date
   * @return true if future else return false
   */
  public boolean checkFutureDate(String date);

  /**
   * check if date is today's date and time is before 9:30am.
   * @param date input string type date
   * @return true if date is today's date and time is before 9:30am else false
   */
  public boolean checkTodayDateAndTime(String date);

  /**
   * CHeck if the date given by the user follows the user input.
   * @param date input string type date
   * @return true if right format is given else false
   */
  public boolean checkIfRightFormat(String date);

  /**
   * Check if the output folder where .txt needs to be saved exists.
   * @param rootDir path for the folder
   * @return true if exists else false
   */
  public boolean checkFolderExists(String rootDir);

  /**
   * Create the output folder.
   * @param rootDir path where the folder needs to be created
   */
  public void createFolder(String rootDir) throws IOException;

  /**
   * Convert text file to array list consisting of valid tickr symbols.
   * @return array list consisting of all valid tickr symbols
   */
  public ArrayList<String> convertTXT() throws FileNotFoundException;

  /**
   * Validate if the given tickr symbol is valid or not.
   * @param tickrSymbol of type String.
   * @return true if it is valid else false.
   */
  public boolean validateTickrSymbol(String tickrSymbol) throws FileNotFoundException;

  /**
   * Create an empty string list.
   * @return Array List of type String that is empty
   */
  public ArrayList<String> createEmptyArrayList();
}
