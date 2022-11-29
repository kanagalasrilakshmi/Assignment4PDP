package model;

import org.json.simple.JSONObject;

import java.util.ArrayList;

/**
 * This interface extends Portfolio interface and adds more methods that are only used while running GUI based view,
 * and applying new strategies.
 */
public interface PortfolioStratergy extends Portfolio {

    /**
     * This function helps to validate the input list of tickr symbols seperated by comma with tickrData.txt file.
     *
     * @param entry list of all the tickr symbols seperated by a comma
     * @return an array list of tickr symbols if all the given symbols are valid, else return empty array list
     */
    ArrayList<String> validateTickrEntries(String entry);

    /**
     * This function checks if the sum of the weights is equal to 100, and size of tickr symbols is equal,
     * to number of weights given .
     *
     * @param entry         list of all the weight symbols seperated by a comma
     * @param tickrListSize size of the tickr symbols
     * @return list of weights in float format
     */
    ArrayList<Float> validateStockWeightEntries(String entry, int tickrListSize);

    /**
     * get list of all the dates in the string format YYYY-MM-DD in the range of given from date and to date,
     * incremented with the given value after number of days the date in the given range needs to be incremented.
     *
     * @param from      is starting date
     * @param to        is ending date
     * @param increment number
     * @return array list of dates in the range using the given increment value.
     */
    ArrayList<String> getAllDatesUsingStep(String from, String to, int increment);

    /**
     * Give a json object of entries that are to be added to the given portfolio name on a specific date.
     *
     * @param stocksList     list of stocks that are to be added
     * @param weightsList    list of corresponding weights to be added
     * @param commissionfees commision fees for a transaction
     * @param money          value to be invested in the portfolio
     * @param date           specific date on which transaction needs to be done
     * @param portfolio      is the JSON Object for the given existing portfolio
     * @return return final json object to be added in the given existing portfolio after transaction occurs
     */
    JSONObject dollarCostExisting(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                  float commissionfees, float money, String date, JSONObject portfolio);

    /**
     * list all the json files in the given root directory.
     * strategy lookup json that persists all the portfolios should not be modified.
     * apart from that all other jsons are listed.
     *
     * @param rootDir is the root directory set by the user or by default(depends on the input given by the user)
     * @return a string of consisting all the json files present in the given root directory
     */
    String listJSONfiles(String rootDir);

    /**
     * Check if the given weights sum equals to 100.
     *
     * @param entry string list of weights
     * @return float list of weight entries if the sum equals to 100 else return empty float list
     */
    ArrayList<Float> validateWeightEntriesSum(String entry);

    /**
     * Check if the given string of weights is in correct format.
     *
     * @param entry is list of corresponding weights
     * @return true if format given is correct else false
     */
    boolean validateWeightFormat(String entry);

    /**
     * Returns a json object to be added in the lookup stratergy portfolio for persisting all the strategies applied,
     * to a portfolio.
     *
     * @param stocksList     list of stocks that are to be added
     * @param weightsList    list of corresponding weights to be added
     * @param commissionFees is commision fees for a transaction
     * @param freq           number of days after which transaction needs to recur
     * @param startDate      is starting date for a transaction to happen
     * @param endDate        is ending date for a transaction to happen
     * @param money          value to be invested in the portfolio
     * @param strategyName   is the name of the stratergy that has all the records of strategies applied to a portfolio
     * @param strategyLookUp is the Json object entries saved in the lookup json
     * @param pfName         is the name of the portfolio over which strategy was applied
     * @return a json object with pfname as key, value with a json object consisting of strtegy name and inputs given
     */
    JSONObject saveStrategyRecord(ArrayList<String> stocksList, ArrayList<Float> weightsList, float commissionFees,
                                  int freq, String startDate, String endDate, float money, String strategyName,
                                  JSONObject strategyLookUp, String pfName);

    /**
     * Return a json object that needs to be added to the new portfolio that is created while doing start to finish,
     * dollar cost averaging stratergy on a new portfolio.
     *
     * @param stocksList     list of stocks that are to be added
     * @param weightsList    list of corresponding weights to be added
     * @param commissionfees is commision fees for a transaction
     * @param freq           number of days after which transaction needs to recur
     * @param startDate      is starting date for a transaction to happen
     * @param endDate        is ending date for a transaction to happen
     * @param money          value to be invested in the portfolio
     * @return a json object of entries to be added for the given start date, end date and frequency
     */
    JSONObject startToFinishDollarCost(ArrayList<String> stocksList, ArrayList<Float> weightsList, float commissionfees,
                                       int freq, String startDate, String endDate, float money);

    /**
     * Check if repeated tickr symbols were given.
     *
     * @param stocks list of stock tickr symbols that needs to be checked
     * @return true if duplicate values are present else false
     */
    boolean checkDuplicates(ArrayList<String> stocks);

    /**
     * Check for invalid characters in the given list of stocks string.
     *
     * @param stocks string entry og the given batch of stocks
     * @return true if it has invalid characters else false
     */
    boolean checkforInvalidcharacters(String stocks);

    /**
     * This helps to multiple strategies on the existing portfolio and return a json object after applying the strategy.
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
    JSONObject startToFinishDollarCostPresent(ArrayList<String> stocksList, ArrayList<Float> weightsList,
                                              float commissionFees, int freq, String startDate,
                                              String endDate, float money, JSONObject portfolio);

    /**
     * get today's date.
     *
     * @return today's date in YYYY-MM-DD format
     */
    String getTodayDate();
}
