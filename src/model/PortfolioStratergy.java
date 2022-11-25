package model;

import java.util.ArrayList;

public interface PortfolioStratergy extends Portfolio{
  void dollarCostExisting(ArrayList<String>stocksList,ArrayList<Float>weightsList,
                          Float commissionfees,Float money, String date);
  void startToFinishDollarCost(ArrayList<String>stocksList,ArrayList<Float>weightsList,
                               Float commissionfees, int freq, String startDate, String endDate,
                               Float money);

}
