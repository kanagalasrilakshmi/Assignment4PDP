package model;

/**
 * Interface that helps to implement api key functions getTickrSymbol, fetch price of a stock on a date,
 * Call present price of a stock.
 */
public interface IApiKey {
  /**
   * Gets the tickr symbol.
   *
   * @return tickr symbol of type string
   */
  String getTickrSymbol();

  /**
   * get the price for the present day stock.
   *
   * @return float type price of the stock
   */
  float callPresentPrice();

  /**
   * get the price for a given date.
   *
   * @param date of type string
   * @return float type price of the stock
   */
  float callPriceDate(String date);
}
