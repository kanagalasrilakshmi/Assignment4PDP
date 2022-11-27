import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import model.FlexiblePortfolioImpl;
import model.Portfolio;
import model.PortfolioNewStratergy;
import model.PortfolioStratergy;

import static org.junit.Assert.assertEquals;

/**
 * Test class for testing the Flexible portfolios.
 */
public class PortfolioNewStratergyTest {

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
  public void validateTickrEntries() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[GOOG]", String.valueOf(portfolioObj.validateTickrEntries("GOOG")));
    assertEquals("[]", String.valueOf(portfolioObj.validateTickrEntries("hfgbs")));
    assertEquals("[]", String.valueOf(portfolioObj.validateTickrEntries("goog")));
    assertEquals("[GOOG, MSFT, TSLA]", String.valueOf(portfolioObj.validateTickrEntries("GOOG, MSFT,TSLA")));
    assertEquals("[GOOG, TSLA, MSFT]", String.valueOf(portfolioObj.validateTickrEntries("GOOG,  TSLA,  MSFT")));
  }

  @Test
  public void validateWeightEntries() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[10.0, 10.0, 10.0, 10.0, 60.0]", String.valueOf(portfolioObj.validateWeightEntries("10 ,10  ,10 ,10, 60", 5)));
    assertEquals("[]", String.valueOf(portfolioObj.validateWeightEntries("50,50", 3)));
    assertEquals("[]", String.valueOf(portfolioObj.validateWeightEntries("20,70,10", 2)));
    assertEquals("[25.5, 24.5, 50.0]", String.valueOf(portfolioObj.validateWeightEntries("25.5, 24.5,50", 3)));
  }

  @Test
  public void getAllDatesUsingStep() {
    PortfolioStratergy portfolioObj = new PortfolioNewStratergy();
    assertEquals("[2022-01-01, 2022-01-05, 2022-01-09, 2022-01-13, 2022-01-17, 2022-01-21, 2022-01-25, 2022-01-29, 2022-02-02]", portfolioObj.getAllDatesUsingStep("2022-01-01", "2022-02-02", 4));
  }

  @Test
  public void dollarCostExisting() {
    Portfolio portfolioObj = new FlexiblePortfolioImpl();
    PortfolioStratergy strategyPortfolioObj = new PortfolioNewStratergy();
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/" + "pf.json";
    JSONObject addTickr = new JSONObject();
    JSONObject addEntry = portfolioObj.makeTransactionRecord("2022-01-01",
            7, Integer.valueOf(8), "GOOG");
    JSONArray listEntry = new JSONArray();
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    portfolioObj.savePortfolio(path, addTickr);
    ArrayList<String> tickrs = new ArrayList<>();
    tickrs.add("GOOG") ;
    tickrs.add("MSFT");
    ArrayList<Float> weights = new ArrayList<>();
    weights.add(50F);
    weights.add(50F);
    JSONObject pf = strategyPortfolioObj.dollarCostExisting(tickrs,weights ,2,1000, "2022-11-11", addTickr);
    portfolioObj.savePortfolio(path, pf);
    assertEquals(pf.toString(), portfolioObj.readPortfolio(path).toString());
  }
}
