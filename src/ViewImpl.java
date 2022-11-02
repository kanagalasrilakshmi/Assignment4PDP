import java.io.File;
import java.io.PrintStream;

public class ViewImpl implements View {

  private PrintStream out;

  public ViewImpl(PrintStream out) {
    this.out = out;
  }

  public void showString(String s) {
    out.println(s);
  }

  public void showOptions() {
    //print the UI
    out.println("Menu: ");
    out.println("C: To create a new Portfolio.");
    out.println("V: View existing Portfolio.");
    out.println("D: View existing Portfolio value for a given date.");
    out.println("Q: Quit the program");
    out.print("Enter your choice: ");
  }

  public void listJsonFiles(String rootDir) {
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

  public void showStringEntry() {
    out.print("\nEnter the string to be echoed: ");
  }

  public void showOptionError() {
    out.print("\nInvalid option. Please try again.\n");
  }

    public void showInvalidInputError(String s) {
      out.print("Invalid input error message:" + s);
      //showOptions();
  }

}
