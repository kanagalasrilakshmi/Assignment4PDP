import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementing the Portfolio Interface and coded the implementation.
 */
public class PortfolioImpl implements Portfolio {
  /**
   * Method for creating new portfolio by the user.
   * Dumps all the data entered by user, stores in ListObj to a .txt file.
   */
  public void createPortfolio(String rootDir, String fileName, ArrayList<StocksObj> listObj) {
    // create a txt type file.
    // Create a String type ArrayList.
    ArrayList<String> listAdded = new ArrayList<>();
    try (FileWriter file = new FileWriter(rootDir + fileName + ".txt")) {
      listAdded.add("Company Tickr Symbol,Num Of Stocks");
      for (StocksObj object : listObj) {
        // go through all the elements in the ListObj.
        String toBeAppended = object.getTickr() + "," + String.valueOf(object.getNumStocks());
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
  public ArrayList<PortfolioObj> viewPortfolio(String rootDir, String filename) throws IOException {
    // load the portfolio of the given input file name.
    BufferedReader in = new BufferedReader(new FileReader(rootDir + filename + ".txt"));
    String inputLine;
    ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        if (!(inputLine.split(",")[0].equals("Company Tickr Symbol") &&
                inputLine.split(",")[1].equals("Num Of Stocks"))) {
          String tickrSymbol = inputLine.split(",")[0];
          //ApiKey apiObj = new ApiKey(tickrSymbol);
          int numStocks = Integer.valueOf(inputLine.split(",")[1]);
          // set a timer here.
          // allow api calls for every 25s only.

          viewPortfolioObj.add(new PortfolioObj(tickrSymbol, numStocks));
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
   * Get portfolio value for a given date.
   */
  public float portfolioValueDate(String rootDir, String fileName,
                                  String date) throws FileNotFoundException {
    // initialize sum to 0.
    float finalSum = 0;
    // load the portfolio of the given input file name.
    BufferedReader in = new BufferedReader(new FileReader(rootDir + fileName + ".txt"));
    String inputLine;
    ArrayList<PortfolioObj> viewPortfolioObj = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        if (!(inputLine.split(",")[0].equals("Company Tickr Symbol") &&
                inputLine.split(",")[1].equals("Num Of Stocks"))) {
          String tickrSymbol = inputLine.split(",")[0];
          ApiKey apiObj = new ApiKey(tickrSymbol);
          Float numStocks = Float.valueOf(inputLine.split(",")[1]);
          ArrayListObj tickerSymbolsPrice = this.convertTXT();
          // initialize it as zero
          Float price = 0.0f;
          for (int i = 0; i < tickerSymbolsPrice.getTickrSymbols().size(); i++) {
            if (tickerSymbolsPrice.getTickrSymbols().get(i).equals(tickrSymbol)) {
              price = Float.valueOf(tickerSymbolsPrice.getPrices().get(i));
              break;
            }
          }
          Float apiPrice = apiObj.callPriceDate(date);
          if (apiPrice != 0) {
            price = apiPrice;
          }
          // set a timer here.
          // allow api calls for every 25s only.
          finalSum += numStocks * price;
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
      LocalDate.parse(date, format);
    } catch (DateTimeException e) {
      return false;
    }
    try {
      LocalDate givenDate = LocalDate.parse(date, format);
      LocalDate todayDate = LocalDate.now();
      return givenDate.isAfter(todayDate);
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
    } catch (DateTimeException e) {
      return false;
    }
    try {
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
          } catch (DateTimeException e) {
            e.printStackTrace();
            return false;
          }
        }
        return false;
      } catch (Exception e) {
        e.printStackTrace();
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
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
   * Convert text file to array list consisting of valid tickr symbols.
   *
   * @return array list consisting of all valid tickr symbols
   */
  public ArrayListObj convertTXT() throws FileNotFoundException {
    BufferedReader in = new BufferedReader(new FileReader(new
            File("tickrData.txt").getAbsolutePath()));
    String inputLine;
    ArrayList<String> tickrSymbolsList = new ArrayList<>();
    ArrayList<String> pricesList = new ArrayList<>();
    try {
      while ((inputLine = in.readLine()) != null) {
        String tickrSymbol = inputLine.split(",")[0];
        String prices = inputLine.split(",")[1];
        tickrSymbolsList.add(tickrSymbol);
        pricesList.add(prices);
      }
      return new ArrayListObj(tickrSymbolsList, pricesList);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Validate if the given tickr symbol is valid or not.
   *
   * @param tickrSymbol of type String.
   * @return true if it is valid else false.
   */
  public boolean validateTickrSymbol(String tickrSymbol) {
    // open the tickrlist.
    // check if given symbol is in the text file.
    try {
      ArrayListObj tickrSymbolsPriceList = this.convertTXT();
      return (tickrSymbolsPriceList.getTickrSymbols().contains(tickrSymbol));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Check if there are any spaces or null or empty or length > 25 for the given portfolio name.
   *
   * @param pfName portfolio name of type string
   * @return true if there are any spaces or null or empty or length > 25 else false
   */
  public boolean checkValidpfName(String pfName) {
    if (pfName == null || pfName.length() > 25 || pfName.isEmpty() || pfName.contains(" ")) {
      return false;
    }
    for (int i = 0; i < pfName.length(); i++) {
      if (!Character.isLetter(pfName.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Check if the last ending character is : /.
   *
   * @param rootDirUser is the path given by user in string format
   * @return true if it ends with / else false
   */
  public boolean checkLastEndingCharacter(String rootDirUser) {
    return (rootDirUser.charAt(rootDirUser.length() - 1) == '/');
  }

  /**
   * Check if a given string is an integer.
   *
   * @param numberStocks is number of stocks purchased by user in string format
   * @return true if integer else false
   */
  public boolean checkValidInteger(String numberStocks) {
    for (int i = 0; i < numberStocks.length(); i++) {
      if (!Character.isDigit(numberStocks.charAt(i))) {
        return false;
      }
    }
    return true;
  }
}
