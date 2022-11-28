package model;

import org.json.simple.JSONObject;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

  public boolean validateWeightFormat(String entry){
    String[] items = entry.split("\\s*,\\s*");
    for (String weight : items) {
      if (!checkValidInteger(weight) && !checkValidFloat(weight)) {
        return false;
      }
    }
    return true;
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
    return portfolio;
  }

  public ArrayList<String> getAllDatesUsingStep(String from, String to, int increment) {
    ArrayList<String> allDates = new ArrayList<>();
    LocalDate start = LocalDate.parse(from);
    LocalDate end;
    if (to.equals("")) {
      end = start.plusDays(365);
    }
    else {
      end = LocalDate.parse(to);
    }
    while (!start.isAfter(end)) {
      if (start.getDayOfWeek() == DayOfWeek.SUNDAY){
        start = start.plusDays(1);
      }
      if (start.getDayOfWeek() == DayOfWeek.SATURDAY){
        start = start.plusDays(2);
      }
      String currDate = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      allDates.add(currDate);
      start = start.plusDays(increment);
    }
    return allDates;
  }

  public JSONObject saveStrategyRecord(ArrayList<String> stocksList, ArrayList<Float> weightsList, float commissionFees,
                                       int freq, String startDate, String endDate, float money, String strategyName,
                                       JSONObject strategyLookUp, String pfName) {
    JSONObject record = new JSONObject();
    record.put("stock_list", stocksList);
    record.put("weight_list", weightsList);
    record.put("commission_fee", commissionFees);
    record.put("frequency", freq);
    record.put("start_date", startDate);
    record.put("end_date", endDate);
    record.put("investment", money);
    if(strategyLookUp.containsKey(pfName)){
      JSONObject allStrategies = (JSONObject) strategyLookUp.get(pfName);
      allStrategies.put(strategyName, record);
      strategyLookUp.put(pfName, allStrategies);
    }
    else{
      JSONObject newPfRecord = new JSONObject();
      newPfRecord.put(strategyName, record);
      strategyLookUp.put(pfName, newPfRecord);
    }
    return strategyLookUp;
  }

  private JSONObject getPorfolioEntries(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                             float commissionFees, int freq, String startDate,
                             String endDate, float money, JSONObject portfolio){
    ArrayList<String> allDates = getAllDatesUsingStep(startDate, endDate, freq);
    for(String date: allDates) {
      portfolio = dollarCostExisting(stocksList, weightsList, commissionFees,  money,  date, portfolio);
    }
    return portfolio;
  }

  public JSONObject startToFinishDollarCost(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                      float commissionFees, int freq, String startDate,
                                      String endDate, float money) {
    return getPorfolioEntries(stocksList,weightsList,commissionFees,freq,startDate,endDate,money,new JSONObject());
  }

  public JSONObject startToFinishDollarCostPresent(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                            float commissionFees, int freq, String startDate,
                                            String endDate, float money, JSONObject portfolio) {
    return getPorfolioEntries(stocksList,weightsList,commissionFees,freq,startDate,endDate,money,portfolio);
  }

  public boolean checkDuplicates(ArrayList<String>stocks){
    Set<String> set = new HashSet<>(stocks);
    if(set.size() <stocks.size()){
      return true;
    }
    return false;
  }

  public boolean checkforInvalidcharacters(String stocks){
    String[] items = stocks.split("\\s*,\\s*");
    Pattern specialCharc = Pattern.compile("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]");
    for (String obj : items){
      Matcher hasSpecial = specialCharc.matcher(obj);
      if(hasSpecial.find() || obj.equals("")){
        return true;
      }
    }
    return false;
  }

  public String listJSONfiles(String rootDir) {
    StringBuilder message = new StringBuilder();
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    assert filesList != null;
    for (File f : filesList) {
      if (f.isFile()) {
        // list only .json files and not lookup file.
        if (f.getName().contains(".json") && !(f.getName().equals("stratergyLookup.json"))) {
          message.append(f.getName().split("\\.json")[0]).append("\n");
        }
      }
    }
    return message.toString();
  }

}
