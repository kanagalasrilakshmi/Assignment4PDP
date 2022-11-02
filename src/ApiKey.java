import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ApiKey {
  private String tickrsymbol;

  public ApiKey(String tickrsymbol) {
    this.tickrsymbol = tickrsymbol;
  }

  public String getTickrSymbol() {
    return this.tickrsymbol;
  }

  /**
   * get the price for the present day stock.
   *
   * @return float type price of the stock
   */
  public float callPresentPrice() {
    String apiKey = "W0M1JOKC82EZEQA8";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + this.getTickrSymbol() + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    StringBuilder output = new StringBuilder();
    float num = 0;
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        String h2 = inputLine.split(",")[0];
        if (!h2.equals("timestamp")) {
          num = Float.valueOf(inputLine.split(",")[4]);
          break;
        }
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + this.getTickrSymbol());
    }
    return num;
  }

  /**
   * get the price for a given date.
   *
   * @return float type price of the stock
   */
  public float callPriceDate(String date) {
    String apiKey = "W0M1JOKC82EZEQA8";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + this.getTickrSymbol() + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    StringBuilder output = new StringBuilder();
    float num = 0;
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null) {
        String dateSheet = inputLine.split(",")[0];
        // check if the given date falls on sat or sun.
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        try {
          Date dateCheck = formatDate.parse(date);
          Calendar cal = Calendar.getInstance();
          cal.setTime(dateCheck);
          int weekday = cal.get(Calendar.DAY_OF_WEEK);
          if (weekday == Calendar.SATURDAY) {
            // set one day back i.e. friday date.
            LocalDate fridayDate = LocalDate.parse(formatDate.format(dateCheck)).minusDays(1);
            date = String.valueOf(fridayDate);
          } else if (weekday == Calendar.SUNDAY) {
            // set 2 days back date i.e. friday date.
            LocalDate fridayDate = LocalDate.parse(formatDate.format(dateCheck)).minusDays(2);
            date = String.valueOf(fridayDate);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        if (date.equals(dateSheet)) {
          num = Float.valueOf(inputLine.split(",")[4]);
          break;
        }

      }
    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + this.getTickrSymbol());
    }
    return num;
  }
}
