package model;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public interface PortfolioStratergy extends Portfolio{

  ArrayList<String> validateTickrEntries(String entry);

  ArrayList<Float> validateStockWeightEntries(String entry, int tickrListSize);

  public ArrayList<String> getAllDatesUsingStep( String from, String to, int increment);

  JSONObject dollarCostExisting(ArrayList<String> stocksList, ArrayList<Float>weightsList,
                          float commissionfees, float money, String date, JSONObject portfolio);

  String listJSONfiles(String rootDir);
  ArrayList<Float> validateWeightEntriesSum(String entry);
  void startToFinishDollarCost(ArrayList<String>stocksList,ArrayList<Float>weightsList,
                               float commissionfees, int freq, String startDate, String endDate,
                               float money);

}
