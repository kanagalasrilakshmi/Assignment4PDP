import java.util.ArrayList;

/**
 * Model Interface that consists of all the methods to be implemented by the model.
 */
public interface Portfolio {

  /**
   * Method for creating new portfolio by the user.
   *
   */
  public void createPortfolio();

  /**
   * Method for displaying the portfolio.
   *
   */
  public ArrayList<PortfolioObj> viewPortfolio();

  /**
   * Get portfolio value for a given date
   *
   */
  public float portfolioValueDate();
}
