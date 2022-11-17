package controller;

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
  public void goStocks() throws IOException, ParseException {
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
        case "C":
          // check valid if not then it uses the default.
          // initialized path only for saving the portfolios.
          ArrayList<String> storinglist = new ArrayList<>();
          // check if this same name portfolio exists.
          String pfName = createValidPf(this.rootDir, ".txt");
          ArrayList<Object> objList = new ArrayList<>();
          boolean done = false;
          while (!done) {
            theView.showString("Press Y to add stocks " +
                    "to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            switch (in.next()) {
              case "S":
                if (objList.size() == 0) {
                  theView.showString("Portfolio must contain at least one entry!! ");
                } else {
                  thePortfolio.createPortfolio(this.rootDir, pfName, objList);
                  done = true;
                  theView.showString("Successfully created the portfolio " + pfName);
                }
                break;
              case "Y":
                String tickr = getValidTickr(storinglist);
                storinglist.add(tickr);
                String numberStocks = getValidNumberStocks("Enter number of stocks purchased " +
                        "(Integer Values are Only Allowed)");
                objList.add(thePortfolio.makeStockObj(tickr, numberStocks));
                break;
              default:
                theView.showString("Please Enter Either S/Y only!!");
                break;
            }
          }
          break;
        case "V":
          if (!thePortfolio.hasAtleastOnePortfolio(this.rootDir, ".txt")) {
            theView.showString("No portfolio exists. Create a portfolio to view it.");
            break;
          }
          if (!doneListingPortfolios(this.rootDir, ".txt")) {
            break;
          }
          String pfNameChosen = getValidPfName(this.rootDir, ".txt");
          boolean viewDone = false;
          while (!viewDone) {
            theView.showString("Press D to view portfolio value by date");
            theView.showString("Press P to view portfolio composition");
            switch (in.next()) {
              case "D":
                String date = getAndValidateDate("Enter the date for which you want to fetch " +
                        "the portfolio in YYYY-MM-DD format only!");
                float finalVal = thePortfolio.portfolioValueDate(this.rootDir, pfNameChosen, date);
                theView.showString("The total value of the portfolio " + pfNameChosen + " " +
                        "is $" + finalVal);
                viewDone = true;
                break;
              case "P":
                thePortfolio.viewPortfolioDisplay(this.rootDir, pfNameChosen);
                theView.showString("Company Tickr Symbol" + " " + "Num Stocks");
                ArrayList<String> tickrsymbols = thePortfolio.getTickrs();
                ArrayList<String> numStocks = thePortfolio.getNumberStocks();
                for (int i = 0; i < tickrsymbols.size(); i++) {
                  theView.showString(tickrsymbols.get(i) + "                  " +
                          numStocks.get(i));
                }
                viewDone = true;
                break;
              default:
                theView.showString("Please enter either D/P only!!");
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
   * Check if the given portfolio already exists in the given input folder of portfolios.
   *
   * @param rootDir   is the input location where all the portfolios are present
   * @param extension is either .json or .txt
   * @return a valid portfolio name that exists in the given input folder
   */
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
   * Checks if the given input tickr symbol by the user exists by validating,
   * in the list of valid symbols present in the tickrData.txt file.
   *
   * @return a valid tickr from the user that is also there in the tickrData.txt file
   * @throws FileNotFoundException if there is a problem finding tickrData.txt file
   */
  public String getValidTickr(ArrayList<String> storinglist) throws FileNotFoundException {
    theView.showString("Enter Valid Stock company tickr symbol");
    String tickr = in.next();
    while (!thePortfolio.validateTickrSymbol(tickr)) {
      theView.showString("Invalid Tickr Symbol! Enter valid company tickr symbol:");
      tickr = in.next();
    }
    while (storinglist.contains(tickr)) {
      theView.showString("The Tickr symbol already " +
              "exists! Please enter new Symbol");
      tickr = in.next();
    }
    return tickr;
  }

  /**
   * Takes in valid input for stocks which should allow only integer values.
   *
   * @param message is used to ask the user to enter the stock price
   * @return valid stock price entered by the user
   */
  public String getValidNumberStocks(String message) {
    theView.showString(message);
    String numberStocks = in.next();
    while (!thePortfolio.checkValidInteger(numberStocks)) {
      theView.showString("Only Integer Stock values " +
              "are allowed. Please enter a valid Integer number.");
      numberStocks = in.next();
    }
    return numberStocks;
  }

  /**
   * List all the portfolios with the given extension of the folder where portfolios are present.
   *
   * @param path      is the portfolio input folder
   * @param extension is either, .json or .txt
   * @return false if no portfolios are present in the given input folder else print all of them
   */
  public boolean doneListingPortfolios(String path, String extension) {
    if (!thePortfolio.hasAtleastOnePortfolio(path, extension)) {
      theView.showString("No portfolio exists. Create a portfolio.");
      return false;
    }
    theView.showString("Enter the name of the portfolio you want, from " +
            "the list of " + "portfolios displayed below:");
    theView.listTXTFiles(this.rootDir);
    return true;
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
}

