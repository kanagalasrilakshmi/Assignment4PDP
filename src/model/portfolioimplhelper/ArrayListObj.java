package model.portfolioimplhelper;

import java.util.ArrayList;

/**
 * Class for creating an object for creating arraylist objects.
 */
public class ArrayListObj implements IArrayListObj {
  private final ArrayList<String> tickrSymbols;
  private final ArrayList<String> prices;

  /**
   * Constructor for ArrayLiostObj.
   *
   * @param tickrSymbols of type string array list of stock tickrs
   * @param prices       of type string array list of prices
   */
  public ArrayListObj(ArrayList<String> tickrSymbols, ArrayList<String> prices) {
    this.tickrSymbols = tickrSymbols;
    this.prices = prices;
  }

  /**
   * gets the array list of tickr symbols.
   *
   * @return arraylist of tickrsymbols of type string
   */
  public ArrayList<String> getTickrSymbols() {
    return this.tickrSymbols;
  }

  /**
   * gets the array list of corresponding prices for a tickr symbols.
   *
   * @return arraylist of price for tickrsymbols of type string
   */
  public ArrayList<String> getPrices() {
    return this.prices;
  }
}
