package model.portfolioimplhelper;

/**
 * A class that helps to create Portfolio Object.
 */
public class PortfolioObj implements IPortfolioObj{
  private final String tickr;
  private final float numStocks;

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
