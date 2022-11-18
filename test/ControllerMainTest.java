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
import controller.ControllerMain;
import model.FlexiblePortfolioImpl;
import model.PortfolioImpl;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing  Main Controller functions to choose flexible/rigid portfolio.
 */
public class ControllerMainTest {

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
  public void testGoQuitAtStart() throws ParseException, IOException {
    String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    String inputStream = "Q";
    InputStream in = new ByteArrayInputStream(inputStream.getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    Controller controlObj = new ControllerMain(new PortfolioImpl(), new ViewImpl(out), new FlexiblePortfolioImpl(), in);
    controlObj.goStocks();
    assertEquals("Menu: \n" +
            "F: For Flexible Portfolios.\n" +
            "R: For Rigid Portfolios.\n" +
            "Q: Quit the program\n" +
            "Enter your choice: ", bytes.toString());
  }
}