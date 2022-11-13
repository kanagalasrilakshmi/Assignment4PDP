import java.io.IOException;
import java.text.ParseException;

import Model.FlexiblePortfolioImpl;
import Model.Portfolio;
import controller.Controller;
import controller.FlexibleControllerImpl;
import view.FlexibleViewImpl;
import view.View;


/**
 * Main Stocks class that implements Controllor, PortfolioObj(Model), View.
 * Program is ran using main method.
 */
public class MVCStocksFlexible {

  /**
   * Main method to run the whole stock program.
   *
   * @param args Takes in model object, view Object and input given by user
   * @throws ParseException if given input parsing fails. Happens if not specified in right format
   * @throws IOException    if given input is not valid
   */
  public static void main(String[] args) throws ParseException, IOException {
    Portfolio model = new FlexiblePortfolioImpl();
    View view = new FlexibleViewImpl(System.out);
    Controller controller = new FlexibleControllerImpl(model, view, System.in);
    controller.goStocks();
  }
}
