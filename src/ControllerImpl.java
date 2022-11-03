import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
   * function to run the Stocks implementation.
   *
   * @throws ParseException when parsing of a date fails.
   * @throws IOException    when given input is not valid
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
      // create a portfolio bucket if it does not exist.
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
        case "V":
          // check if the output folder has .txt files or not.
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolio exists. Create a portfolio to view it.");
            break;
          }
          // list the portfolios.
          theView.showString("Enter a name from the list of portfolios displayed below:");
          theView.listTXTFiles(this.rootDir);
          // check if user enters valid file name.
          String pfNameChosen = in.nextLine();
          while (!new File(this.rootDir + pfNameChosen + ".txt").exists()) {
            theView.showString("Please enter a valid Portfolio name from " +
                    "the displayed list only!");
            pfNameChosen = in.nextLine();
          }
          ArrayList<PortfolioObj> portfolioView = thePortfolio.viewPortfolio(
                  this.rootDir, pfNameChosen);
          // print the value.
          theView.showString("Company Tickr Symbol" + " " + "Num Stocks");
          for (PortfolioObj obj : portfolioView) {
            theView.showString(obj.getTickr() + "                  " + obj.getNumStocks());
          }
          break;
        case "D":
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolio exists. Please create a portfolio to view it.");
            break;
          }
          // list the portfolios.
          theView.showString("Enter a name from the list of portfolios displayed below:");
          theView.listTXTFiles(this.rootDir);
          // check if user enters valid file name.
          String pFileName = in.nextLine();
          while (!new File(this.rootDir + pFileName + ".txt").exists()) {
            theView.showString("Please enter a valid Portfolio" +
                    " name from the displayed list only!!");
            pFileName = in.nextLine();
          }
          theView.showString("Enter the date on which you want to " +
                  "extract the portfolio in YYYY-MM-DD format only!");
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
                      "is yet to be opened today! Please wait till 9:30 am.");
              break;
            }
          } catch (Exception e) {
            e.printStackTrace();
            break;
          }
          // if all the above conditions are not met then it is called for portfolio.
          float finalVal = thePortfolio.portfolioValueDate(this.rootDir, pFileName, date);
          // print the value.
          theView.showString("The total value of the portfolio " + pFileName + " is " + finalVal);
          break;
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
            theView.showString("Portfolio with this name already exists.! ");
            theView.showString("Give another name for the portfolio you want to create:");
            pfName = in.nextLine();
            pfNamePath = this.rootDir + pfName + ".txt";
          }
          ArrayList<StocksObj> objList = new ArrayList<>();
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
                int numberStocks = 0;
                try {
                  numberStocks = in.nextInt();
                } catch (InputMismatchException e) {
                  theView.showString("Enter number of stocks purchased " +
                          "(Integer Values are Only Allowed)");
                  theView.showString("Only Integer Stock values " +
                          "are allowed are allowed!!");
                  storinglist.remove(tickr);
                  break;
                }
                objList.add(new StocksObj(tickr, numberStocks));
                break;
              default:
                theView.showString("Please Enter Either S/Y only!!");
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
