import model.FileOperation;
import model.FileOperationImplementation;
import model.PortfolioStratergy;
import model.PortfolioNewStratergy;
import model.Portfolio;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Test class for testing the Flexible portfolios.
 */
public class PortfolioNewStrategyTest {
  FileOperation fileObj = new FileOperationImplementation();

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
    for (File file : Objects.requireNonNull(files.listFiles())) {
      if (!file.isDirectory()) {
        file.delete();
      }
    }
  }

  @Test
  public void validateTickrEntries() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[GOOG]", String.valueOf(
            portfolioObj.validateTickrEntries("GOOG")));
    assertEquals("[]", String.valueOf(portfolioObj.validateTickrEntries("hfgbs")));
    assertEquals("[]", String.valueOf(portfolioObj.validateTickrEntries("goog")));
    assertEquals("[GOOG, MSFT, TSLA]", String.valueOf(
            portfolioObj.validateTickrEntries("GOOG, MSFT,TSLA")));
    assertEquals("[GOOG, TSLA, MSFT]", String.valueOf(
            portfolioObj.validateTickrEntries("GOOG,  TSLA,  MSFT")));
    assertEquals("[]",
            String.valueOf(portfolioObj.validateTickrEntries(",,,GOOG,*@,234,TSLA," +
            "MSFT")));
  }

  @Test
  public void validateWeightEntries() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[10.0, 10.0, 10.0, 10.0, 60.0]", String.valueOf(
            portfolioObj.validateStockWeightEntries("10 ,10  ,10 ,10, 60", 5)));
    assertEquals("[]", String.valueOf(portfolioObj.validateStockWeightEntries(
            "50,50", 3)));
    assertEquals("[]", String.valueOf(portfolioObj.validateStockWeightEntries(
            "20,70,10", 2)));
    assertEquals("[25.5, 24.5, 50.0]", String.valueOf(
            portfolioObj.validateStockWeightEntries("25.5, 24.5,50", 3)));
  }

  @Test
  public void getAllDatesUsingStep() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[2022-01-03, 2022-01-07, 2022-01-11, 2022-01-17, 2022-01-21, " +
                    "2022-01-25, 2022-01-31]",
            portfolioObj.getAllDatesUsingStep(
                    "2022-01-01", "2022-02-02", 4).toString());
  }

  @Test
  public void getAllDatesUsingStepSatSunday() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[2022-11-01, 2022-11-07, 2022-11-11, 2022-11-15, 2022-11-21, " +
                    "2022-11-25, 2022-11-29]",
            portfolioObj.getAllDatesUsingStep(
                    "2022-11-01", "2022-12-01", 4).toString());
  }

  @Test
  public void getAllDatesUsingStepOngoing() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[2022-11-01, 2023-01-02, 2023-03-03, 2023-05-02, 2023-07-03, " +
                    "2023-09-01, 2023-10-31]",
            portfolioObj.getAllDatesUsingStep(
                    "2022-11-01", "", 60).toString());
  }

  @Test
  public void dollarCostOnSpecificDate() throws ParseException {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, 8, "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    fileObj.savePortfolio(path, addTickr);
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pf = strategyPortfolioObj.dollarCostExisting(tickrs, weights,
            2, 1000, "2022-11-11", addTickr);
    fileObj.savePortfolio(path, pf);
    // Check if contents match
    assertEquals(pf.toString(), fileObj.readPortfolio(path).toString());
    // Check cost basis
    assertEquals("24157.72", String.valueOf(portfolioObj.getCostBasis(path, "2022-11-11")));
    assertEquals("24157.72", String.valueOf(portfolioObj.getCostBasis(path, "2022-12-11")));
  }

  @Test
  public void dollarCostOnSpecificDateValue() throws ParseException, FileNotFoundException {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, 8, "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    fileObj.savePortfolio(path, addTickr);
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pf = strategyPortfolioObj.dollarCostExisting(tickrs, weights,
            2, 1000, "2022-11-11", addTickr);
    fileObj.savePortfolio(path, pf);
    // Check if contents match
    assertEquals(pf.toString(), fileObj.readPortfolio(path).toString());
    // Check cost basis
    assertEquals("1751.71", String.valueOf(portfolioObj.portfolioValueDate(
            System.getProperty("user.home") + "/Desktop/PortfolioBucket/" , "pf","2022-11-11")));
    assertEquals("0.0", String.valueOf(portfolioObj.portfolioValueDate(
            System.getProperty("user.home") + "/Desktop/PortfolioBucket/" , "pf","2022-12-12")));
  }

  @Test
  public void dollarCostNew() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pf = strategyPortfolioObj.startToFinishDollarCost(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000);
    fileObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), fileObj.readPortfolio(path).toString());
  }

  @Test
  public void startToFinishNewPfValueByDate() throws ParseException, FileNotFoundException {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"
            + "pfStrategyLookUp.json";
    String pfPath = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pfStrat = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000,
            "strategy_1", new JSONObject(), "pf");
    fileObj.savePortfolio(path, pfStrat);
    JSONObject updated = strategyPortfolioObj.startToFinishDollarCostPresent(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, new JSONObject());
    fileObj.savePortfolio(pfPath, updated);
    // Check if strategy record persists.
    assertEquals(pfStrat.toString(), fileObj.readPortfolio(path).toString());
    String root = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    // Check value out of range (future date)
    assertEquals("1482.66", String.valueOf(portfolioObj.portfolioValueDate(
            root , "pf", "2022-11-11")));
    assertEquals("0.0", String.valueOf(portfolioObj.portfolioValueDate(
            root , "pf", "2024-11-11")));
    // Check value within range
    // Jan 1,2022 is a saturday so first transaction is done on Jan 3,2022
    assertEquals("0.0", String.valueOf(portfolioObj.portfolioValueDate(
            root , "pf","2022-01-01")));
    assertEquals("907.94995", String.valueOf(portfolioObj.portfolioValueDate(
            root , "pf", "2022-01-18")));
    assertEquals("1185.48", String.valueOf(portfolioObj.portfolioValueDate(
            root, "pf","2022-01-24")));
    assertEquals("1880.76", String.valueOf(portfolioObj.portfolioValueDate(
            root , "pf", "2022-02-02")));
  }

  @Test
  public void startToFinishNewPfNew() throws ParseException {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"
            + "pfStrategyLookUp.json";
    String pfPath = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pfStrat = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000,
            "strategy_1", new JSONObject(), "pf");
    fileObj.savePortfolio(path, pfStrat);
    JSONObject updated = strategyPortfolioObj.startToFinishDollarCostPresent(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, new JSONObject());
    fileObj.savePortfolio(pfPath, updated);
    // Check if strategy record persists.
    assertEquals(pfStrat.toString(), fileObj.readPortfolio(path).toString());
    // Check cost basis out of range (future date)
    assertEquals("6016.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2022-11-11")));
    assertEquals("6016.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2024-11-11")));
    // Check cost basis within range
    // Jan 1,2022 is a saturday so first transaction is done on Jan 3,2022
    assertEquals("0.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2022-01-02")));
    assertEquals("3010.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2022-01-18")));
    assertEquals("4012.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2022-01-24")));
    assertEquals("6016.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2022-02-02")));
    assertEquals("6016.0", String.valueOf(portfolioObj.getCostBasis(pfPath, "2022-02-30")));
  }

  @Test
  public void saveStrategyExistingPf() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"
            + "pfStrategyLookUp.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pfOld = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000,
            "strategy_1", new JSONObject(), "Portfolio_1");
    fileObj.savePortfolio(path, pfOld);
    JSONObject loadedStrategyLookUp = fileObj.readPortfolio(path);
    JSONObject pf = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000,
            "strategy_2", loadedStrategyLookUp, "Portfolio_1");
    fileObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), fileObj.readPortfolio(path).toString());
  }

  @Test
  public void saveStrategyExistingPf2() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"
            + "pfStrategyLookUp.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pfOld = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000,
            "strategy_1", new JSONObject(), "Portfolio_1");
    fileObj.savePortfolio(path, pfOld);
    JSONObject loadedStrategyLookUp = fileObj.readPortfolio(path);
    JSONObject pf = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000,
            "strategy_2", loadedStrategyLookUp, "Portfolio_2");
    fileObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), fileObj.readPortfolio(path).toString());
  }

  @Test
  public void checkDuplicateentries() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    tickrs.add("GOOG");
    tickrs.add("AAL");
    tickrs.add("UBER");
    assertTrue(portfolioObj.checkDuplicates(tickrs));
  }

  @Test
  public void checkFormatString() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    String tickrBatchCheck = ",,,GOOG,*@,234,TSLA,MSFT";
    assertTrue(portfolioObj.checkforInvalidcharacters(tickrBatchCheck));
    assertFalse(portfolioObj.checkforInvalidcharacters("GOOG,UBER"));
    assertTrue(portfolioObj.checkforInvalidcharacters(",,,GOOG"));
    assertTrue(portfolioObj.checkforInvalidcharacters("GOOG,,,,UBER,,,"));
    assertFalse(portfolioObj.checkforInvalidcharacters("GOOG,,,,,"));
    assertTrue(portfolioObj.checkforInvalidcharacters("GOOG,,,**1234()878,UBER"));
    assertTrue(portfolioObj.checkforInvalidcharacters("GOOG,,,**1234()878,UBER,,,,"));
    assertTrue(portfolioObj.checkforInvalidcharacters("GOOG,,,**1234()878,UBER,,, ,"));
    assertFalse(portfolioObj.checkforInvalidcharacters("GOOG,UBER, ,"));
  }

  @Test
  public void checkFormatWeight() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertTrue(portfolioObj.validateWeightFormat("10.4,89.6"));
    assertFalse(portfolioObj.validateWeightFormat("10.4jghkjgjh,89.6"));
    assertFalse(portfolioObj.validateWeightFormat("10.4,,,,89.6"));
    assertTrue(portfolioObj.validateWeightFormat("10.4,89.6,,,,,"));
    assertFalse(portfolioObj.validateWeightFormat("10.4,89.6,,,,,**,,"));
    assertFalse(portfolioObj.validateWeightFormat(",,,,,10.4,89.6,,,,,"));
  }
}
