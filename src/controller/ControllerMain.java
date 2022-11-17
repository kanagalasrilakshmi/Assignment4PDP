package controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;

import model.Portfolio;
import view.View;
import view.ViewImpl;

/**
 * Main controller that takes in input from the user if he wants to create,
 * flexible or rigid portfolios.
 * Based on users input it goes runs controller goStocks method,
 * for flexible or rigid portfolios.
 */
public class ControllerMain implements Controller {
  private View theView;
  private Portfolio thePortfolioFlex;
  private Portfolio thePortfolioRigid;
  private Scanner in;
  private String rootDir;

  /**
   * Main controller that takes in flexible portfolio objects, rigid portfolio objects.
   *
   * @param thePortfolioRigid is the model object for creating rigid portfolio
   * @param thePortfolioFlex  is the model object for creating flexible portfolio
   * @param in                of type InputStream
   */
  public ControllerMain(Portfolio thePortfolioRigid,View view,
                        Portfolio thePortfolioFlex, InputStream in) {
    this.thePortfolioRigid = thePortfolioRigid;
    this.thePortfolioFlex = thePortfolioFlex;
    this.theView = view;
    this.in = new Scanner(in);
  }

  /**
   * function to run the Stocks implementation.
   *
   * @throws ParseException when parsing of a date fails.
   * @throws IOException    when given input is not valid
   */
  public void goStocks() throws ParseException, IOException {
    Controller controller;
    boolean done = false;
    while (!done) {
      theView.showMainOptions();
      switch (in.next()) {
        case "F":
          controller = new ControllerImplFlexible(thePortfolioFlex, theView, System.in);
          controller.goStocks();
          break;
        case "R":
          controller = new ControllerImpl(thePortfolioRigid, theView, System.in);
          controller.goStocks();
          break;
        case "Q":
          done = true;
          break;
        default:
          theView.showString("Press either F/R/Q only!!");
          break;
      }
    }
  }
}
