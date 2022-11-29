import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

import controller.Controller;
import controller.ControllerGUI;
import controller.ControllerImpl;
import controller.ControllerImplFlexible;
import controller.ControllerImplGUI;
import controller.ControllerMain;
import model.FlexiblePortfolioImpl;
import model.Portfolio;
import model.PortfolioNewStratergy;
import model.PortfolioStratergy;
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
   * Main method to run the whole stock program.
   *
   * @param args Takes in model object, view Object and input given by user
   * @throws ParseException if given input parsing fails. Happens if not specified in right format
   * @throws IOException    if given input is not valid
   */
  public static void main(String[] args) throws ParseException, IOException {
    PortfolioStratergy modelFlex = new PortfolioNewStratergy();
    //View guiView = new ViewImpl(System.out);
    GUIView guiView = new GUIViewImpl();
    //Controller controller = new ControllerImplFlexible(modelFlex,guiView,System.in);
    //controller.goStocks();
    ControllerGUI controllerGui = new ControllerImplGUI(modelFlex, guiView);
    controllerGui.goStocks();

  }
}
