import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class ControllerImpl implements Controller {

  private View theView;
  private Portfolio thePortfolio;
  private Scanner in;
  private String rootDir;

  public ControllerImpl(Portfolio thePortfolio, View theView, InputStream in) {
    this.theView = theView;
    this.thePortfolio = thePortfolio;
    this.in = new Scanner(in);
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }

  public void go() throws IOException {
    boolean quit = false;
    theView.showString("Give a valid input path where you want to store the created portfolios!");

    // ask the user to give valid input path for storing the portfolio.
    String rootDirUser = in.nextLine();
    while (!thePortfolio.checkLastEndingCharacter(rootDirUser)) {
      theView.showString("Give a valid absolute path that ends with a /:");
      rootDirUser = in.nextLine();
    }
    if (new File(rootDirUser).exists()) {
      this.rootDir = rootDirUser;
    } else {
      theView.showString("Invalid path is given so by default portfolios are stored in "
              + this.rootDir + " path.");
    }
    while (!quit) {
      theView.showOptions();
      switch (in.nextLine()) {
        case "Q":
          quit = true;
          break;
        case "V":
          // check if output folder is present. If not present break.
          if (!new File(this.rootDir).exists()) {
            theView.showString("No portfolios are created!!");
            break;
          }
          // check if the output folder has .txt files or not.
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolios are present!");
            break;
          }
          // list the portfolios.
          theView.showString("Choose from the list of portfolios");
          theView.listJsonFiles(this.rootDir);
          theView.showString("Enter the name of the portfolio from the displayed list");
          // check if user enters valid file name.
          // enters a portfolio name that does not exist.
          // type the name of the portfolio from the given list of portfolios.
          String pfNameChosen = in.nextLine();
          while (!new File(this.rootDir + pfNameChosen + ".txt").exists()){
            theView.showString("Please enter a valid Portfolio name from the displayed list only!!");
            pfNameChosen = in.nextLine();
          }
          ArrayList<PortfolioObj> PortfolioView = thePortfolio.viewPortfolio(this.rootDir, pfNameChosen);
          // print the value.
          theView.showString("Company Tickr Symbol" + " " + "Num Stocks");
          for (PortfolioObj obj : PortfolioView) {
            theView.showString(obj.getTickr() + "                  " + obj.getNumStocks());
          }
          break;
        case "D":
          // check if output folder is present. If not present create it.
          if (!new File(this.rootDir).exists()) {
            theView.showString("No portfolios are created!!");
            break;
          }
          // check if the output folder has .txt files or not.
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(this.rootDir)) {
            theView.showString("No portfolios are present!");
            break;
          }
          // list the portfolios.
          theView.showString("Choose from the list of portfolios");
          theView.listJsonFiles(this.rootDir);
          // type the name of the portfolio from the given list of portfolios.
          theView.showString("Enter the name of the portfolio from the list");
          // check if user enters valid file name.
          // enters a portfolio name that does not exist.
          // type the name of the portfolio from the given list of portfolios.
          String pFileName = in.nextLine();
          while (! new File (this.rootDir + pFileName + ".txt").exists()) {
            theView.showString("Please enter a valid Portfolio name from the displayed list only!!");
            pFileName = in.nextLine();
          }
          theView.showString("Enter the date on which you want to extract the portfolio in YYYY-MM-DD format only!");
          String date = in.nextLine();
          // check date format.

          while (!thePortfolio.checkIfRightFormat(date)) {
            theView.showString("Please enter correct format for date");
            date = in.nextLine();
          }
          // check if future date is entered.
          if (thePortfolio.checkFutureDate(date)) {
            theView.showString("Future Date is entered for which portfolio cannot be accessed!!");
            break;
          }
          // check if today's date is entered and stock market is yet to opened.
          try {
            if (thePortfolio.checkTodayDateAndTime(date)) {
              theView.showString("Stock market is yet to be opened!!!");
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
          // check valid if not then it uses the default initialized path only for saving the portfolios.
          theView.showString("Give a name for the portfolio you want to create,(The string " +
                  "should not have spaces,null,emptystring,and length less than 25 " +
                  "must be entered in a single word)");
          ArrayList<String> StoringList = new ArrayList<>();
          String pfName = in.nextLine();
          while (!thePortfolio.checkValidpfName(pfName)) {
            theView.showString("Please enter a valid portfolio name:");
            pfName = in.nextLine();
          }
          // check if this same name portfolio exists.
          String pfNamePath = this.rootDir + pfName + ".txt";
          // check if output folder is present. If not present create it.
          if (!new File(this.rootDir).exists()) {
            try {
              Path path = Paths.get(this.rootDir);
              Files.createDirectories(path);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          while (new File(pfNamePath).exists()) {
            theView.showString("Portfolio with this name already exists.! ");
            theView.showString("Give another name for the portfolio you want to create:");
            pfName = in.nextLine();
            pfNamePath = this.rootDir + pfName + ".txt";
          }
          ArrayList<StocksObj> objList = new ArrayList<>();
          boolean done = false;
          while (!done) {
            theView.showString("Press Y to add to add stocks to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            switch (in.nextLine()) {
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
                String tickr = in.nextLine();
                // validate tickr symbol in model.
                while (!thePortfolio.validateTickrSymbol(tickr)) {
                  theView.showString("Invalid Tickr Symbol is entered!");
                  theView.showString("Enter Valid Stock company tickr symbol");
                  tickr = in.nextLine();
                }
                while (StoringList.contains(tickr)) {
                  theView.showString("The Tickr symbol already exists! Please enter new Symbol");
                  tickr = in.nextLine();
                }
                // check if tickr symbol is already in the list.
                StoringList.add(tickr);
                // backup for api key failure.
                theView.showString("Enter number of stocks purchased");
                float numberStocks = in.nextFloat();
                objList.add(new StocksObj(tickr, numberStocks));
                break;
              default:
                theView.showString("Please Enter Either S/Y only!!");
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
