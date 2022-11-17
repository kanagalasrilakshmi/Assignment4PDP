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
import static org.junit.Assert.assertEquals;

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
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"+"pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    assertEquals(true, portfolioObj.checkPriorDate("2022-02-02", "GOOG", path));
    assertEquals(false, portfolioObj.checkPriorDate("2019-02-02", "GOOG", path));
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
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"+"pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    boolean isValid = portfolioObj.checkValidSell(path,9,"GOOG", "2022-02-02");
    assertEquals(false, isValid);
    isValid = portfolioObj.checkValidSell(path,8,"GOOG", "2022-02-02");
    assertEquals(true, isValid);
    isValid = portfolioObj.checkValidSell(path,1,"GOOG", "2022-02-02");
    assertEquals(true, isValid);
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
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"+"pf.json";
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
    portfolioObj.modifyJson(Float.valueOf(4), 83, "2022-02-02", "GOOG", path);
    // Check buy
    assertEquals(addTickr.toString(), portfolioObj.readPortfolio(path).toString());
    addEntry = portfolioObj.makeTransactionRecord("2022-04-04",
            5, -4, "GOOG");
    listEntry.add(addEntry);
    addTickr.put("GOOG", listEntry);
    portfolioObj.modifyJson(Float.valueOf(5), -4, "2022-04-04", "GOOG", path);
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
    String path = System.getProperty("user.home") + "/Desktop/PortfolioBucket/"+"pf.json";
    portfolioObj.savePortfolio(path, addTickr);
    ArrayList<Float> values = portfolioObj.getValuesPortfolio(
            System.getProperty("user.home")+
            "/Desktop/PortfolioBucket/","pf","2021-01-01","2022-06-06",
            portfolioObj.checkDifference("2021-01-01","2022-06-06"));
    assertEquals(446.8783874511719,portfolioObj.getScale(values),0.01);
  }

}
