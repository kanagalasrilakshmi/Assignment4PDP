package model.portfolioimplhelper;

/**
 * Interface that returns array list type object that gets tickr symbol and num of stocks.
 */
public interface IPortfolioObj {
  /**
   * Get the tickr symbol of the company.
   *
   * @return string type of the tickr symbol.
   */
  String getTickr();

  /**
   * Get number of stocks value.
   *
   * @return float type of number of stocks
   */
  float getNumStocks();
}
