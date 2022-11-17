package view;

import org.json.simple.JSONObject;

import java.io.PrintStream;


public class ViewMain implements View {
  final PrintStream out;

  /**
   * Shows the options that can be used by the user to run the stocks program.
   */
  public ViewMain(PrintStream out) {
    this.out = out;
  }

  public void showOptions() {
    // do nothing.
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
   * Displays the list of .txt file present in the given input directory argument.
   *
   * @param rootDir is the directory where .txt files needs to be listed down
   */
  public void listTXTFiles(String rootDir) {
    // do nothing.
  }

  /**
   * If an argument other than C,D,V or Q is passed this function is called to display error.
   */
  public void showOptionError() {
    // do nothing.
  }

  /**
   * List all the json files.
   *
   * @param rootDir is the path from which json files need to be searched
   */
  public void listJSONFiles(String rootDir) {
    // do nothing.
  }

  /**
   * View the composition of the entire portfolio.
   *
   * @param portfolio is the json object of a tickr symbol that consists of values to be displayed.
   */
  public void viewFlexibleComposition(JSONObject portfolio) {
    // do nothing.
  }

  /**
   * Shows the options that can be used by the user to run the stocks program.
   */
  public void showFlexibleViewOptions() {
    // do nothing.
  }
}
