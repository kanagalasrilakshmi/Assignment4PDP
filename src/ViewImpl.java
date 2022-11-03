import java.io.File;
import java.io.PrintStream;

public class ViewImpl implements View {

  private final PrintStream out;

  /**
   * Constructo for view.
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
    out.println("D: View existing Portfolio value for a given date.");
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

}
