package view;

import java.io.PrintStream;

public class ViewImplFlexible extends ViewImpl{
  /**
   * Constructor for view.
   *
   * @param out  takes in prinstream type object
   */
  public ViewImplFlexible(PrintStream out) {
    super(out);
  }
  @Override
  /**
   * Shows the options that can be used by the user to run the stocks program.
   */
  public void showOptions() {
    //print the UI
    out.println("Menu: ");
    out.println("C: To create a new Portfolio.");
    out.println("V: View existing Portfolio.");
    out.println("Q: Quit the program.");
    out.println("B: Get Bar graph of the program.");
    out.print("Enter your choice: ");
  }
}
