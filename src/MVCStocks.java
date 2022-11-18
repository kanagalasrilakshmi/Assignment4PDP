import java.io.IOException;
import java.text.ParseException;

import controller.Controller;
import controller.ControllerMain;
import model.FlexiblePortfolioImpl;
import model.Portfolio;
import model.PortfolioImpl;
import view.View;
import view.ViewImpl;

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
    Portfolio modelRigid = new PortfolioImpl();
    View view = new ViewImpl(System.out);
    Portfolio modelFlex = new FlexiblePortfolioImpl();
    Controller controller = new ControllerMain(modelRigid, view, modelFlex, System.in);
    controller.goStocks();
  }
}
