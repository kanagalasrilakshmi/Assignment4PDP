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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public class ControllerImplFlexible extends ControllerImpl implements Controller {

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
    super(thePortfolio, theView, in);
    this.theView = theView;
    this.thePortfolio = thePortfolio;
    this.in = new Scanner(in);
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }


  /**
   * List all the portfolios with the given extension of the folder where portfolios are present.
   *
   * @param path      is the portfolio input folder
   * @param extension is either, .json or .txt
   * @return false if no portfolios are present in the given input folder else print all of them
   */
  @Override
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

  /**
   * Used for creating a valid portfolio that is not too long or special characters.
   * The same name does not already exist in the given input folder for the portfolio.
   *
   * @param rootDir   is the input root directory where all the created portfolios are stored
   * @param extension is either .json or .txt
   * @return a valid portfolio name given by the user
   */
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

  /**
   * Used to take in date from the user and check if right format date is entered,
   * if the date entered is in future or entered before the stock market opened today.
   *
   * @param message that asks the user to enter date in correct format
   * @return valid date given by the user
   */
  public String getAndValidateDate(String message) throws ParseException {
    theView.showString(message);
    String date = in.next();
    while (!thePortfolio.checkIfRightFormat(date) || date.length() < 10) {
      theView.showString("Please enter correct format for date:");
      date = in.next();
    }    // check if future date is entered or today's date but before 9.30 am
    while (thePortfolio.checkFutureDate(date) || thePortfolio.checkTodayDateAndTime(date)) {
      theView.showString("You can only enter past date or present(if after 9:30am)." +
              "! Please enter new date:");
      date = getAndValidateDate(message);
    }
    return date;

  }

  /**
   * enter the commision fees for performing a transaction.
   *
   * @return valid value of the commission fees entered by the user in float type
   */
  public Float getValidCommission() {
    theView.showString("Enter the commission fees:");
    String commision = in.next();
    while (!(thePortfolio.checkValidInteger(commision) ||
            thePortfolio.checkValidFloat(commision))) {
      theView.showString("Enter only float or integer values:");
      commision = in.next();
    }
    if (Float.valueOf(commision) < 0) {
      theView.showString("Negative commission values are not allowed!");
      getValidCommission();
    }
    return Float.valueOf(commision);
  }

  /**
   * Checks if the given input tickr symbol by the user exists by validating,
   * in the list of valid symbols present in the tickrData.txt file.
   *
   * @return a valid tickr from the user that is also there in the tickrData.txt file
   * @throws FileNotFoundException if there is a problem finding tickrData.txt file
   */
  public String getValidTickr() throws FileNotFoundException {
    theView.showString("Enter Valid Stock company tickr symbol");
    String tickrpurchase = in.next();
    while (!thePortfolio.validateTickrSymbol(tickrpurchase)) {
      theView.showString("Invalid Tickr Symbol! Enter valid company tickr symbol:");
      tickrpurchase = in.next();
    }
    return tickrpurchase;
  }

  /**
   * Check if for a given tickr symbol and date,
   * the number of stocks present in the portfolio are more than or equal to input number of stocks.
   * If given number of stocks are less than or equal to the stocks present,
   * for the tickr symbol till date then sale can be made.
   *
   * @param num   is the number of stocks that are to be sold
   * @param path  is the path for the portfolio over which transaction needs to be done
   * @param tickr is company tickr symbol for which sale needs to be made
   * @param date  is date on which sale needs to be made
   * @return true if the given stocks can be sold else return false
   * @throws ParseException is thrown when error while parsing the input portfolio
   */
  public boolean validSell(int num, String path, String tickr, String date)
          throws ParseException {
    if (!thePortfolio.checkValidSell(path, Integer.valueOf(num), tickr, date)) {
      theView.showString("The number entered for selling stocks is more than " +
              "stocks purchased");
      return false;
    }
    return true;
  }

  /**
   * Check if the given portfolio already exists in the given input folder of portfolios.
   *
   * @param rootDir   is the input location where all the portfolios are present
   * @param extension is either .json or .txt
   * @return a valid portfolio name that exists in the given input folder
   */
  @Override
  public String getValidPfName(String rootDir, String extension) {
    theView.showString("Enter valid portfolio name:");
    String pfNameChosen = in.next();
    while (!new File(rootDir + pfNameChosen + extension).exists()) {
      theView.showString("Portfolio with this name doesn't exist. Try again");
      pfNameChosen = in.next();
    }
    return pfNameChosen;
  }


  /**
   * This is used to make a purchase or sell.
   *
   * @param tickr  ticker symbol for which transaction needs to be made
   * @param path   is input path of the portfolio on which transaction needs to be made
   * @param isSell make it true if sale is to be made, set to false if purchase needs to be made
   * @return a message if the transaction is suceessful and portfolio is modified or not
   * @throws java.text.ParseException if an error occurs while parsing the portfolio
   */
  public String makeTransaction(String tickr, String path, boolean isSell) throws
          java.text.ParseException {
    Float fees = getValidCommission();
    String transactionDate = getAndValidateDate("Enter date of transaction:");
    int numberOfStocks = Integer.valueOf(getValidNumberStocks("Number of stocks:"));
    if (isSell) {
      if (!validSell(numberOfStocks, path, tickr, transactionDate)) {
        return "This transaction is not valid.";
      }
      numberOfStocks = numberOfStocks * (-1);
    }
    thePortfolio.modifyJson(fees, numberOfStocks, transactionDate, tickr, path);
    return "The portfolio is successfully modified";
  }

  /**
   * Create a directory on the desktop if user gives invalid path.
   *
   * @param rootDirUser is the root directory given by user to store all the created portfolios
   */
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

  /**
   * Print the performance of the portfolio for the given time range.
   *
   * @param pfPerformance  is the portfolio name for which performance needs to be plotted
   * @param date1          initial argument in the time range
   * @param date2          is the final argument in the time range
   * @param differenceDays is the difference in days between date1 and date2
   * @throws FileNotFoundException if no file is found
   * @throws ParseException        if error occurs while parsing the input portfolio
   */
  public void printPerformance(String pfPerformance, String date1, String date2, int differenceDays)
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
    theView.showString("Minimum price value in the graph is : $" + Collections.min(values));
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
      theView.showFlexibleOptions();
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
          theView.showString("Enter the time span range to view the " +
                  "performance of the portfolio");
          String msgStart = "Enter the start date in YYYY-MM-DD format only:";
          String msgEnd = "Enter the end date value in  YYYY-MM-DD format only:";
          String date1 = getAndValidateDate(msgStart);
          String date2 = getAndValidateDate(msgEnd);
          // if user first input date > second input date prompt invalid range
          while (!thePortfolio.checkValidDates(date1, date2)) {
            theView.showString("Invalid date range please give valid period:");
            date1 = getAndValidateDate(msgStart);
            date2 = getAndValidateDate(msgEnd);
          }
          int differenceDays = thePortfolio.checkDifference(date1, date2);
          // if the difference is less than 5 return error.
          if (differenceDays < 5) {
            theView.showString("The difference is less than 5 days hence not valid");
            break;
          }
          printPerformance(pfPerformance, date1, date2, differenceDays);
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
                String date = getAndValidateDate("Enter the date at which you want to " +
                        "get cost basis in YYYY-MM-DD format only!");
                float costBasis = thePortfolio.getCostBasis(this.rootDir + pfNameChosen
                        + ".json", date);
                theView.showString("The cost basis till date, " + date + " is: $" + costBasis);
                viewDone = true;
                break;
              case "D":
                String valdate = getAndValidateDate("Enter the date for which you want to " +
                        "fetch the portfolio value in YYYY-MM-DD format only!");
                float totVal = thePortfolio.portfolioValueDate(this.rootDir, pfNameChosen, valdate);
                theView.showString("Portfolio Value on " + valdate + " is : $" + totVal);
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
            theView.showString("Press Y to add stocks to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            switch (in.next()) {
              case "S":
                thePortfolio.savePortfolio(this.rootDir + pfName + ".json",
                        addTickr);
                done = true;
                theView.showString("Successfully created the portfolio " + pfName);
                break;
              case "Y":
                Float commission = getValidCommission();
                String tickrpurchase = getValidTickr();
                String numpurchase = getValidNumberStocks("Enter the number of stocks:");
                String datepurchase = getAndValidateDate("Enter the date of purchase:");
                if (!thePortfolio.checkTickrJSONArray(addTickr, tickrpurchase)) {
                  JSONObject addEntry = thePortfolio.makeTransactionRecord(datepurchase,
                          commission, Integer.valueOf(numpurchase), tickrpurchase);
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


