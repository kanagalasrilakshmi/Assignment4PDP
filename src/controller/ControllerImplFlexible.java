package controller;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import model.Portfolio;
import view.View;

/**
 * Implements Controller interface for running the stocks program for.
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

  public void setDirectory(String rootDirUser) {
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
  }

  //TODO: PUT IT IN VIEW
  public boolean doneListingPortfolios(String path, String extension) {
    if (!thePortfolio.hasAtleastOnePortfolio(path, extension)) {
      theView.showString("No portfolio exists. Create a portfolio.");
      return false;
    }
    theView.showString("Enter the name of the portfolio you want, from " +
            "the list of " + "portfolios displayed below:");
    theView.listJSONFiles(this.rootDir);
    return true;
  }

  public String getValidPfName(String rootDir, String extension) {
    theView.showString("Enter valid portfolio name:");
    String pfNameChosen = in.next();
    while (!new File(rootDir + pfNameChosen + extension).exists()) {
      theView.showString("Portfolio with this name doesn't exist. Try again");
      pfNameChosen = in.next();
    }
    return pfNameChosen;
  }

  public String createValidPf(String rootDir, String extension) {
    theView.showString("Give a valid name for the portfolio you want to create. The string " +
            "should not have spaces or special characters and the length must be less than " +
            "25 characters.");
    String pfName = in.next();
    while (!thePortfolio.checkValidpfName(pfName)) {
      theView.showString("Please enter a valid portfolio name:");
      pfName = in.next();
    }
    while (new File(rootDir + pfName + extension).exists()) {
      theView.showString("Portfolio with this name already exists. Try again");
      pfName = createValidPf(rootDir, extension);
    }
    return pfName;
  }

  public String getAndValidateDate(String message) {
    theView.showString(message);
    String date = in.next();
    while (!thePortfolio.checkIfRightFormat(date)) {
      theView.showString("Please enter correct format for date:");
      date = in.next();
    }    // check if future date is entered or today's date but before 9.30 am
    while (thePortfolio.checkFutureDate(date) || thePortfolio.checkTodayDateAndTime(date)) {
      theView.showString("You can only enter past date or present(if after 9:30am)." +
              "! Please enter new date:");
      getAndValidateDate(message);
    }
    return date;
  }

  public String getValidNumberStocks(String message) {
    theView.showString(message);
    String num = in.next();
    if (!thePortfolio.checkValidInteger(num)) {
      theView.showString("Only Integer stock values are allowed. ");
      getValidNumberStocks(message);
    }
    return num;
  }

  public Float getValidCommission() {
    theView.showString("Enter the commission fees");
    String commision = in.next();
    while (!(thePortfolio.checkValidInteger(commision) ||
            thePortfolio.checkValidFloat(commision))) {
      theView.showString("Enter only float or integer values:");
      commision = in.next();
    }
    return Float.valueOf(commision);
  }

  public String getValidTickr() throws FileNotFoundException {
    theView.showString("Enter Valid Stock company tickr symbol");
    String tickrpurchase = in.next();
    while (!thePortfolio.validateTickrSymbol(tickrpurchase)) {
      theView.showString("Invalid Tickr Symbol! Enter valid company tickr symbol:");
      tickrpurchase = in.next();
    }
    return tickrpurchase;
  }

  public boolean validSell(int num, String path, String tickr, String date) throws ParseException {
    if (!thePortfolio.checkValidSell(path, Integer.valueOf(num), tickr, date)) {
      theView.showString("The number entered for selling stocks is more than " +
              "stocks purchased");
      return false;
    }
    return true;
  }

  public String makeTransaction(String tickr, String path, boolean isSell) throws
          java.text.ParseException {
    Float fees = getValidCommission();
    String transactionDate = getAndValidateDate("Enter date of transaction:");
    int numberOfStocks = Integer.valueOf(getValidNumberStocks("Number of stocks:"));
    if (isSell) {
      while (!validSell(numberOfStocks, path, tickr, transactionDate)) {
        numberOfStocks = Integer.valueOf(getValidNumberStocks("Enter valid number of stocks:"));
        if (numberOfStocks <= 0) {
          return "This sale cannot be made!";
        }
      }
      numberOfStocks = numberOfStocks * (-1);
    }
    thePortfolio.modifyJson(fees, numberOfStocks, transactionDate, tickr, path);
    return "The portfolio is successfully modified";
  }

  public void  printBargraph(String pfPerformance,String date1,String date2,int differenceDays)
          throws FileNotFoundException, ParseException {
    ArrayList<Float> values = thePortfolio.getValuesPortfolio(this.rootDir,
            pfPerformance, date1, date2, differenceDays);
    ArrayList<String> dates = thePortfolio.getDatesDisplay(date1, date2, differenceDays);
    float scaleVal = thePortfolio.getScale(values);
    ArrayList<String> points = thePortfolio.getPoints(scaleVal, values);
    theView.showString("Performance of the portfolio " + pfPerformance + " from " + date1 +
            " to " + date2);
    for (int i = 0; i < points.size(); i++) {
      theView.showString(dates.get(i) + " : " + points.get(i));
    }
    theView.showString("Scale: * = $" + scaleVal);
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
    setDirectory(rootDirUser);
    while (!quit) {
      theView.showOptions();
      switch (in.next()) {
        case "Q":
          quit = true;
          break;
        case "M":
          if (!doneListingPortfolios(this.rootDir, ".json")) {
            break;
          }
          String pfJsonNameChosen = getValidPfName(this.rootDir, ".json");
          boolean transDone = false;
          String pfPath = this.rootDir + pfJsonNameChosen + ".json";
          while (!transDone) {
            theView.showString("Press 1 to sell stocks in portfolio");
            theView.showString("Press 2 to purchase stocks and add them to your portfolio");
            switch (in.next()) {
              case "1":
                theView.showString("Enter Valid Stock company tickr symbol");
                String tickr = in.next();
                if (!thePortfolio.ifTickrInPf(pfPath, tickr)) {
                  theView.showString("No stocks for this tickr exists to sell.");
                  break;
                }
                theView.showString(makeTransaction(tickr, pfPath, true));
                transDone = true;
                break;
              case "2":
                String tickrpurchase = getValidTickr();
                theView.showString(makeTransaction(tickrpurchase, pfPath, false));
                transDone = true;
                break;
              default:
                theView.showString("Press either 1 or 2 only.");
            }
          }
          break;
        case "B":
          if (!doneListingPortfolios(this.rootDir, ".json")) {
            break;
          }
          String pfPerformance = getValidPfName(this.rootDir, ".json");
          theView.showString("Enter the timespan range to view the " +
                  "performance of the portfolio");
          // take range of inputs from the user.
          String date1 = getAndValidateDate("Enter the date for which you want to " +
                  "fetch the portfolio in YYYY-MM-DD format only!");
          String date2 = getAndValidateDate("Enter the date for which you want to " +
                  "fetch the portfolio in YYYY-MM-DD format only!");
          // if user first input date > second input date.
          // say it is not possible enter valid range.
          while (!thePortfolio.checkValidDates(date1, date2)) {
            theView.showString("The dates given cannot give a valid range please given valid " +
                    "input dates");
            date1 = in.next();
            date2 = in.next();
          }
          // if the user enters valid range dates.
          // check difference between the dates.
          int differenceDays = thePortfolio.checkDifference(date1, date2);
          // if the difference is less than 5 return error.
          if (differenceDays < 5) {
            theView.showString("The difference is less than 5 days hence not valid");
            break;
          }
          printBargraph(pfPerformance,date1,date2,differenceDays);
          break;
        case "V":
          if (!thePortfolio.hasAtleastOnePortfolio(this.rootDir, ".json")) {
            theView.showString("No portfolio exists. Create a portfolio to view it.");
            break;
          }
          if (!doneListingPortfolios(this.rootDir, ".json")) {
            break;
          }
          // check if user enters valid file name.'
          String pfNameChosen = getValidPfName(this.rootDir, ".json");
          boolean viewDone = false;
          while (!viewDone) {
            theView.showFlexibleViewOptions();
            switch (in.next()) {
              case "I":
                String date = getAndValidateDate("Enter the date for which you want to " +
                        "fetch the portfolio in YYYY-MM-DD format only!");
                float costBasis = thePortfolio.getCostBasis(this.rootDir + pfNameChosen
                        + ".json", date);
                theView.showString("The cost basis till date, " + date + " is: " + costBasis);
                viewDone = true;
                break;
              case "D":
                String valdate = getAndValidateDate("Enter the date for which you want to " +
                        "fetch the portfolio in YYYY-MM-DD format only!");
                float totVal = thePortfolio.portfolioValueDate(this.rootDir, pfNameChosen, valdate);
                theView.showString("Portfolio Value on " + valdate + " is : " + totVal);
                viewDone = true;
                break;
              case "P":
                JSONObject portfolio = thePortfolio.readPortfolio(this.rootDir +
                        pfNameChosen + ".json");
                theView.viewFlexibleComposition(portfolio);
                viewDone = true;
                break;
              default:
                theView.showString("Please enter either I/D/P only!!");
                break;
            }
          }
          break;
        case "C":
          String pfName = createValidPf(this.rootDir, ".json");
          boolean done = false;
          JSONObject addTickr = new JSONObject();
          while (!done) {
            theView.showString("Press Y to add stocks " +
                    "to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            switch (in.next()) {
              case "S":
                thePortfolio.savePortfolio(this.rootDir + pfName + ".json",
                        addTickr);
                done = true;
                thePortfolio.savePortfolio(pfName, addTickr);
                theView.showString("Successfully created the portfolio " + pfName);
                break;
              case "Y":
                Float commission = getValidCommission();
                String tickrpurchase = getValidTickr();
                String numpurchase = getValidNumberStocks("Enter the number of stocks:");
                String datepurchase = getAndValidateDate("Enter the date of purchase:");
                if (!thePortfolio.checkTickrJSONArray(addTickr, tickrpurchase)) {
                  JSONObject addEntry = thePortfolio.makeTransactionRecord
                          (datepurchase, commission, Integer.valueOf(numpurchase), tickrpurchase);
                  JSONArray listEntry = new JSONArray();
                  listEntry.add(addEntry);
                  addTickr.put(tickrpurchase, listEntry);
                } else {
                  theView.showString("The given tickr symbol already exists, select M " +
                          "option to modify it");
                  break;
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


