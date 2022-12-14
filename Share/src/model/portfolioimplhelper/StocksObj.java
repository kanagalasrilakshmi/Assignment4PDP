package model.portfolioimplhelper;

/**
 * A class for creating stocks object.
 */

public class StocksObj implements IStocksObj {
  private final String tickr;
  private final int numStocks;

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
