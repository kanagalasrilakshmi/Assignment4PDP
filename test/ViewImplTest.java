import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

/**
 * test class for testing View functions.
 */
public class ViewImplTest {

  @Test
  public void testViewQuitAtStart() throws ParseException, IOException {
    InputStream in = new ByteArrayInputStream("Q".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewInvalidInputAtMenu() throws ParseException, IOException {
    InputStream in = new ByteArrayInputStream("A Q".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewCreate() throws ParseException, IOException {
    InputStream in = new ByteArrayInputStream("C healthpf Y goStocksOG 100 S Q".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a name for the portfolio you want to create:\n" +
            "Press Y to add to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased\n" +
            "Press Y to add to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio healthpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }
/*
  assertEquals("Menu: \n" +
                       "C: To create a new Portfolio.\n" +
                       "V: View existing Portfolio.\n" +
                       "D: View existing Portfolio value for a given date.\n" +
                       "Q: Quit the program\n" +
                       "Enter your choice: Give a name for the portfolio you want to create:\n" +
                       "Press Y to add to add stocks to the Baby portfolio.\n" +
                       "Press S to save the Portfolio.\n" +
                       "Enter Valid Stock company tickr symbol\n" +
                       "Enter number of stocks purchased\n" +
                       "Press Y to add to add stocks to the Baby portfolio.\n" +
                       "Press S to save the Portfolio.\n" +
                       "Successfully created the portfolio Baby\n" +
                       "Menu: \n" +
                       "C: To create a new Portfolio.\n" +
                       "V: View existing Portfolio.\n" +
                       "D: View existing Portfolio value for a given date.\n" +
                       "Q: Quit the program\n" +
                       "Enter your choice: ",new String(bytes.toByteArray()));*/

}