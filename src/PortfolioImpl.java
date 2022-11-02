import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementing the Portfolio Interface and coded the implementation.
 */
public class PortfolioImpl implements Portfolio {
  private ArrayList<StocksObj> ListObj;
  private String fileName;
  private String date;

  public PortfolioImpl() {
  }

  public PortfolioImpl(ArrayList<StocksObj> ListObj, String fileName) {
    this.fileName = fileName;
    this.ListObj = ListObj;
  }

  public PortfolioImpl(String fileName) {
    this.fileName = fileName;
  }

  public PortfolioImpl(String fileName, String date) {
    this.fileName = fileName;
    this.date = date;
  }

  /**
   * Method for creating new portfolio by the user.
   */
  public void createPortfolio(String rootDir) {
    // create a txt type file.
    // Create a String type ArrayList.
    ArrayList<String> listAdded = new ArrayList<>();
    try (FileWriter file = new FileWriter(rootDir + this.fileName + ".txt")) {
      listAdded.add("Company Tickr Symbol,Num Of Stocks");
      for (StocksObj Object : this.ListObj) {
        // go through all the elements in the ListObj.
        String toBeAppended = Object.getTickr() + "," + String.valueOf(Object.getNumStocks());
        listAdded.add(toBeAppended);
      }
      for (int i = 0; i < listAdded.size(); i++) {
        file.write(listAdded.get(i));
        // add a new line.
        if (i < listAdded.size() - 1) {
          file.write("\n");
        }
      }
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for displaying the portfolio.
   */
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir) throws IOException {
    // load the portfolio of the given input file name.
    BufferedReader in = new BufferedReader(new FileReader(rootDir + this.fileName + ".txt"));
    String inputLine;
    ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        if (inputLine.split(",")[0] != "Company Tickr Symbol" &&
                inputLine.split(",")[1] != "Num Of Stocks") {
          String tickrSymbol = inputLine.split(",")[0];
          ApiKey apiObj = new ApiKey(tickrSymbol);
          Float numStocks = Float.valueOf(inputLine.split(",")[1]);
          viewPortfolioObj.add(new PortfolioObj(tickrSymbol, numStocks, apiObj.callPresentPrice()));
        }
      }
      return viewPortfolioObj;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Get portfolio value for a given date
   */
  public float portfolioValueDate(String rootDir) throws FileNotFoundException {
    // initialize sum to 0.
    float finalSum = 0;
    // load the portfolio of the given input file name.
    BufferedReader in = new BufferedReader(new FileReader(rootDir + this.fileName + ".txt"));
    String inputLine;
    ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null){
        if (inputLine.split(",")[0] != "Company Tickr Symbol" &&
                inputLine.split(",")[1] != "Num Of Stocks") {
          String tickrSymbol = inputLine.split(",")[0];
          ApiKey apiObj = new ApiKey(tickrSymbol);
          Float numStocks = Float.valueOf(inputLine.split(",")[1]);
          finalSum += numStocks * apiObj.callPriceDate(this.date);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return finalSum;
  }

  /**
   * Check if the file exists in the given directory.
   *
   * @param pfNamePath path of the file
   * @return true if exists else false
   */
  public boolean checkExists(String pfNamePath) {
    File tempFile = new File(pfNamePath);
    return tempFile.exists();
  }

  /**
   * Check if the given output folder has any portfolios.
   *
   * @param rootDir is the path
   * @return true if there are any portfolios else false
   */
  public boolean checkOutputFolder(String rootDir) {
    File curDir = new File(rootDir);
    File[] filesList = curDir.listFiles();
    for (File file : filesList) {
      if (file.isFile()) {
        if (file.getName().contains(".txt")) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * check for future date.
   *
   * @param date input string type date
   * @return true if future else return false
   */
  public boolean checkFutureDate(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate givenDate = LocalDate.parse(date, format);
      LocalDate todayDate = LocalDate.now();
      if (givenDate.isAfter(todayDate)) {
        return true;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * check if date is today's date and time is before 9:30am.
   *
   * @param date input string type date
   * @return true if date is today's date and time is before 9:30am else false
   */
  public boolean checkTodayDateAndTime(String date) {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    try {
      LocalDate givenDate = LocalDate.parse(date, format);
      LocalDate todayDate = LocalDate.now();
      // check today date.
      if (givenDate.isEqual(todayDate)) {
        // check if time is less than 9:30 am.
        LocalTime timeNow = LocalTime.now();
        String valTimeNow = String.valueOf(timeNow);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
          Date dateFormat = timeFormat.parse(valTimeNow);
          if (dateFormat.before(timeFormat.parse("09:30"))) {
            return true;
          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        return false;
      }
      return false;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * CHeck if the date given by the user follows the user input.
   *
   * @param date input string type date
   * @return true if right format is given else false
   */
  public boolean checkIfRightFormat(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    format.setLenient(false);
    try {
      format.parse(date);
      return true;
    } catch (java.text.ParseException e) {
      return false;
    }
  }

  /**
   * Check if the output folder where .txt needs to be saved exists.
   * @param rootDir path for the folder
   * @return true if exists else false
   */
  public boolean checkFolderExists(String rootDir){
    return new File(rootDir).exists();
  }

  /**
   * Create the output folder.
   * @param rootDir path where the folder needs to be created
   */
  public void createFolder(String rootDir) throws IOException {
    try{
      Path path = Paths.get(rootDir);
      Files.createDirectories(path);
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }
}
