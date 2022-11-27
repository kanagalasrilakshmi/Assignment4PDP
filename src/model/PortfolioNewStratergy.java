package model;

import org.json.simple.JSONObject;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PortfolioNewStratergy extends FlexiblePortfolioImpl implements PortfolioStratergy {

  public ArrayList<String> validateTickrEntries(String entry) {
    String[] items = entry.split("\\s*,\\s*");
    ArrayList<String> convertedList = new ArrayList<>();
    for (String tickr : items) {
      if (!validateTickrSymbol(tickr)) {
        return new ArrayList<>();
      }
      convertedList.add(tickr);
    }
    return convertedList;
  }

  public ArrayList<Float> validateWeightEntriesSum(String entry){
    String[] items = entry.split("\\s*,\\s*");
    ArrayList<Float> convertedList = new ArrayList<>();
    Float sum = (float) 0;
    for (String weight : items) {
      if (!checkValidInteger(weight) && !checkValidFloat(weight)) {
        return new ArrayList<>();
      }
      sum += Float.parseFloat(weight);
      convertedList.add(Float.valueOf(weight));
    }
    if(sum!=100){
      return new ArrayList<>();
    }
    return convertedList;
  }

  public ArrayList<Float> validateStockWeightEntries(String entry, int tickrListSize) {
    ArrayList<Float> convertedList = validateWeightEntriesSum(entry);
    if (convertedList.size() == 0 || convertedList.size() != tickrListSize) {
      return new ArrayList<>();
    }
    return convertedList;
  }

  public JSONObject dollarCostExisting(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                       float commissionFees, float money, String date,
                                       JSONObject portfolio) {
    float toInvest = money - commissionFees;
    for (int i = 0; i < stocksList.size(); i++) {
      float stocksToBuy = ((weightsList.get(i) / 100) * toInvest) /
              getCallPriceDate(date, stocksList.get(i));
      portfolio = modifyJson(commissionFees, stocksToBuy, date, stocksList.get(i), portfolio);
    }
    // should commission fee be commission fee/noOfStocks?
    return portfolio;
  }

  public ArrayList<String> getAllDatesUsingStep(String from, String to, int increment) {
    ArrayList<String> allDates = new ArrayList<>();
    LocalDate start = LocalDate.parse(from);
    LocalDate end = LocalDate.parse(to);
    while (!start.isAfter(end)) {
      allDates.add(start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      start = start.plusDays(increment);
    }
    return allDates;
  }

  public void startToFinishDollarCost(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                      float commissionFees, int freq, String startDate,
                                      String endDate, float money) {
    /*JSONObject portfolio = new JSONObject();
    if (!thePortfolio.checkTickrJSONArray(addTickr, tickrpurchase)) {
      JSONObject addEntry = thePortfolio.makeTransactionRecord(datepurchase,
              commission, Float.valueOf(numpurchase), tickrpurchase);
      JSONArray listEntry = new JSONArray();
      listEntry.add(addEntry);
      addTickr.put(tickrpurchase, listEntry);
      SHOULD BE DONE IN CONTROLLER or need new method, need to discuss*/
  }

  public String listJSONfiles(String rootDir) {
    StringBuilder message = new StringBuilder();
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    assert filesList != null;
    for (File f : filesList) {
      if (f.isFile()) {
        // list only .json files.
        if (f.getName().contains(".json")) {
          message.append(f.getName().split("\\.json")[0]).append("\n");
        }
      }
    }
    return message.toString();
  }

}
