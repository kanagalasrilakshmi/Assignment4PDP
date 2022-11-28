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
import controller.ControllerImplFlexible;
import model.FlexiblePortfolioImpl;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing  the flexible portfolio's controller functions.
 */
public class ControllerImplFlexibleTest {

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
    public void testGetAndValidDateSell() throws ParseException {
        String inputStream = "20/36/22\n2022/22/23\n2023-02-02\n2022-02-02";
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Controller controlObj = new ControllerImplFlexible(new FlexiblePortfolioImpl(),
                new ViewImpl(out), in);
        ((ControllerImplFlexible)
                controlObj).getAndValidateDate("Please enter correct format for date:");
        // Ends only when valid date is entered.
        assertEquals("Please enter correct format for date:\n" +
                "Please enter correct format for date:\n" +
                "Please enter correct format for date:\n" +
                "You can only enter past date or present(if after 9:30am).! Please enter new date:\n" +
                "Please enter correct format for date:\n", bytes.toString());
    }

    @Test
    public void testGoQuitAtStart() throws ParseException, IOException {
        String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
        String inputStream = rootDir + "\nQ";
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Controller controlObj = new ControllerImplFlexible(new FlexiblePortfolioImpl(),
                new ViewImpl(out), in);
        controlObj.goStocks();
        assertEquals("Give a valid input path where you want to" +
                " store your portfolios. For example: /Users/PDP/PortfolioBucket/\n" +
                "Menu: \n" +
                "C: To create a new Portfolio.\n" +
                "M: To modify an existing Portfolio.\n" +
                "V: View existing Portfolio.\n" +
                "Q: Quit the program.\n" +
                "B: Get Bar graph of the program.\n" +
                "Enter your choice: ", bytes.toString());
    }

    @Test
    public void negativeCommission() {
        String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
        String inputStream = rootDir + "\n-2\n2";
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Controller controlObj = new ControllerImplFlexible(new FlexiblePortfolioImpl(),
                new ViewImpl(out), in);
        ((ControllerImplFlexible) controlObj).getValidCommission();
        assertEquals("Enter the commission fees:\n" +
                "Enter only float or integer values:\n" +
                "Negative commission values are not allowed!\n" +
                "Enter the commission fees:\n", bytes.toString());
    }

    @Test
    public void testGetAndValidDatePurchase() throws ParseException {
        String inputStream = "2022/22/23\n20/36/22\n22ghdh\n2023-02-02\n2022-02-02";
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Controller controlObj = new ControllerImplFlexible(new FlexiblePortfolioImpl(),
                new ViewImpl(out), in);
        ((ControllerImplFlexible) controlObj).getAndValidateDate("Please enter correct " +
                "format for date:");
        // Ends only when valid date is entered.
        assertEquals("Please enter correct format for date:\n" +
                "Please enter correct format for date:\n" +
                "Please enter correct format for date:\n" +
                "Please enter correct format for date:\n" +
                "You can only enter past date or present(if after 9:30am).! Please enter new date:\n" +
                "Please enter correct format for date:\n", bytes.toString());
    }
}