import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import controller.Controller;
import controller.ControllerImpl;
import Model.PortfolioImpl;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing View Implementation functions.
 */
public class ViewImplTest {

  @Before
  public void setUp() {
    // Flush contents in directory for every test
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket";
    if (!new File(rootDir).exists()) {
      try {
        Path path = Paths.get(rootDir);
        Files.createDirectories(path);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    File files = new File(rootDir);
    for (File file : files.listFiles()) {
      if (!file.isDirectory()) {
        file.delete();
      }
    }
  }

  @Test
  public void testViewSetRootDirectoryDefaulting() throws ParseException, IOException {
    //Checks if user tried to give invalid directory route
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "hhgg\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Invalid path given so portfolios will be stored in " + rootDir + " by default. " +
            "To change directory, quit and start again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewQuitAtStartAndCorrectDirectory() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewInvalidInputAtMenu() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nA\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: \n" +
            "Invalid option. Please try again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewCreatePf() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "j\nC\nretirementpf\nY\nGOOG\n100\nS\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Invalid path given so portfolios will be stored in " +
            System.getProperty("user.home") +
            "/Desktop/PortfolioBucket/ by default. To change directory, " +
            "quit and start again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must " +
            "be less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio retirementpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testPortfolioAlreadyExists() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir +
            "j\nC\nretirementpf\nY\nGOOG\n100\nS\nC\nretirementpf\nhealth\nY\nGOOG\n100\nS\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Invalid path given so portfolios will be stored in " +
            System.getProperty("user.home") +
            "/Desktop/PortfolioBucket/ by default. To change directory, " +
            "quit and start again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio retirementpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Portfolio with this name already exists! \n" +
            "Give another name for the portfolio you want to create:\n" +
            "Press Y to add stocks to the health portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the health portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio health\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testAtleastOneStockToSavePortfolio() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "j\nC\nretirementpf\nS\nY\nGOOG\n100\nS\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Invalid path given so portfolios will be stored in " +
            System.getProperty("user.home") +
            "/Desktop/PortfolioBucket/ by default. To change directory, " +
            "quit and start again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be less " +
            "than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Portfolio must contain at least one entry!! \n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio retirementpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewCreatePfwithInvalidStockTickrInvalidStockProportion() throws
          ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir +
            "j\nC\nretirementpf\nY\nGOOOOG\n1278\nGOOG\nsd11f\n$11%^\n100.1\n100\nS\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Invalid path given so portfolios will be stored in " +
            System.getProperty("user.home") +
            "/Desktop/PortfolioBucket/ by default. To change directory, quit " +
            "and start again.\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Invalid Tickr Symbol is entered!\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Invalid Tickr Symbol is entered!\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Only Integer Stock values are allowed. Please enter a valid Integer number.\n" +
            "Only Integer Stock values are allowed. Please enter a valid Integer number.\n" +
            "Only Integer Stock values are allowed. Please enter a valid Integer number.\n" +
            "Press Y to add stocks to the retirementpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio retirementpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewPortfolio() throws ParseException, IOException {
    // Create pfOne view create pfTwo view
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\npfOne\nY\nGOOG\n100\nS\nV\npfOne\nP\nC\npfTwo\nY\nGOOG\n" +
            "100\nS\nV\npfTwo\nP\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the pfOne portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the pfOne portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio pfOne\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "pfOne\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be less " +
            "than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the pfTwo portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the pfTwo portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio pfTwo\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "pfTwo\n" +
            "pfOne\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewPortfolioAllInvalidPortfo() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\nhealthpf\nY\nGOOG\n100\nS\nV\nhealth\nhealthpf\nP\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be less " +
            "than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio healthpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the list " +
            "of portfolios displayed below:\n" +
            "healthpf\n" +
            "Please enter a valid Portfolio name from the displayed list only!\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewPortfolioAllInvalidViewByDate() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\nhealthpf\nY\nGOOG\n100\nS\nV\nhealth\nhealthpf\nD\n" +
            "2022/02/02\n%%\n\n \n4356\nfvnbbm\n12@#\n2020-20-01\n2020-02-02\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio healthpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "healthpf\n" +
            "Please enter a valid Portfolio name from the displayed list only!\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Enter the date for which you want to fetch the portfolio in YYYY-MM-DD " +
            "format only!\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "Please enter correct format for date\n" +
            "The total value of the portfolio healthpf is 143423.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewPortfolioViewByDateMany() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\nhealthpf\nY\nGOOG\n100\nS\nV\nhealthpf\nP\nC" +
            "\nhealth\nY\nGOOG\n100\nS\nV\nhealth\nP\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must " +
            "be less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio healthpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "healthpf\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the health portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the health portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio health\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "health\n" +
            "healthpf\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }

  @Test
  public void testViewPortfolioSave() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = rootDir + "\nC\nhealthpf\nY\nGOOG\n100\nS\nV\nhealthpf\nP\nC" +
            "\nhealth\nY\nGOOG\n100\nS\nV\nhealth\nP\nQ";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
    controlObj.goStocks();
    assertEquals("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must " +
            "be less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the healthpf portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio healthpf\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "healthpf\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Give a valid name for the portfolio you want to create. " +
            "The string should not have spaces or special characters and the length must be " +
            "less than 25 characters.\n" +
            "Please enter a valid portfolio name:\n" +
            "Press Y to add stocks to the health portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Enter Valid Stock company tickr symbol\n" +
            "Enter number of stocks purchased (Integer Values are Only Allowed)\n" +
            "Press Y to add stocks to the health portfolio.\n" +
            "Press S to save the Portfolio.\n" +
            "Successfully created the portfolio health\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: Enter the name of the portfolio you want to view from the " +
            "list of portfolios displayed below:\n" +
            "health\n" +
            "healthpf\n" +
            "Press D to view portfolio value by date\n" +
            "Press P to view portfolio composition\n" +
            "Company Tickr Symbol Num Stocks\n" +
            "GOOG                  100.0\n" +
            "Menu: \n" +
            "C: To create a new Portfolio.\n" +
            "V: View existing Portfolio.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }
}