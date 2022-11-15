package controller;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import Model.Portfolio;
import view.View;

/**
 * Implements Controller inteface for running the stocks program for.
 * creation of portfolio.
 * Viewing created portfolios.
 * Getting value of a portfolio on a given date.
 * And Quitting the program.
 */
public class ControllerImplFlexible implements Controller {

  private View theView;
  private Portfolio thePortfolio;
  private Scanner in;
  private String rootDir;

  /**
   * Constructor for the ControllerImpl.
   *
   * @param thePortfolio of type Portfolio Object
   * @param theView      of type View Object
   * @param in           of type InputStream
   */
  public ControllerImplFlexible(Portfolio thePortfolio, View theView, InputStream in) {
    this.theView = theView;
    this.thePortfolio = thePortfolio;
    this.in = new Scanner(in);
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }

  /**
   * Function to run the Stocks app implementation.
   *
   * @throws IOException if invalid file is given
   */
  public void goStocks() throws IOException, java.text.ParseException {
    boolean quit = false;
    theView.showString("Give a valid input path where you want to store your portfolios. " +
            "For example: /Users/PDP/PortfolioBucket/");

    // ask the user to give valid input path for storing the portfolio.
    String rootDirUser = in.nextLine();
    if (new File(rootDirUser).exists()) {
      if (!thePortfolio.checkLastEndingCharacter(rootDirUser)) {
        rootDirUser = rootDirUser + "/";
      }
      this.rootDir = rootDirUser;
    } else {
      theView.showString("Invalid path given so portfolios will be stored in "
              + this.rootDir + " by default. To change directory, quit and start again.");
      if (!new File(this.rootDir).exists()) {
        try {
          Path path = Paths.get(this.rootDir);
          Files.createDirectories(path);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    while (!quit) {
      theView.showOptions();
      switch (in.next()) {
        case "Q":
          quit = true;
          break;
        case "M":
          // modify the portfolio.
          // select from the existing list of portfolios you want to modify from.
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolio exists. Create a portfolio to view it.");
            break;
          }
          // list the portfolios.
          theView.showString("Enter the name of the portfolio you want to view from " +
                  "the list of " +
                  "portfolios displayed below:");
          theView.listJSONFiles(this.rootDir);
          // check if user enters valid file name.
          String pfJsonNameChosen = in.next();
          while (!new File(this.rootDir + pfJsonNameChosen + ".json").exists()) {
            theView.showString("Please enter a valid Portfolio name from " +
                    "the displayed list only!");
            pfJsonNameChosen = in.next();
          }
          // ask if you want to make a purchase or sell.
          boolean transDone = false;
          while (!transDone) {
            // ask for the commission fees.
            theView.showString("Press 1 to sell stocks in portfolio");
            theView.showString("Press 2 to purchase stocks and add them to portfolio");
            switch (in.next()) {
              case "1":
                // selling.
                // ask for commission fees.
                // check if it is a valid number.
                theView.showString("Enter the commission fees");
                String fees = in.next();
                while (!thePortfolio.checkValidNum(fees)) {
                  theView.showString("Enter only float and integer values!");
                  theView.showString("Enter the commission fees");
                  fees = in.next();
                }
                // ask for which tickr symbol you want to sell stocks.
                // validate that tickr symbol.
                theView.showString("Enter Valid Stock company tickr symbol");
                String tickr = in.next();
                // check if this tickr symbol exists in the portfolio.
                while (!thePortfolio.checkTickrExists(this.rootDir + pfJsonNameChosen
                        + ".json", tickr)) {
                  theView.showString("The stock for which sale is to be made does not exist!");
                  theView.showString("Enter Valid Stock company tickr symbol");
                  tickr = in.next();
                }

                // ask number of stocks to be sold.
                // check if this number is valid.
                theView.showString("Enter number of stocks to be sold " +
                        "(Integer Values are Only Allowed)");
                String num = in.next();
                while (!thePortfolio.checkValidInteger(num)) {
                  theView.showString("Only Integer Stock values " +
                          "are allowed. Please enter a valid Integer number.");
                  num = in.next();
                }
                // check if number of stocks to be sold are valid.
                while (!thePortfolio.checkValidSell(this.rootDir +
                        pfJsonNameChosen + ".json", Integer.valueOf(num), tickr)) {
                  theView.showString("The number entered for selling stocks is more than " +
                          "stocks purchased");
                  theView.showString("Please enter valid number for stocks to be sold!!");
                  num = in.next();
                }
                // ask the date on which you want to sell the stocks.
                // check if this date is prior or not to the existing dates.
                String date = in.nextLine();
                while (!thePortfolio.checkIfRightFormat(date)) {
                  theView.showString("Please enter correct format for date");
                  date = in.nextLine();
                }
                // check if future date is entered.
                if (thePortfolio.checkFutureDate(date)) {
                  theView.showString("Future date is entered for which portfolio cannot be " +
                          "accessed!!");
                  break;
                }
                // check if today's date is entered and stock market is yet to be opened.
                try {
                  if (thePortfolio.checkTodayDateAndTime(date)) {
                    theView.showString("Stock value cannot be fetched as the stock market " +
                            "is yet to be opened today! Please check for a previous date.");
                    break;
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                  break;
                }
                // check if it is prior to the date for the tickr symbol.
                if (!thePortfolio.checkPriorDate(date, tickr, this.rootDir +
                        pfJsonNameChosen + ".json")) {
                  theView.showString("Sale on this date cannot be made on this date since " +
                          "it is invalid");
                  break;
                }
                // if all are valid then call modify the json function.
                else{
                  thePortfolio.modifyJson(fees, Integer.parseInt(num) * -1, date,
                          tickr, this.rootDir + pfJsonNameChosen + ".json");
                  theView.showString("The portfolio " + pfJsonNameChosen +
                          " is sucessfully modified");
                }
                transDone = true;
                break;
              case "2":
                // purchasing
                // ask for commission fees.
                // check if it is a valid number.
                theView.showString("Enter the commission fees");
                String feespurchase = in.next();
                while (!thePortfolio.checkValidNum(feespurchase)) {
                  theView.showString("Enter only float and integer values!");
                  feespurchase = in.next();
                }
                // ask for which tickr symbol you want to purchase stocks.
                // validate that tickr symbol.
                theView.showString("Enter Valid Stock company tickr symbol");
                String tickrpurchase = in.next();
                while (!thePortfolio.validateTickrSymbol(tickrpurchase)) {
                  theView.showString("Invalid Tickr Symbol is entered!");
                  theView.showString("Enter Valid Stock company tickr symbol");
                  tickrpurchase = in.next();
                }
                // ask the date on which you want to purchase the stocks.
                // check if this date is prior or not to the existing dates.
                // ask number of stocks to be purchased.
                // check if this number is valid.
                theView.showString("Enter number of stocks to be purchased " +
                        "(Integer Values are Only Allowed)");
                String numpurchase = in.next();
                while (!thePortfolio.checkValidInteger(numpurchase)) {
                  theView.showString("Only Integer Stock values " +
                          "are allowed. Please enter a valid Integer number.");
                  numpurchase = in.next();
                }
                theView.showString("Enter date to purchase the stocks");
                String datepurchase = in.next();
                while (!thePortfolio.checkIfRightFormat(datepurchase)) {
                  theView.showString("Please enter correct format for date");
                  datepurchase = in.next();
                }
                // check if future date is entered.
                if (thePortfolio.checkFutureDate(datepurchase)) {
                  theView.showString("Future date is entered for which portfolio cannot be " +
                          "accessed!!");
                  break;
                }
                // check if today's date is entered and stock market is yet to be opened.
                try {
                  if (thePortfolio.checkTodayDateAndTime(datepurchase)) {
                    theView.showString("Stock value cannot be fetched as the stock market " +
                            "is yet to be opened today! Please check for a previous date.");
                    break;
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                  break;
                }
                // if all are valid then call modify the json function.
                if (!thePortfolio.checkTickrExists(this.rootDir + pfJsonNameChosen +
                        ".json", tickrpurchase)) {
                  thePortfolio.modifyJson(feespurchase, Integer.parseInt(numpurchase),
                          datepurchase, tickrpurchase,
                          this.rootDir + pfJsonNameChosen + ".json");
                  theView.showString("The portfolio " + pfJsonNameChosen + " is successfully " +
                          "modified");
                } else {
                  // check if it is prior to the date for the tickr symbol.
                  if (!thePortfolio.checkPriorDate(datepurchase, tickrpurchase,
                          this.rootDir + pfJsonNameChosen + ".json")) {
                    theView.showString("Purchase on this date cannot be made on this " +
                            "date since, it is invalid");
                    break;
                  }
                  thePortfolio.modifyJson(feespurchase, Integer.parseInt(numpurchase),
                          datepurchase, tickrpurchase,
                          this.rootDir + pfJsonNameChosen + ".json");
                  theView.showString("The portfolio " + pfJsonNameChosen + " is successfully " +
                          "modified");
                }
                transDone = true;
                break;
              default:
                theView.showString("Press either 1 or 2 only.");
            }
          }
          break;
        case "B":
          // display bar graph of the portfolio.
          // ask the user input.
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolio exists. Create a portfolio to view it.");
            break;
          }
          // list the portfolios.
          theView.showString("Enter the name of the portfolio you want to view from " +
                  "the list of " +
                  "portfolios displayed below:");
          theView.listJSONFiles(this.rootDir);
          // check if user enters valid file name.
          String pfPerformance = in.next();
          while (!new File(this.rootDir + pfPerformance + ".json").exists()) {
            theView.showString("Please enter a valid Portfolio name from " +
                    "the displayed list only!");
            pfPerformance = in.next();
          }
          theView.showString("Choose one of the following timespan range to view the " +
                  "performance of the portfolio");
          // take range of inputs from the user.
          String date1 = in.next();
          String date2 = in.next();
          // if user first input date > second input date.
          // say it is not possible enter valid range.
          while(!thePortfolio.checkValidDates(date1,date2)){
            theView.showString("The dates given cannot give a valid range please given valid " +
                    "input dates");
            date1 = in.next();
            date2 = in.next();
          }
          // if the user enters valid range dates.
          // check difference between the dates.
          int differenceDays = thePortfolio.checkDifference(date1,date2);
          // if the difference is less than 5 return error.
          if(differenceDays < 5){
            theView.showString("The difference is less than 5 days hence not valid");
            break;
          }
          ArrayList<Float>values = thePortfolio.getValuesPortfolio(this.rootDir,
                  pfPerformance ,date1,date2,differenceDays);
          ArrayList<String>dates = thePortfolio.getDatesDisplay(date1,date2,differenceDays);
          float scaleVal = thePortfolio.getScale(values);
          ArrayList<String>points = thePortfolio.getPoints(scaleVal,values);
          theView.showString("Performance of the portfolio "+pfPerformance+" from "+date1+
                  " to "+date2);
          for(int i = 0;i<points.size();i++){
            theView.showString(dates.get(i)+" : "+points.get(i));
          }
          theView.showString("Scale: * = $"+scaleVal);
          break;
        case "V":
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolio exists. Create a portfolio to view it.");
            break;
          }
          // list the portfolios.
          theView.showString("Enter the name of the portfolio you want to view from " +
                  "the list of " +
                  "portfolios displayed below:");
          theView.listJSONFiles(this.rootDir);
          // check if user enters valid file name.
          String pfNameChosen = in.next();
          while (!new File(this.rootDir + pfNameChosen + ".json").exists()) {
            theView.showString("Please enter a valid Portfolio name from " +
                    "the displayed list only!");
            pfNameChosen = in.next();
          }
          boolean viewDone = false;
          while (!viewDone) {
            theView.showString("Press I to view investment made in a portfolio by a " +
                    "specific date");
            theView.showString("Press D to view portfolio value on specific date");
            theView.showString("Press P to view portfolio composition");
            switch (in.next()) {
              case "I":
                // get cost basis of the portfolio till that date.
                theView.showString("Enter the date for which you want to " +
                        "fetch the portfolio in YYYY-MM-DD format only!");
                String date = in.nextLine();
                // check date format.
                while (!thePortfolio.checkIfRightFormat(date)) {
                  theView.showString("Please enter correct format for date");
                  date = in.nextLine();
                }
                // check if future date is entered.
                if (thePortfolio.checkFutureDate(date)) {
                  theView.showString("Future date is entered for which portfolio cannot be " +
                          "accessed!!");
                  break;
                }
                // check if today's date is entered and stock market is yet to be opened.
                try {
                  if (thePortfolio.checkTodayDateAndTime(date)) {
                    theView.showString("Stock value cannot be fetched as the stock market " +
                            "is yet to be opened today! Please check for a previous date.");
                    break;
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                  break;
                }
                // get the cost basis.
                float costBasis = thePortfolio.getCostBasis(this.rootDir + pfNameChosen
                        + ".json", date);
                theView.showString("The cost basis till the date, " + date + " is : "
                        + costBasis);
                viewDone = true;
                break;
              case "D":
                // get value of the portfolio on that specific date.
                theView.showString("Enter the date for which you want to " +
                        "fetch the portfolio in YYYY-MM-DD format only!");
                String valdate = in.nextLine();
                // check date format.

                while (!thePortfolio.checkIfRightFormat(valdate)) {
                  theView.showString("Please enter correct format for date");
                  valdate = in.nextLine();
                }
                // check if future date is entered.
                if (thePortfolio.checkFutureDate(valdate)) {
                  theView.showString("Future date is entered for which portfolio cannot be " +
                          "accessed!!");
                  break;
                }
                // check if today's date is entered and stock market is yet to be opened.
                try {
                  if (thePortfolio.checkTodayDateAndTime(valdate)) {
                    theView.showString("Stock value cannot be fetched as the stock market " +
                            "is yet to be opened today! Please check for a previous date.");
                    break;
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                  break;
                }
                // get the value on the specific date.
                float totVal = thePortfolio.portfolioValueDate(this.rootDir, pfNameChosen, valdate);
                theView.showString("Portfolio Value on " + valdate + " is : " + totVal);
                viewDone = true;
                break;
              case "P":
                // view composition of portfolio.
                JSONParser parser = new JSONParser();
                try (FileReader reader = new FileReader
                        (this.rootDir + pfNameChosen + ".json")) {
                  Object parseObj = parser.parse(reader);
                  JSONObject portfolio = (JSONObject) parseObj;
                  for (Object tickrsym : portfolio.keySet()) {
                    theView.showString("TICKER SYMBOL : " + (String) tickrsym);
                    theView.showString("NUM OF STOCKS PURCHASED/SOLD    DATE OF PURCHASE/SELL   "
                            + " COMMISION FEES    STOCK PRICE   TOTAL NUMBER OF STOCKS TILL DATE  "
                            + "COST BASIS");
                    JSONArray arrayObj = (JSONArray) portfolio.get(tickrsym);
                    for (int i = 0; i < arrayObj.size(); i++) {
                      JSONObject tickrRecord = (JSONObject) arrayObj.get(i);
                      theView.showString(
                              tickrRecord.get("NumStocks Sold or Purchased")
                                      + "                 " +
                                      "              " + (String) tickrRecord.get("Date")
                                      + "            " +
                                      "     " + (Double) tickrRecord.get("Commission Fee") +
                                      "                   " + tickrRecord.get("Stock Price") +
                                      "                  " + tickrRecord.get("TotalStocks")
                                      + "                   "
                                      + (Double) tickrRecord.get("CostBasis"));
                    }
                  }
                } catch (ParseException e) {
                  throw new RuntimeException(e);
                }
                viewDone = true;
                break;
              default:
                theView.showString("Please enter either I/D/P only!!");
                break;
            }
          }
          break;
        case "C":
          // create a new portfolio.
          // check valid if not then it uses the default.
          // initialized path only for saving the portfolios.
          theView.showString("Give a valid name for the portfolio you " +
                  "want to create. The string " +
                  "should not have spaces or special characters and " +
                  "the length must be less than 25 characters.");
          String pfName = in.next();
          while (!thePortfolio.checkValidpfName(pfName)) {
            theView.showString("Please enter a valid portfolio name:");
            pfName = in.next();
          }
          // check if this same name portfolio exists.
          String pfNamePath = this.rootDir + pfName + ".json";
          while (new File(pfNamePath).exists()) {
            theView.showString("Portfolio with this name already exists! ");
            theView.showString("Give another name for the portfolio you want to create:");
            pfName = in.next();
            pfNamePath = this.rootDir + pfName + ".json";
          }
          boolean done = false;
          JSONObject addTickr = new JSONObject();
          while (!done) {
            theView.showString("Press Y to add stocks " +
                    "to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            switch (in.next()) {
              case "S":
                if (addTickr.size() == 0) {
                  System.out.println(addTickr);
                  theView.showString("Portfolio must contain at least one entry!! ");
                } else {
                  thePortfolio.createPortfolioJson(this.rootDir + pfName + ".json",
                          addTickr);
                  done = true;
                  thePortfolio.createPortfolioJson(pfNamePath, addTickr);
                  theView.showString("Successfully created the portfolio " + pfName);
                }
                break;
              case "Y":
                theView.showString("Enter the commission fees");
                String feescommision = in.next();
                while (!thePortfolio.checkValidNum(feescommision)) {
                  theView.showString("Enter only float and integer values!");
                  feescommision = in.next();
                }
                theView.showString("Enter the tickr symbol");
                String tickrpurchase = in.next();
                while (!thePortfolio.validateTickrSymbol(tickrpurchase)) {
                  theView.showString("Invalid Tickr Symbol is entered!");
                  theView.showString("Enter Valid Stock company tickr symbol");
                  tickrpurchase = in.next();
                }

                theView.showString("Enter the number of stocks");
                String numpurchase = in.next();
                while (!thePortfolio.checkValidInteger(numpurchase)) {
                  theView.showString("Only Integer Stock values " +
                          "are allowed. Please enter a valid Integer number.");
                  numpurchase = in.next();
                }

                theView.showString("Enter the date of purchase");
                String datepurchase = in.next();
                while (!thePortfolio.checkIfRightFormat(datepurchase)) {
                  theView.showString("Please enter correct format for date");
                  datepurchase = in.next();
                }
                // check if future date is entered.
                if (thePortfolio.checkFutureDate(datepurchase)) {
                  theView.showString("Future date is entered for which portfolio cannot be " +
                          "created!!");
                  break;
                }
                // check if today's date is entered and stock market is yet to be opened.
                try {
                  if (thePortfolio.checkTodayDateAndTime(datepurchase)) {
                    theView.showString("Stock value cannot be fetched as the stock market " +
                            "is yet to be opened today! Please check for a previous date.");
                    break;
                  }
                } catch (Exception e) {
                  e.printStackTrace();
                  break;
                }
                if (!thePortfolio.checkTickrJSONArray(addTickr, tickrpurchase)) {
                  JSONObject addEntry = new JSONObject();
                  JSONArray listEntry = new JSONArray();
                  addEntry.put("Date", datepurchase);
                  addEntry.put("Commission Fee", Float.valueOf(feescommision));
                  addEntry.put("NumStocks Sold or Purchased", Integer.valueOf(numpurchase));
                  addEntry.put("TotalStocks", Integer.valueOf(numpurchase));
                  // get stock price on that day.
                  // calculate cost basis.
                  //Float stockPrice = thePortfolio.getCallPriceDate(datepurchase,tickrpurchase);
                  float stockPrice = 0;
                  addEntry.put("CostBasis", stockPrice * Float.valueOf(numpurchase) +
                          Float.valueOf(feescommision));
                  addEntry.put("Stock Price", stockPrice);
                  listEntry.add(addEntry);
                  addTickr.put(tickrpurchase, listEntry);
                } else {
                  JSONArray tickerRecord = (JSONArray) addTickr.get(tickrpurchase);
                  JSONObject keyEntry = (JSONObject) tickerRecord.get(tickerRecord.size() - 1);
                  while (!thePortfolio.checkDateinJSONObject(datepurchase,
                          (String) keyEntry.get("Date"))) {
                    theView.showString("The date entered is invalid entry");
                    theView.showString("Enter the date of purchase");
                    datepurchase = in.next();
                  }
                  JSONObject newKeyEntry = new JSONObject();
                  Float stockPrice = thePortfolio.getCallPriceDate
                          (datepurchase, tickrpurchase);
                  newKeyEntry.put("Date", datepurchase);
                  newKeyEntry.put("Commission Fee", Float.valueOf(feescommision));
                  newKeyEntry.put("NumStocks Sold or Purchased", Integer.valueOf(numpurchase));
                  newKeyEntry.put("TotalStocks", Integer.valueOf(numpurchase) +
                          (int) keyEntry.get("NumStocks Sold or Purchased"));
                  newKeyEntry.put("Stock Price", stockPrice);
                  newKeyEntry.put("CostBasis", stockPrice * Float.valueOf(numpurchase) +
                          (float) keyEntry.get("CostBasis") + Float.valueOf(feescommision));
                  tickerRecord.add(newKeyEntry);
                  addTickr.put(tickrpurchase, tickerRecord);
                }
                break;
              default:
                theView.showString("Press Either Y/S only!!");
                break;
            }
          }
          break;
        default:
          theView.showOptionError();
          break;
      }
    }
  }
}


