import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class that creates controller,model and view methods.
 */
public class StocksMain {
  public static void main(String args[]){
    System.out.println("Welcome to the Stocks Program");
    System.out.println("Please tell what would u like to do");
    Scanner scan = new Scanner(System.in);
    while(true){
      // ask an option on what to do.
      int category = scan.nextInt();
      // Type 1 to create portfolio.
      if(category == 1){
        while(true){
          System.out.println("Enter portfolio name");
          String name = scan.next();
          ArrayList<StocksObj>objList = new ArrayList<>();
          while(true){
            System.out.println("Enter Valid Stock company tickr symbol");
            String tickr = scan.next();
            System.out.println("Enter number of stocks purchased");
            float numberStocks = scan.nextFloat();
            objList.add(new StocksObj(tickr,numberStocks));
            System.out.println("Do you want to Continue? Y/N");
            String ans = scan.next();
            if(ans == "N"){
              break;
            }
          }
          Portfolio ObjImpl = new PortfolioImpl(objList,name);
          ObjImpl.createPortfolio();
          System.out.println("Do you want to add another Portfolio? Y/N");
          String ans = scan.next();
          if(ans == "N"){
            break;
          }
        }
      }
      // Type 2 to view portfolio.
      else if(category == 2){
        // list the portfolios.
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
        System.out.println("Enter the name of the portfolio");
        String filename = scan.next();
        // choose one of the portfolio.
        Portfolio viewObj = new PortfolioImpl(filename);
        // view the portfolio.
        ArrayList<PortfolioObj>  PortfolioView = viewObj.viewPortfolio();
      }
      // Type 3 to access portfolio value on a particular date.
      else if(category == 3){
        // list the portfolios.
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
        System.out.println("Enter the name of the portfolio");
        String filename = scan.next();
        // Enter the date on which you want to get value for.
        String date = scan.next();
        // choose one of the portfolio.
        Portfolio valueDateObj = new PortfolioImpl(filename,date);
        float finalVal = valueDateObj.portfolioValueDate();
      }
      else{
        System.out.println("Wrong option is chosen please chose right option");
      }
    }
  }
}
