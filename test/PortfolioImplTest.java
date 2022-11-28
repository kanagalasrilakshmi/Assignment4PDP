import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;

import controller.Controller;
import controller.ControllerImpl;
import model.Portfolio;
import model.PortfolioImpl;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing  Portfolio (Model) functions.
 */
public class PortfolioImplTest {

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
    public void checkIfDateInRightFormatCorrect() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(true, portfolioObj.checkIfRightFormat("2022-02-01"));
    }

    @Test
    public void checkIfDateInRightFormatWrong1() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkIfRightFormat("01/02/2020"));
    }

    @Test
    public void checkIfDateInRightFormatWrong2() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkIfRightFormat("01022020"));
    }

    @Test
    public void checkIfDateInRightFormatWrong3() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkIfRightFormat("01-02-2020"));
    }

    @Test
    public void ifPortfolioNameRightFormatValid() {
        // Without any white spaces
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(true, portfolioObj.checkValidpfName("healthPortfolio"));
    }

    @Test
    public void ifPortfolioNameRightFormatInvalid1() {
        // With any white spaces is invalid
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkValidpfName("Health Portfolio"));
    }

    @Test
    public void ifPortfolioNameRightFormatInvalidEmptyName() {
        // With any white spaces is invalid
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkValidpfName(""));
    }

    @Test
    public void ifPortfolioNameRightFormatInvalidNullName() {
        // With any white spaces is invalid
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkValidpfName(null));
    }

    @Test
    public void ifPortfolioNameRightFormatInvalidBigName() {
        // Portfolio name greater than 25 letters not allowed
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkValidpfName("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    public void ifPortfolioNameRightFormatInvalidSpecialCharacters() {
        // Name greater than 25 letters not allowed
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkValidpfName("ab*b"));
    }

    @Test
    public void ifPortfolioNameRightFormatInvalidNumbers() {
        // Name greater than 25 letters not allowed
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkValidpfName("2345678"));
    }

    @Test
    public void checkDirectoryNameEndingValid() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(true, portfolioObj.checkLastEndingCharacter("/Users/"));
    }

    @Test
    public void checkDirectoryNameEndingInvalid() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkLastEndingCharacter("/Users"));
    }

    @Test
    public void checkValidateTickrSymbolValid() {
        Portfolio portfolioObj = new PortfolioImpl();
        try {
            assertEquals(true, portfolioObj.validateTickrSymbol("AAL"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkValidateTickrSymbolInvalid() {
        Portfolio portfolioObj = new PortfolioImpl();
        try {
            assertEquals(false, portfolioObj.validateTickrSymbol("YZ"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void checkTodayDateAndTimePastDate() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkTodayDateAndTime("2020-12-12"));
    }

    @Test
    public void checkTodayDateAndTimeFutureDate() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkTodayDateAndTime("2024-11-12"));
    }

    @Test
    public void checkTodayDateAndTimeInvalidDateFormat1() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkTodayDateAndTime("2024/11/12"));
    }

    @Test
    public void checkTodayDateAndTimeInvalidDateFormat2() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkTodayDateAndTime("20241112"));
    }

    @Test
    public void checkFutureDateForFutureDate() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(true, portfolioObj.checkFutureDate("2028-11-23"));
    }

    @Test
    public void checkFutureDateForPresentDate() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkFutureDate(String.valueOf(LocalDate.now())));
    }

    @Test
    public void checkFutureDateForPastDate() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkFutureDate("1998-06-01"));
    }

    @Test
    public void checkFutureDateForWrongFormat1() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkFutureDate("123435436"));
    }


    @Test
    public void checkFutureDateForWrongFormat2() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkFutureDate("1234-35-436"));
    }

    @Test
    public void checkFutureDateForWrongFormat3() {
        Portfolio portfolioObj = new PortfolioImpl();
        assertEquals(false, portfolioObj.checkFutureDate("1234/11/11"));
    }

    @Test
    public void testFileCreatedAndContentsExist() throws ParseException, IOException {
        String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket";
        if (!new File(rootDir).exists()) {
            try {
                Path path = Paths.get(rootDir);
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String root = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
        String inputStream = root + "F\nC\nretire\nY\nGOOG\n100\nS\nQ";
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
        controlObj.goStocks();
        String text = "";
        // Read all contents of the file
        try {
            text = new String(Files.readAllBytes(Paths.get(root + "retire" + ".txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals("Company Tickr Symbol,Num Of Stocks\n" +
                "GOOG,100", text);
    }

    @Test
    public void testPortfolioValues() throws IOException, ParseException {
        String rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
        if (!new File(rootDir).exists()) {
            try {
                Path path = Paths.get(rootDir);
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String root = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
        String inputStream = root + "F\nC\nnewpf\nY\nGOOG\n100\nS\nQ";
        InputStream in = new ByteArrayInputStream(inputStream.getBytes());
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        Controller controlObj = new ControllerImpl(new PortfolioImpl(), new ViewImpl(out), in);
        controlObj.goStocks();

        Portfolio newModel = new PortfolioImpl();
        newModel.portfolioValueDate(rootDir, "newpf", "2017-02-10");
        assertEquals(81367.0, newModel.portfolioValueDate(rootDir, "newpf", "2017-02-10"),
                0.01);
    }
}