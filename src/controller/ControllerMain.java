package controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Scanner;

import model.Portfolio;
import view.View;
import view.ViewMain;

public class ControllerMain implements Controller {
  private View theViewFlex;
  private View theViewRigid;
  private View theViewMain;
  private Portfolio thePortfolioFlex;
  private Portfolio thePortfolioRigid;
  private Scanner in;
  private String rootDir;

  public ControllerMain(Portfolio thePortfolioRigid, View theViewRigid,
                        Portfolio thePortfolioFlex, View theViewFlex, InputStream in) {
    this.thePortfolioRigid = thePortfolioRigid;
    this.thePortfolioFlex = thePortfolioFlex;
    this.theViewRigid = theViewRigid;
    this.theViewFlex = theViewFlex;
    this.theViewMain = new ViewMain(System.out);
    this.in = new Scanner(in);
  }

  /**
   * function to run the Stocks implementation.
   *
   * @throws ParseException when parsing of a date fails.
   * @throws IOException    when given input is not valid
   */
  public void goStocks() throws ParseException, IOException {
    theViewMain.showString("Type F if you want to choose flexible portfolios");
    theViewMain.showString("Type R if you want to choose Rigid portfolios");
    Controller controller;
    boolean done = false;
    while (!done) {
      switch (in.next()) {
        case "F":
          controller = new ControllerImplFlexible(thePortfolioFlex, theViewFlex, System.in);
          controller.goStocks();
          done = true;
          break;
        case "R":
          controller = new ControllerImpl(thePortfolioRigid, theViewRigid, System.in);
          controller.goStocks();
          done = true;
          break;
        default:
          theViewMain.showString("Press either F/R only!!");
          break;
      }
    }
  }

  public void setDirectory(String rootDirUser) {
  }

  public String getValidPfName(String rootDir, String extension) {
    return "";
  }
}
