public class PortfolioObj {
  private String tickr;
  private float numStocks;
  private float stockPrice;

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

  /**
   * Get stock price value of the particular tickr symbol.
   *
   * @return float type stock price
   */
  public float getStockPrice() {
    return this.stockPrice;
  }
}
