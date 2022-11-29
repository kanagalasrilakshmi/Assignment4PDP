import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import model.FlexiblePortfolioImpl;
import model.Portfolio;

import static org.junit.Assert.*;

/**
 * Test class for testing the Flexible portfolios.
 */
public class FlexiblePortfolioImplTest {

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
  public void checkPriorDate() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    assertTrue(portfolioObj.checkPriorDate("2022-02-02", "GOOG", path));
    assertFalse(portfolioObj.checkPriorDate("2019-02-02", "GOOG", path));
  }

  @Test
  public void checkIfToSellValid() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    boolean isValid = portfolioObj.checkValidSell(path, 9, "GOOG",
            "2022-02-02");
    // Sell more than purchase
    assertFalse(isValid);
    isValid = portfolioObj.checkValidSell(path, 8, "GOOG", "2022-02-02");
    // Sell equal to  purchase
    assertTrue(isValid);
    isValid = portfolioObj.checkValidSell(path, 1, "GOOG", "2022-02-02");
    // Sell lesser than  purchase
    assertTrue(isValid);
  }

  @Test
  public void checkSavePfAndReadPf() throws IOException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    Scanner scanner = new Scanner(Paths.get(path), StandardCharsets.UTF_8.name());
    String content = scanner.useDelimiter("\\A").next();
    scanner.close();
    // Check save
    assertEquals(addTickr.toString(), content);
    // Check read
    assertEquals(addTickr.toString(), portfolioObj.readPortfolio(path).toString());
  }

  @Test
  public void modifyPf() throws IOException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    addEntry = portfolioObj.makeTransactionRecord("2022-02-02",
            4, 83, "GOOG");
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    JSONObject portfolio = portfolioObj.readPortfolio(path);
    portfolio = portfolioObj.modifyJson(Float.valueOf(4), 83, "2022-02-02",
            "GOOG", portfolio);
    portfolioObj.savePortfolio(path, portfolio);
    // Check buy
    assertEquals(addTickr.toString(), portfolioObj.readPortfolio(path).toString());
    addEntry = portfolioObj.makeTransactionRecord("2022-04-04",
            5, -4, "GOOG");
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    JSONObject portfo = portfolioObj.readPortfolio(path);
    portfolio = portfolioObj.modifyJson(Float.valueOf(5), -4, "2022-04-04",
            "GOOG", portfo);
    portfolioObj.savePortfolio(path, portfolio);
    // Check sell
    assertEquals(addTickr.toString(), portfolioObj.readPortfolio(path).toString());
  }

  @Test
  public void checkScaleVal() throws ParseException, FileNotFoundException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    ArrayList<Float> values = portfolioObj.getValuesPortfolio(
            System.getProperty("user.home") +
                    "/Desktop/PortfolioBucket/", "pf", "2021-01-01",
            "2022-06-06",
            portfolioObj.checkDifference("2021-01-01", "2022-06-06"));
    assertEquals(446.8783874511719, portfolioObj.getScale(values), 0.01);
  }

  @Test
  public void checkDifferenceTest() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    assertEquals(520, portfolioObj.checkDifference("2021-01-01", "2022-06-06"), 0.01);
  }

  @Test
  public void checkValidDatesTest1() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    assertFalse(portfolioObj.checkValidDates("2021-02-02", "2021-01-17"));
  }

  @Test
  public void checkValidDatesTest2() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    assertTrue(portfolioObj.checkValidDates("2021-01-17", "2021-02-02"));
  }

  @Test
  public void checkDatesDisplayEveryDay() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    String expected = "[2021-01-17, 2021-01-18, 2021-01-19, 2021-01-20, 2021-01-21, " +
            "2021-01-22, 2021-01-23, 2021-01-24, 2021-01-25, 2021-01-26, 2021-01-27," +
            " 2021-01-28, 2021-01-29, 2021-01-30, 2021-01-31, 2021-02-01, 2021-02-02]";
    assertEquals(expected, portfolioObj.getDatesDisplay(
            "2021-01-17", "2021-02-02",
            portfolioObj.checkDifference("2021-01-17", "2021-02-02")).toString());
  }

  @Test
  public void checkDatesDisplayEveryFiveDays() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    String expected = "[2021-01-17, 2021-01-22, 2021-01-27, 2021-02-01, 2021-02-06, 2021-02-11, " +
            "2021-02-16, 2021-02-21, 2021-02-26, 2021-03-03, 2021-03-08, 2021-03-13, 2021-03-18," +
            " 2021-03-23, 2021-03-28, 2021-04-02]";
    assertEquals(expected, portfolioObj.getDatesDisplay(
            "2021-01-17", "2021-04-02",
            portfolioObj.checkDifference("2021-01-17", "2021-04-02")).toString());
  }

  @Test
  public void checkDatesDisplayEveryMonth() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    String expected = "[Jan 2020, Feb 2020, Mar 2020, Apr 2020, May 2020, Jun 2020, Jul 2020, " +
            "Aug 2020, Sep 2020, Oct 2020, Nov 2020, Dec 2020, Jan 2021]";
    assertEquals(expected, portfolioObj.getDatesDisplay(
            "2020-01-17", "2021-02-02",
            portfolioObj.checkDifference("2020-01-17", "2021-02-02")).toString());
  }

  @Test
  public void checkDatesDisplayEveryThreeMonth() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    String expected = "[Jan 2018, Apr 2018, Jul 2018, Oct 2018, Jan 2019, Apr 2019, Jul 2019, " +
            "Oct 2019, Jan 2020, Apr 2020, Jul 2020, Oct 2020, Jan 2021]";
    assertEquals(expected, portfolioObj.getDatesDisplay(
            "2018-01-17", "2021-02-02",
            portfolioObj.checkDifference("2018-01-17", "2021-02-02")).toString());
  }

  @Test
  public void checkDatesDisplayYearWise() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    String expected = "[2016, 2017, 2018, 2019, 2020]";
    assertEquals(expected, portfolioObj.getDatesDisplay(
            "2016-01-17", "2021-02-02",
            portfolioObj.checkDifference("2016-01-17", "2021-02-02")).toString());
  }

  @Test
  public void costBasisTest() throws ParseException {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    Float costBasis = portfolioObj.getCostBasis(path, "2022-02-02");
    assertEquals(23155.7, costBasis, 0.9);
  }
}
