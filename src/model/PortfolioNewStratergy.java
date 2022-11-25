package model;

import java.util.ArrayList;

public class PortfolioNewStratergy extends FlexiblePortfolioImpl implements PortfolioStratergy {
  public void dollarCostExisting(ArrayList<String> stocksList, ArrayList<Float>weightsList,
                                 Float commissionfees, Float money, String date){
    // take the stocks.
    // divide money by the weight percentages.
    // subtract that money from commision fees.
    // get the stock value of that stock on that particular date.
    // divide that value by stock price.
    // buy those many stocks on that day for that stock.
    // add all these values and make a transaction and add it to the existing portfolio.
  }

  public void startToFinishDollarCost(ArrayList<String>stocksList,ArrayList<Float>weightsList,
                                      Float commissionfees, int freq, String startDate,
                                      String endDate, Float money){
    // take the stocks.
    // divide the money by weight percentages.
    // substract that money with commission fees.
    // make transaction for every specified frequency range in the given time range.
    // if end date is not given then, do the transaction till today.
    // if future date is given do transaction till today.
  }

}
