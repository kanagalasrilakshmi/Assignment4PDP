import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import org.junit.Before;

public class ViewImplTest {

  @Before
  public void setUp() throws Exception {
    // default 32411
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket";
    File files = new File(rootDir);
    for(File file: files.listFiles())
      if (!file.isDirectory())
        file.delete();
  }


  @ Test
  //new folder directory
  //    rootDir + "hhgg\nQ";
  // wrong path using deafult path
  public void testViewSetRootDirectoryDeafaulting() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "hhgg\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(),new ViewImpl(out),in);
    controlObj.go();
    assertEquals("", new String(bytes.toByteArray()));
  }

  @Test
  public void testViewQuitAtStartAndCorrectDirectory() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(),new ViewImpl(out),in);
    controlObj.go();
    assertEquals("Give a valid input path where you want to store the created portfolios!\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", new String(bytes.toByteArray()));
  }
  @Test
  public void testViewInvalidInputAtMenu() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nA\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(),new ViewImpl(out),in);
    controlObj.go();
    assertEquals("Give a valid input path where you want to store the created portfolios!\n" +
            "Menu: \n" +
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
            "Enter your choice: ", new String(bytes.toByteArray()));
  }

  @Test
  public void testViewCreatePf() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\nretirementpf\nY\nGOOG\n100\nS\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(),new ViewImpl(out),in);
    controlObj.go();
    assertEquals("Give a valid input path where you want to store the created portfolios!\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a name for the portfolio you want to create,(The string should not have spaces,null,emptystring,and length less than 25 must be entered in a single word)\n" +
            "Press Y to add to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased\n" +
            "Press Y to add to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Please Enter Either S/Y only!!\n" +
            "Press Y to add to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio retirementpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", new String(bytes.toByteArray()));
  }

  @Test
  public void testViewPortfolio() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\nhealthpf\nY\nGOOG\n100\nS\nV\nhealthpf\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(),new ViewImpl(out),in);
    controlObj.go();
    assertEquals("Give a valid input path where you want to store the created portfolios!\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a name for the portfolio you want to create,(The string should not have spaces,null,emptystring,and length less than 25 must be entered in a single word)\n" +
            "Press Y to add to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased\n" +
            "Press Y to add to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Please Enter Either S/Y only!!\n" +
            "Press Y to add to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio healthpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Choose from the list of portfolios\n" +
            "healthpf\n" +
            "Enter the name of the portfolio from the displayed list\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "D: View existing Portfolio value for a given date.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", new String(bytes.toByteArray()));
  }
  //"\n\
}