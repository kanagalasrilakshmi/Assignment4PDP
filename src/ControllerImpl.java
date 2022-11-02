import java.io.IOException;
import java.io.InputStream;
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
    rootDir = "/Users/PortfolioBucket/";
  }

  public void go() throws IOException {
    boolean quit = false;
    while (!quit) {
      theView.showOptions();
      switch (in.next()) {
        case "Q":
          quit = true;
          break;
        case "V":
          // check if output folder is present. If not present create it.
          if(!thePortfolio.checkFolderExists(rootDir)){
            thePortfolio.createFolder(rootDir);
          }
          // check if the output folder has .txt files or not.
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(rootDir)) {
            theView.showString("No portfolios are present!");
            break;
          }
          // list the portfolios.
          theView.showString("Choose from the list of portfolios");
          theView.listJsonFiles();
          theView.showString("Enter the name of the portfolio from the displayed list");
          // check if user enters valid file name.
          // enters a portfolio name that does not exist.
          // type the name of the portfolio from the given list of portfolios.
          String pfNameChosen = in.next();
          while (!thePortfolio.checkExists(rootDir + pfNameChosen + ".txt")) {
            theView.showString("Please enter a valid Portfolio name from the displayed list only!!");
            pfNameChosen = in.next();
          }
          Portfolio viewObj = new PortfolioImpl(pfNameChosen);
          ArrayList<PortfolioObj> PortfolioView = viewObj.viewPortfolio(rootDir);
          // print the value.
          theView.showString("Company Tickr Symbol" + " " + "Num Stocks Purchased" + " " + "Stock Price");
          for (PortfolioObj obj : PortfolioView) {
            theView.showString(obj.getTickr() + "                  " + obj.getNumStocks() + "                    " + obj.getStockPrice());
          }
          break;
        case "D":
          // check if output folder is present. If not present create it.
          if(!thePortfolio.checkFolderExists(rootDir)){
            thePortfolio.createFolder(rootDir);
          }
          // check if the output folder has .txt files or not.
          // if no portfolio exists say no portfolio has been added.
          if (!thePortfolio.checkOutputFolder(rootDir)) {
            theView.showString("No portfolios are present!");
            break;
          }
          // list the portfolios.
          theView.showString("Choose from the list of portfolios");
          theView.listJsonFiles();
          // type the name of the portfolio from the given list of portfolios.
          theView.showString("Enter the name of the portfolio from the list");
          // check if user enters valid file name.
          // enters a portfolio name that does not exist.
          // type the name of the portfolio from the given list of portfolios.
          String pFileName = in.next();
          while (!thePortfolio.checkExists(rootDir + pFileName + ".txt")) {
            theView.showString("Please enter a valid Portfolio name from the displayed list only!!");
            pFileName = in.next();
          }
          theView.showString("Enter the date on which you want to extract the portfolio in YYYY-MM-DD format only!");
          String date = in.next();
          // check date format.

          while (!thePortfolio.checkIfRightFormat(date)) {
            theView.showString("Please enter correct format for date");
            date = in.next();
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
          Portfolio valueDateObj = new PortfolioImpl(pFileName, date);
          float finalVal = valueDateObj.portfolioValueDate(rootDir);
          // print the value.
          theView.showString("The total value of the portfolio " + pFileName + " is " + finalVal);
          break;
        case "C":
          theView.showString("Give a name for the portfolio you want to create:");
          String pfName = in.next();
          // check if this same name portfolio exists.
          String pfNamePath = rootDir + pfName + ".txt";
          // check if output folder is present. If not present create it.
          if(!thePortfolio.checkFolderExists(rootDir)){
            thePortfolio.createFolder(rootDir);
          }
          while (thePortfolio.checkExists(pfNamePath)) {
            theView.showString("Portfolio with this name already exists.! ");
            theView.showString("Give another name for the portfolio you want to create:");
            pfName = in.next();
            pfNamePath = rootDir + pfName + ".txt";
          }
          ArrayList<StocksObj> objList = new ArrayList<>();
          boolean done = false;
          while (!done) {
            // validate tickr symbol in model.
            // check if tickr symbol is already in the list.
            // backup for api key failure.
            theView.showString("Press Y to add to add stocks to the " + pfName + " portfolio.");
            theView.showString("Press S to save the Portfolio.");
            switch (in.next()) {
              case "S":
                //check if object list is empty nothing to save
                if (objList.size() == 0) {
                  theView.showString("Portfolio must contain at least one entry!! ");
                } else {
                  Portfolio ObjImpl = new PortfolioImpl(objList, pfName);
                  ObjImpl.createPortfolio(this.rootDir);
                  done = true;
                  theView.showString("Successfully created the portfolio " + pfName);
                }
                break;
              case "Y":
                theView.showString("Enter Valid Stock company tickr symbol");
                String tickr = in.next();
                theView.showString("Enter number of stocks purchased");
                float numberStocks = in.nextFloat();
                objList.add(new StocksObj(tickr, numberStocks));
                break;
              default:
                theView.showString("Please Enter Either S/Y only!!");
            }
          }
          break;
        default:
          theView.showOptionError();
      }
    }
  }
}
