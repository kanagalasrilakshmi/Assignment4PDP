/**
 * Model Interface that consists of all the methods to be implemented by the model.
 */
public interface Portfolio {

  /**
   * Method for creating new portfolio by the user.
   *
   * @param fileName with filename of the string
   * @param ListObj  with list of objects if type StocksObj
   */
  public void createPortfolio(String fileName, StocksObj ListObj);

  /**
   * Load portfolio.
   *
   * @param fileName with filename of the string
   */
  public void loadPortfolio(String fileName);

  /**
   * Method for saving the portfolio.
   *
   * @param fileName with filename of the string
   */
  public void savePortfolio(String fileName);

  /**
   * Method for adding StocksObj type list to the json file of the given string input file name.
   *
   * @param fileName with filename of the string
   * @param Obj      for including the entry into the file
   */
  public void helperCreatePortfolio(String fileName, StocksObj Obj);

  /**
   * Method for displaying the portfolio.
   *
   * @param fileName for which portfolio needs to be displayed
   */
  public PortfolioObj viewPortfolio(String fileName);

  /**
   * Get portfolio value for a given date
   *
   * @param fileName for which portfolio needs to be displayed
   * @param date     for which portfolio value needs to be displayed
   */
  public float portfolioValueDate(String fileName, String date);
}
