package controller;

import java.io.File;
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
public class ControllerImpl implements Controller {

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
  public ControllerImpl(Portfolio thePortfolio, View theView, InputStream in) {
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
  public void goStocks() throws IOException {
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
          while (!new File(this.rootDir + pfJsonNameChosen + ".txt").exists()) {
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
                String fees = in.next();
                // ask for which tickr symbol you want to sell stocks.
                // validate that tickr symbol.
                String tickr = in.next();
                // ask the date on which you want to sell the stocks.
                // check if this date is prior or not to the existing dates.
                String date = in.next();
                // ask number of stocks to be sold.
                // check if this number is valid.
                String num = in.next();
                // if all are valid then modify the json.
                transDone = true;
                break;
              case "2":
                // purchasing
                // ask for commission fees.
                // check if it is a valid number.
                String feespurchase = in.next();
                // ask for which tickr symbol you want to purchase stocks.
                // validate that tickr symbol.
                String tickrpurchase = in.next();
                // ask the date on which you want to purchase the stocks.
                // check if this date is prior or not to the existing dates.
                String datepurchase = in.next();
                // ask number of stocks to be sold.
                // check if this number is valid.
                // ask number of stocks to be sold.
                // check if this number is valid.
                String numpurchase = in.next();
                transDone = true;
                break;
              default:
                theView.showString("Press either 1 or 2 only.");
            }
          }
          break;
        case "B":
          // display bar graph of the portfolio.
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
          while (!new File(this.rootDir + pfNameChosen + ".txt").exists()) {
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
                break;
              case "D":
                // get value of the portfolio on that specific date.
                theView.showString("Enter the date for which you want to " +
                        "fetch the portfolio in YYYY-MM-DD format only!");
                String valdate = in.nextLine();
                // check date format.

                while (!thePortfolio.checkIfRightFormat(valdate)) {
                  theView.showString("Please enter correct format for date");
                  date = in.nextLine();
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
                break;
              case "P":
                // view composition of portfolio.
                // display the .json file.
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
          String pfName = in.nextLine();
          ArrayList<String> storinglist = new ArrayList<>();
          while (!thePortfolio.checkValidpfName(pfName)) {
            theView.showString("Please enter a valid portfolio name:");
            pfName = in.nextLine();
          }
          // check if this same name portfolio exists.
          String pfNamePath = this.rootDir + pfName + ".txt";
          while (new File(pfNamePath).exists()) {
            theView.showString("Portfolio with this name already exists! ");
            theView.showString("Give another name for the portfolio you want to create:");
            pfName = in.nextLine();
            pfNamePath = this.rootDir + pfName + ".txt";
          }
          boolean done = false;
          while (!done) {
            theView.showString("Press Y to add stocks " +
                    "to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            ArrayList<Object> objList = new ArrayList<>();
            switch (in.next()) {
              case "S":
                //check if object list is empty nothing to save
                if (objList.size() == 0) {
                  theView.showString("Portfolio must contain at least one entry!! ");
                } else {
                  thePortfolio.createPortfolio(this.rootDir, pfName, objList);
                  done = true;
                  theView.showString("Successfully created the portfolio " + pfName);
                }
                break;
              case "Y":
                theView.showString("Enter Valid Stock company tickr symbol");
                String tickr = in.next();
                // validate tickr symbol in model.
                while (!thePortfolio.validateTickrSymbol(tickr)) {
                  theView.showString("Invalid Tickr Symbol is entered!");
                  theView.showString("Enter Valid Stock company tickr symbol");
                  tickr = in.next();
                }
                while (storinglist.contains(tickr)) {
                  theView.showString("The Tickr symbol already " +
                          "exists! Please enter new Symbol");
                  tickr = in.next();
                }
                // check if tickr symbol is already in the list.
                storinglist.add(tickr);
                // backup for api key failure.
                theView.showString("Enter number of stocks purchased " +
                        "(Integer Values are Only Allowed)");
                String numberStocks = in.next();
                while (!thePortfolio.checkValidInteger(numberStocks)) {
                  theView.showString("Only Integer Stock values " +
                          "are allowed. Please enter a valid Integer number.");
                  numberStocks = in.next();
                }
                break;
              /**
               case "V":
               // check if the output folder has .txt files or not.
               // if no portfolio exists say no portfolio has been added.
               if (!thePortfolio.checkOutputFolder(this.rootDir)) {
               theView.showString("No portfolio exists. Create a portfolio to view it.");
               break;
               }
               // list the portfolios.
               theView.showString("Enter the name of the portfolio you want to view from " +
               "the list of " +
               "portfolios displayed below:");
               theView.listTXTFiles(this.rootDir);
               // check if user enters valid file name.
               String pfNameChosen = in.next();
               while (!new File(this.rootDir + pfNameChosen + ".txt").exists()) {
               theView.showString("Please enter a valid Portfolio name from " +
               "the displayed list only!");
               pfNameChosen = in.next();
               }

               boolean viewDone = false;
               while (!viewDone) {
               theView.showString("Press D to view portfolio value by date");
               theView.showString("Press P to view portfolio composition");
               switch (in.next()) {
               case "D":
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
               // if all the above conditions are not met then it is called for portfolio.
               float finalVal = thePortfolio.portfolioValueDate(this.rootDir, pfNameChosen, date);
               // print the value.
               theView.showString("The total value of the portfolio " + pfNameChosen + " " +
               "is " + finalVal);
               viewDone = true;
               break;
               case "P":
               // view the portfolio.
               thePortfolio.viewPortfolioDisplay(this.rootDir, pfNameChosen);
               // print the value.
               theView.showString("Company Tickr Symbol" + " " + "Num Stocks");
               ArrayList<String>tickrsymbols = thePortfolio.getTickrs();
               ArrayList<String>numStocks= thePortfolio.getNumberStocks();
               for(int i =0;i<tickrsymbols.size();i++){
               theView.showString(tickrsymbols.get(i)+ "                  " +numStocks.get(i));
               }
               viewDone = true;
               break;
               default:
               theView.showString("Please enter either D/P only!!");
               break;
               }
               }

               break;
               **/
              /**
               case "C":
               // check valid if not then it uses the default.
               // initialized path only for saving the portfolios.
               theView.showString("Give a valid name for the portfolio you " +
               "want to create. The string " +
               "should not have spaces or special characters and " +
               "the length must be less than 25 characters.");
               ArrayList<String> storinglist = new ArrayList<>();
               String pfName = in.nextLine();
               while (!thePortfolio.checkValidpfName(pfName)) {
               theView.showString("Please enter a valid portfolio name:");
               pfName = in.nextLine();
               }
               // check if this same name portfolio exists.
               String pfNamePath = this.rootDir + pfName + ".txt";
               while (new File(pfNamePath).exists()) {
               theView.showString("Portfolio with this name already exists! ");
               theView.showString("Give another name for the portfolio you want to create:");
               pfName = in.nextLine();
               pfNamePath = this.rootDir + pfName + ".txt";
               }
               ArrayList<Object> objList = new ArrayList<>();
               boolean done = false;
               while (!done) {
               theView.showString("Press Y to add stocks " +
               "to the " + pfName + " portfolio.");
               theView.showString("Press S to save the Portfolio.");
               switch (in.next()) {
               case "S":
               //check if object list is empty nothing to save
               if (objList.size() == 0) {
               theView.showString("Portfolio must contain at least one entry!! ");
               } else {
               thePortfolio.createPortfolio(this.rootDir, pfName, objList);
               done = true;
               theView.showString("Successfully created the portfolio " + pfName);
               }
               break;
               case "Y":
               theView.showString("Enter Valid Stock company tickr symbol");
               String tickr = in.next();
               // validate tickr symbol in model.
               while (!thePortfolio.validateTickrSymbol(tickr)) {
               theView.showString("Invalid Tickr Symbol is entered!");
               theView.showString("Enter Valid Stock company tickr symbol");
               tickr = in.next();
               }
               while (storinglist.contains(tickr)) {
               theView.showString("The Tickr symbol already " +
               "exists! Please enter new Symbol");
               tickr = in.next();
               }
               // check if tickr symbol is already in the list.
               storinglist.add(tickr);
               // backup for api key failure.
               theView.showString("Enter number of stocks purchased " +
               "(Integer Values are Only Allowed)");
               String numberStocks = in.next();
               while (!thePortfolio.checkValidInteger(numberStocks)) {
               theView.showString("Only Integer Stock values " +
               "are allowed. Please enter a valid Integer number.");
               numberStocks = in.next();
               }
               objList.add(thePortfolio.makeStockObj(tickr, numberStocks));
               break;
               default:
               theView.showString("Please Enter Either S/Y only!!");
               break;
               }
               }
               break;
               **/
              default:
                theView.showOptionError();
                break;
            }
          }
      }
    }
  }
}

