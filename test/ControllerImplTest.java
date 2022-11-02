import com.sun.org.apache.xpath.internal.operations.Equals;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class ControllerImplTest {

  @Test
  public void testGoCreate() throws ParseException, IOException {
    InputStream in = new ByteArrayInputStream("Q".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Portfolio PortfolioObj = new PortfolioImpl();
    View ViewObj = new ViewImpl(out);
    Controller controlObj = new ControllerImpl(PortfolioObj,ViewObj,in);
    controlObj.go();
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
            "Enter your choice: ",new String(bytes.toByteArray()));
  }
  @Test
  public void testGoView() throws ParseException, IOException {
    InputStream in = new ByteArrayInputStream("V Baby Q".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Portfolio PortfolioObj = new PortfolioImpl();
    View ViewObj = new ViewImpl(out);
    Controller controlObj = new ControllerImpl(PortfolioObj,ViewObj,in);
    controlObj.go();
    assertEquals("Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Choose from the list of portfolios\n" +
            "Baby\n" +
            "Enter the name of the portfolio from the displayed list\n" +
            "Company Tickr Symbol Num Stocks Purchased Stock Price\n" +
            "GOOG                  100.0                    90.5\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ",new String(bytes.toByteArray()));
  }
}