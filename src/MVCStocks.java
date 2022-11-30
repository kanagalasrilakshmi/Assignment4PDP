import java.io.IOException;
import java.text.ParseException;

import controller.Controller;
import controller.ControllerGUI;
import controller.ControllerImplGUI;
import controller.ControllerMain;
import model.Portfolio;
import model.PortfolioImpl;
import model.PortfolioStratergy;
import model.FlexiblePortfolioImpl;
import model.PortfolioNewStratergy;
import view.GUIView;
import view.GUIViewImpl;
import view.View;
import view.ViewImpl;

/**
 * Main Stocks class that implements Controllor, PortfolioObj(Model), View.
 * Program is ran using main method.
 */
public class MVCStocks {

  /**
   * Main method to run the whole stocks program.
   * If 'gui' is given as input argument then graphical interface stocks program is ran.
   * If 'text' is given as input argument then text based interface stocks program is ran.
   *
   * @param args Takes in model object, view Object and input given by user
   * @throws ParseException if given input parsing fails. Happens if not specified in right format
   * @throws IOException    if given input is not valid
   */
  public static void main(String[] args) throws ParseException, IOException {

    if (args[0].equals("gui")) {
      PortfolioStratergy modelFlex = new PortfolioNewStratergy();
      GUIView guiView = new GUIViewImpl();
      ControllerGUI controllerGui = new ControllerImplGUI(modelFlex, guiView);
      controllerGui.goStocks();
    } else if (args[0].equals("text")) {
      Portfolio modelRigid = new PortfolioImpl();
      View view = new ViewImpl(System.out);
      Portfolio modelFlex = new FlexiblePortfolioImpl();
      Controller controller = new ControllerMain(modelRigid, view, modelFlex, System.in);
      controller.goStocks();
    }
  }
}
