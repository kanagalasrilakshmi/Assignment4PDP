package controller;

import java.io.IOException;
import java.text.ParseException;

/**
 * Interface for the controller that implements flexible and rigid portfolio stocks program.
 */
public interface Controller {
  /**
   * function to run the Stocks implementation.
   *
   * @throws ParseException when parsing of a date fails.
   * @throws IOException    when given input is not valid
   */
  void goStocks() throws ParseException, IOException;
}
