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
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir);

  /**
   * Get portfolio value for a given date
   */
  public float portfolioValueDate(String rootDir);

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
}
