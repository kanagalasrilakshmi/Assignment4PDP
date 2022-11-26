package model;

import java.io.File;
import java.util.ArrayList;

public class PortfolioNewStratergy extends FlexiblePortfolioImpl implements PortfolioStratergy {
  public void dollarCostExisting(ArrayList<String> stocksList, ArrayList<Float>weightsList,
                                 Float commissionfees, Float money, String date){
    // take the stocks.
    // divide money by the weight percentages.
    for(int i = 0;i<weightsList.size();i++){
      Float stockmoney = (commissionfees * Float.valueOf(weightsList.get(i)))/100;

    }
    // subtract that money from commission fees.
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
    // subtract that money with commission fees.
    // make transaction for every specified frequency range in the given time range.
    // if end date is not given then, do the transaction till today.
    // if future date is given do transaction till today.
  }
  public String listJSONfiles(String rootDir){
    String message = "";
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for (File f : filesList) {
      if (f.isFile()) {
        // list only .json files.
        if (f.getName().contains(".json")) {
          message += f.getName().split("\\.json")[0] +"\n";
        }
      }
    }
    return message;
  }

}
