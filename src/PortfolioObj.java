/**
 * A class that helps to create Portfolio Object.
 */
public class PortfolioObj {
  private String tickr;
  private float numStocks;
  private String date;

  public PortfolioObj(String tickr, int numStocks) {
    this.tickr = tickr;
    this.numStocks = numStocks;
  }

  public PortfolioObj(String tickr, int numStocks,String date) {
    this.tickr = tickr;
    this.numStocks = numStocks;
    this.date = date;
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

  public String getDate() {
    return this.date;
  }

}
