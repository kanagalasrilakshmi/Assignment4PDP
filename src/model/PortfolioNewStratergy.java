package model;

import org.json.simple.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class implements all the methods in the Portfolio interface and extends all the functions,
 * from the Flexible portfolio class.
 * It helps to validate the all the inputs given by user in gui and also supports dollar-cost,
 * averaging strategy.
 */
public class PortfolioNewStratergy extends FlexiblePortfolioImpl implements PortfolioStratergy {

  /**
   * This function helps to validate the input list of tickr symbols ,
   * seperated by ',' with tickrData.txt file.
   *
   * @param entry list of all the tickr symbols seperated by a comma
   * @return array list of tickr symbols if all the given symbols are valid, else empty array list
   */
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

  /**
   * Check if the given string of weights is in correct format.
   *
   * @param entry is list of corresponding weights
   * @return true if format given is correct else false
   */
  public boolean validateWeightFormat(String entry) {
    String[] items = entry.split("\\s*,\\s*");
    for (String weight : items) {
      if (!checkValidInteger(weight) && !checkValidFloat(weight)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the given weights sum equals to 100.
   *
   * @param entry string list of weights
   * @return float list of weight entries if the sum equals to 100 else return empty float list
   */
  public ArrayList<Float> validateWeightEntriesSum(String entry) {
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
    if (sum != 100) {
      return new ArrayList<>();
    }
    return convertedList;
  }

  /**
   * This function checks if the sum of the weights is equal to 100, and size of tickr symbols,
   * is equal to number of weights given .
   *
   * @param entry         list of all the weight symbols seperated by a comma
   * @param tickrListSize size of the tickr symbols
   * @return list of weights in float format
   */
  public ArrayList<Float> validateStockWeightEntries(String entry, int tickrListSize) {
    ArrayList<Float> convertedList = validateWeightEntriesSum(entry);
    if (convertedList.size() == 0 || convertedList.size() != tickrListSize) {
      return new ArrayList<>();
    }
    return convertedList;
  }

  /**
   * Give a json object of entries that are to be added to the given portfolio name,
   * on a specific date.
   *
   * @param stocksList     list of stocks that are to be added
   * @param weightsList    list of corresponding weights to be added
   * @param commissionFees commision fees for a transaction
   * @param money          value to be invested in the portfolio
   * @param date           specific date on which transaction needs to be done
   * @param portfolio      is the JSON Object for the given existing portfolio
   * @return final json object to be added in the given existing portfolio after transaction occurs
   */
  public JSONObject dollarCostExisting(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                       float commissionFees, float money, String date,
                                       JSONObject portfolio) {
    float toInvest = money - commissionFees;
    for (int i = 0; i < stocksList.size(); i++) {
      float stocksToBuy = 0.0f;
      float storePrice = getCallPriceDate(date, stocksList.get(i));
      if (storePrice != 0.0f) {
        stocksToBuy = ((weightsList.get(i) / 100) * toInvest) / storePrice;
      }
      portfolio = modifyJson(commissionFees, stocksToBuy, date, stocksList.get(i), portfolio);
    }
    return portfolio;
  }

  /**
   * get list of all the dates in the string format YYYY-MM-DD in the range of given from date,
   * and to date,incremented with the given value after number of days,
   * the date in the given range needs to be incremented.
   *
   * @param from      is starting date
   * @param to        is ending date
   * @param increment number
   * @return array list of dates in the range using the given increment value.
   */
  public ArrayList<String> getAllDatesUsingStep(String from, String to, int increment) {
    ArrayList<String> allDates = new ArrayList<>();
    LocalDate start = LocalDate.parse(from);
    LocalDate end;
    if (to.equals("")) {
      end = start.plusDays(365);
    } else {
      end = LocalDate.parse(to);
    }
    while (!start.isAfter(end)) {
      if (start.getDayOfWeek() == DayOfWeek.SUNDAY) {
        start = start.plusDays(1);
      }
      if (start.getDayOfWeek() == DayOfWeek.SATURDAY) {
        start = start.plusDays(2);
      }
      String currDate = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      allDates.add(currDate);
      start = start.plusDays(increment);
    }
    return allDates;
  }

  /**
   * Returns a json object to be added in the lookup stratergy portfolio for persisting all,
   * strategies applied to a portfolio.
   *
   * @param stocksList     list of stocks that are to be added
   * @param weightsList    list of corresponding weights to be added
   * @param commissionFees is commission fees for a transaction
   * @param freq           number of days after which transaction needs to recur
   * @param startDate      is starting date for a transaction to happen
   * @param endDate        is ending date for a transaction to happen
   * @param money          value to be invested in the portfolio
   * @param strategyName   is strategy name that has all records of strategies applied to portfolio
   * @param strategyLookUp is the Json object entries saved in the lookup json
   * @param pfName         is the name of the portfolio over which strategy was applied
   * @return a json object with pfname as key, json object of strategy name and inputs given as key
   */
  public JSONObject saveStrategyRecord(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                       float commissionFees, int freq, String startDate,
                                       String endDate, float money, String strategyName,
                                       JSONObject strategyLookUp, String pfName) {
    JSONObject record = new JSONObject();
    record.put("stock_list", stocksList);
    record.put("weight_list", weightsList);
    record.put("commission_fee", commissionFees);
    record.put("frequency", freq);
    record.put("start_date", startDate);
    record.put("end_date", endDate);
    record.put("investment", money);
    if (strategyLookUp.containsKey(pfName)) {
      JSONObject allStrategies = (JSONObject) strategyLookUp.get(pfName);
      allStrategies.put(strategyName, record);
      strategyLookUp.put(pfName, allStrategies);
    } else {
      JSONObject newPfRecord = new JSONObject();
      newPfRecord.put(strategyName, record);
      strategyLookUp.put(pfName, newPfRecord);
    }
    return strategyLookUp;
  }

  private JSONObject getPorfolioEntries(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                        float commissionFees, int freq, String startDate,
                                        String endDate, float money, JSONObject portfolio) {
    ArrayList<String> allDates = getAllDatesUsingStep(startDate, endDate, freq);
    for (String date : allDates) {
      portfolio = dollarCostExisting(stocksList, weightsList, commissionFees, money, date,
              portfolio);
    }
    return portfolio;
  }

  /**
   * Return a json object that needs to be added to the new portfolio that is created,
   * while doing start to finish, dollar cost averaging stratergy on a new portfolio.
   *
   * @param stocksList     list of stocks that are to be added
   * @param weightsList    list of corresponding weights to be added
   * @param commissionFees is commision fees for a transaction
   * @param freq           number of days after which transaction needs to recur
   * @param startDate      is starting date for a transaction to happen
   * @param endDate        is ending date for a transaction to happen
   * @param money          value to be invested in the portfolio
   * @return a json object of entries to be added for the given start date, end date and frequency
   */
  public JSONObject startToFinishDollarCost(ArrayList<String> stocksList,
                                            ArrayList<Float> weightsList,
                                            float commissionFees, int freq, String startDate,
                                            String endDate, float money) {
    return getPorfolioEntries(stocksList, weightsList, commissionFees, freq, startDate, endDate,
            money, new JSONObject());
  }

  /**
   * This helps to multiple strategies on the existing portfolio and return a json object ,
   * after applying the strategy.
   *
   * @param stocksList     list of stocks that are to be added
   * @param weightsList    list of corresponding weights to be added
   * @param commissionFees is commision fees for a transaction
   * @param freq           number of days after which transaction needs to recur
   * @param startDate      is starting date for a transaction to happen
   * @param endDate        is ending date for a transaction to happen
   * @param money          value to be invested in the portfolio
   * @param portfolio      is the json object of the existing portfolio
   * @return a json object of entries to be added for the given start date, end date and frequency
   */
  public JSONObject startToFinishDollarCostPresent(ArrayList<String> stocksList,
                                                   ArrayList<Float> weightsList,
                                                   float commissionFees, int freq, String startDate,
                                                   String endDate, float money,
                                                   JSONObject portfolio) {
    return getPorfolioEntries(stocksList, weightsList, commissionFees, freq, startDate,
            endDate, money, portfolio);
  }

  /**
   * Check if repeated tickr symbols were given.
   *
   * @param stocks list of stock tickr symbols that needs to be checked
   * @return true if duplicate values are present else false
   */
  public boolean checkDuplicates(ArrayList<String> stocks) {
    Set<String> set = new HashSet<>(stocks);
    return set.size() < stocks.size();
  }

  /**
   * Check for invalid characters in the given list of stocks string.
   *
   * @param stocks string entry og the given batch of stocks
   * @return true if it has invalid characters else false
   */
  public boolean checkforInvalidcharacters(String stocks) {
    String[] items = stocks.split("\\s*,\\s*");
    Pattern specialCharc = Pattern.compile("[!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]");
    for (String obj : items) {
      Matcher hasSpecial = specialCharc.matcher(obj);
      if (hasSpecial.find() || obj.equals("")) {
        return true;
      }
    }
    return false;
  }

  /**
   * get today's date.
   *
   * @return today's date in YYYY-MM-DD format
   */
  public String getTodayDate() {
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    return df.format(new Date());
  }

  /**
   * list all the json files in the given root directory.
   * strategy lookup json that persists all the portfolios should not be modified.
   * apart from that all other jsons are listed.
   *
   * @param rootDir is the root directory set by the user or the initialized default directory
   * @return a string of consisting all the json files present in the given root directory
   */
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
