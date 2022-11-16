package view;

import java.io.PrintStream;

/**
 * View implementation that is used for displaying in flexible portfolio.
 */
public class ViewImplFlexible extends ViewImpl {
  /**
   * Constructor for view.
   *
   * @param out takes in prinstream type object
   */
  public ViewImplFlexible(PrintStream out) {
    super(out);
  }

  /**
   * Shows the options that can be used by the user to run the stocks program.
   */
  @Override
  public void showOptions() {
    //print the UI
    out.println("Menu: ");
    out.println("C: To create a new Portfolio.");
    out.println("M: To modify an existing Portfolio.");
    out.println("V: View existing Portfolio.");
    out.println("Q: Quit the program.");
    out.println("B: Get Bar graph of the program.");
    out.print("Enter your choice: ");
  }
}
