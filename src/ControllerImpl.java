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
    this.rootDir = "/Users/PortfolioBucket/";
  }

  public void go() {
    boolean quit = false;
    while (!quit) {
      theView.showOptions();
      switch (in.next()) {
        case "Q":
          quit = true;
          break;
        case "V":
          // list the portfolios.
          theView.showString("Choose from the list of portfolios");
          theView.listJsonFiles();
          // if no portfolio exists say no portfolio has been added.
          // type the name of the portfolio from the given list of portfolios.
          theView.showString("Enter the name of the portfolio from the list");
          String pfNameChosen = in.next();
          Portfolio viewObj = new PortfolioImpl(pfNameChosen);
          ArrayList<PortfolioObj> PortfolioView = viewObj.viewPortfolio(this.rootDir);
          // print the value.
          theView.showString("Company Tickr Symbol" + " " + "Num Stocks Purchased" + " " + "Stock Price");
          for (PortfolioObj obj : PortfolioView) {
            theView.showString(obj.getTickr() + " " + obj.getNumStocks() + " " + obj.getStockPrice());
          }
          break;
        case "D":
          // list the portfolios.
          theView.showString("Choose from the list of portfolios");
          theView.listJsonFiles();
          // type the name of the portfolio from the given list of portfolios.
          theView.showString("Enter the name of the portfolio from the list");
          String pFileName = in.next();
          theView.showString("Enter the date on which you want to extract the portfolio");
          String date = in.next();
          Portfolio valueDateObj = new PortfolioImpl(pFileName, date);
          float finalVal = valueDateObj.portfolioValueDate(this.rootDir);
          // print the value
          theView.showString("The total value of the portfolio " + pFileName + " is " + finalVal);
          break;
        case "C":
          theView.showString("Give a name for the portfolio you want to create:");
          String pfName = in.next();
          // check if this same name portfolio exists.
          String pfNamePath = rootDir + pfName + ".json";
          while (thePortfolio.checkExists(pfNamePath)) {
            theView.showString("Portfolio with this name already exists.! ");
            theView.showString("Give another name for the portfolio you want to create:");
            pfName = in.next();
            pfNamePath = rootDir + pfName + ".json";
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
                  theView.showString("Sucessfully created the portfolio "+pfName);
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
