/**
 * A class that helps to create a stock object.
 */
public class StocksObj {
  private String tickr;
  private int numStocks;
  private String date;

  public StocksObj(String tickr, int numStocks) {
    this.tickr = tickr;
    this.numStocks = numStocks;
  }

  public StocksObj(String tickr, int numStocks, String date) {
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
  public int getNumStocks() {
    return this.numStocks;
  }

  public String getDate(){
    return this.date;
  }
}
