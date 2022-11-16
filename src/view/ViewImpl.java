package view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.PrintStream;


/**
 * Class for implementing View Interface and used for displaying rigid portfolio.
 */
public class ViewImpl implements View {

  final PrintStream out;

  /**
   * Constructo for view.
   *
   * @param out takes in prinstream type object
   */
  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  /**
   * Prints the given input string when this function is called.
   *
   * @param s to be printed
   */
  public void showString(String s) {
    out.println(s);
  }

  /**
   * Shows the options that can be used by the user to run the stocks program.
   */
  public void showOptions() {
    //print the UI
    out.println("Menu: ");
    out.println("C: To create a new Portfolio.");
    out.println("V: View existing Portfolio.");
    out.println("Q: Quit the program");
    out.print("Enter your choice: ");
  }

  /**
   * Displays the list of .txt file present in the given input directory argument.
   *
   * @param rootDir is the directory where .txt files needs to be listed down
   */
  public void listTXTFiles(String rootDir) {
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for (File f : filesList) {
      if (f.isFile()) {
        // list only .json files.
        if (f.getName().contains(".txt")) {
          out.println(f.getName().split("\\.txt")[0]);
        }
      }
    }
  }

  /**
   * If an argument other than C,D,V or Q is passed this function is called to display error.
   */
  public void showOptionError() {
    out.print("\nInvalid option. Please try again.\n");
  }

  /**
   * List all the json files.
   *
   * @param rootDir is the path from which json files need to be searched
   */
  public void listJSONFiles(String rootDir) {
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for (File f : filesList) {
      if (f.isFile()) {
        // list only .json files.
        if (f.getName().contains(".json")) {
          out.println(f.getName().split("\\.json")[0]);
        }
      }
    }
  }

  /**
   * Displays the options for viewing the cost basis - I, value of portfolio on specific date - D.
   * Composition of the whole portfolio for viewing the entire portfolio.
   */
  public void showFlexibleViewOptions() {
    this.showString("Press I to view investment made in a portfolio by a " +
            "specific date");
    this.showString("Press D to view portfolio value on specific date");
    this.showString("Press P to view portfolio composition");
  }

  /**
   * View the composition of the entire portfolio.
   * @param portfolio is the json object of a tickr symbol that consists of values to be displayed.
   */
  public void viewFlexibleComposition(JSONObject portfolio) {
    // view composition of portfolio
    for (Object tickrsym : portfolio.keySet()) {
      this.showString("TICKER SYMBOL : " + (String) tickrsym);
      this.showString("NUM OF STOCKS PURCHASED/SOLD    DATE OF PURCHASE/SELL   "
              + " COMMISION FEES    STOCK PRICE");
      JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
      for (int i = 0; i < arrayObj.size(); i++) {
        JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
        this.showString(
                tickrRecord.get("no_of_stocks")
                        + "                 " +
                        "              " + (String) tickrRecord.get("date")
                        + "            " +
                        "     " + (Double) tickrRecord.get("commission_fee") +
                        "                   " + tickrRecord.get("stock_price"));
      }
    }
  }
}
