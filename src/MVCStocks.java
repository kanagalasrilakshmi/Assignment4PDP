import java.io.IOException;
import java.text.ParseException;

import Model.FlexiblePortfolioImpl;
import controller.Controller;
import controller.ControllerImpl;
import Model.Portfolio;
import Model.PortfolioImpl;
import controller.Controller;
import controller.ControllerImpl;
import controller.ControllerImplFlexible;
import view.View;
import view.ViewImpl;
import view.ViewImplFlexible;

/**
 * Main Stocks class that implements Controllor, PortfolioObj(Model), View.
 * Program is ran using main method.
 */
public class MVCStocks {

  /**
   * Main method to run the whole stock program.
   *
   * @param args Takes in model object, view Object and input given by user
   * @throws ParseException if given input parsing fails. Happens if not specified in right format
   * @throws IOException    if given input is not valid
   */
  public static void main(String[] args) throws ParseException, IOException {
    Portfolio model = new FlexiblePortfolioImpl();
    View view = new ViewImplFlexible(System.out);
    Controller controller = new ControllerImplFlexible(model, view, System.in);
    controller.goStocks();
  }
}
