import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

import model.Portfolio;
import model.PortfolioNewStratergy;
import model.PortfolioStratergy;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the Flexible portfolios.
 */
public class PortfolioNewStrategyTest {

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
    assertEquals("[2022-01-03, 2022-01-07, 2022-01-11, 2022-01-17, 2022-01-21, 2022-01-25, 2022-01-31]", portfolioObj.getAllDatesUsingStep(
            "2022-01-01", "2022-02-02", 4).toString());
  }

  @Test
  public void getAllDatesUsingStepSatSunday() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[2022-11-01, 2022-11-07, 2022-11-11, 2022-11-15, 2022-11-21, 2022-11-25, 2022-11-29]", portfolioObj.getAllDatesUsingStep(
            "2022-11-01", "2022-12-01", 4).toString());
  }

  @Test
  public void getAllDatesUsingStepOngoing() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[2022-11-01, 2023-01-02, 2023-03-03, 2023-05-02, 2023-07-03, 2023-09-01, 2023-10-31]", portfolioObj.getAllDatesUsingStep("2022-11-01","", 60).toString());
  }

  @Test
  public void dollarCostExisting() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, 8, "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    portfolioObj.savePortfolio(path, addTickr);
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pf = strategyPortfolioObj.dollarCostExisting(tickrs, weights,
            2, 1000, "2022-11-11", addTickr);
    portfolioObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), portfolioObj.readPortfolio(path).toString());
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
    portfolioObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), portfolioObj.readPortfolio(path).toString());
  }

  @Test
  public void saveStrategyNewPf() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pfStrategyLookUp.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pf = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, "strategy_1",new JSONObject(),"Portfolio_1");
    portfolioObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), portfolioObj.readPortfolio(path).toString());
  }

  @Test
  public void saveStrategyExistingPf() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pfStrategyLookUp.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pfOld = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, "strategy_1",new JSONObject(),"Portfolio_1");
    portfolioObj.savePortfolio(path, pfOld);
    JSONObject loadedStrategyLookUp = portfolioObj.readPortfolio(path);
    JSONObject pf = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, "strategy_2",loadedStrategyLookUp,"Portfolio_1");
    portfolioObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), portfolioObj.readPortfolio(path).toString());
  }

  @Test
  public void saveStrategyExistingPf2() {
    Portfolio portfolioObj = new PortfolioNewStratergy();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pfStrategyLookUp.json";
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG");
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pfOld = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, "strategy_1",new JSONObject(),"Portfolio_1");
    portfolioObj.savePortfolio(path, pfOld);
    JSONObject loadedStrategyLookUp = portfolioObj.readPortfolio(path);
    JSONObject pf = strategyPortfolioObj.saveStrategyRecord(tickrs, weights,
            2, 4, "2022-01-01", "2022-02-02", 1000, "strategy_2",loadedStrategyLookUp,"Portfolio_2");
    portfolioObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), portfolioObj.readPortfolio(path).toString());
  }
}
