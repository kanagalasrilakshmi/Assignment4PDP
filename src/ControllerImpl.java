import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class ControllerImpl implements Controller {

  private View theView;
  private Portfolio thePortfolio;
  private Scanner in;

  public ControllerImpl(Portfolio thePortfolio, View theView, InputStream in) {
    this.theView = theView;
    this.thePortfolio = thePortfolio;
    this.in = new Scanner(in);
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
          theView.showString("Choose from the list of portfolios");
          // need to put this into model function.
          File curDir = new File(".");
          File[] filesList = curDir.listFiles();
          for(File f : filesList){
            if(f.isFile()){
              // list only .json files.
              if(f.getName().contains(".json")){
                System.out.println(f.getName());
              }
            }
          }
          // type the name of the portfolio from the given list of portfolios.
          theView.showString("Enter the name of the portfolio");
          String pfNameChosen = in.next();
          Portfolio viewObj = new PortfolioImpl(pfNameChosen);
          ArrayList<PortfolioObj>  PortfolioView = viewObj.viewPortfolio();
          // need to print it.
          break;
        case "D":
          // list the portfolios.
          // need to put this into model function.
          File curDirDate = new File(".");
          File[] filesListDate = curDirDate.listFiles();
          for(File f : filesListDate){
            if(f.isFile()){
              // list only .json files.
              if(f.getName().contains(".json")){
                System.out.println(f.getName());
              }
            }
          }
          theView.showString("Enter the name of the portfolio");
          String pFileName = in.next();
          theView.showString("Enter the date on which you want to extract the portfolio");
          String date = in.next();
          Portfolio valueDateObj = new PortfolioImpl(pFileName,date);
          float finalVal = valueDateObj.portfolioValueDate();
          // need to print it.
          break;
        case "C":
          theView.showString("Give a name for the portfolio you want to create:");
          String pfName = in.next();
          ArrayList<StocksObj> objList = new ArrayList<>();
          boolean done = false;
          while (!done) {
            // check if this same name portfolio exists.
            // add a function in model.
            // validate tickr symbol in model.
            // check if tickr symbol is already in the list.
            // backup for api key failure.
            theView.showString("Press Y to add to add stocks to the " + pfName + " portfolio.");
            switch (in.next()) {
              case "S":
                //check if object list is empty nothing to save
                if (objList.size() == 0) {
                  theView.showString("Portfolio must contain at least one entry!! ");
                } else {
                  Portfolio ObjImpl = new PortfolioImpl(objList, pfName);
                  ObjImpl.createPortfolio();
                  done = true;
                }
                break;
              case "Y":
                theView.showString("Enter Valid Stock company tickr symbol");
                String tickr = in.next();
                theView.showString("Enter number of stocks purchased");
                float numberStocks = in.nextFloat();
                objList.add(new StocksObj(tickr, numberStocks));
                theView.showString("Press S to save the Portfolio.");
                break;
            }
          }
      }
    }
  }
}
