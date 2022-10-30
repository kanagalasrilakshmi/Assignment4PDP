import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlphaVantageDemo {
  public static void main(String []args) {
    //the API key needed to use this web service.
    //Please get your own free API key here: https://www.alphavantage.co/
    //Please look at documentation here: https://www.alphavantage.co/documentation/
    String apiKey = "W0M1JOKC82EZEQA8";
    String stockSymbol = "GOOG"; //ticker symbol for Google
    URL url = null;

    try {
      /*
      create the URL. This is the query to the web service. The query string
      includes the type of query (DAILY stock prices), stock symbol to be
      looked up, the API key and the format of the returned
      data (comma-separated values:csv). This service also supports JSON
      which you are welcome to use.
       */
      url = new URL("https://www.alphavantage"
                    + ".co/query?function=TIME_SERIES_DAILY"
                    + "&outputsize=full"
                    + "&symbol"
                    + "=" + stockSymbol + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
                                 + "no longer works");
    }

    //InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      /*
      Execute this query. This returns an InputStream object.
      In the csv format, it returns several lines, each line being separated
      by commas. Each line contains the date, price at opening time, highest
      price for that date, lowest price for that date, price at closing time
      and the volume of trade (no. of shares bought/sold) on that date.

      This is printed below.
       */
      //in = url.openStream();

      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null){
        //String currentDate = String.valueOf(java.time.LocalDate.now());
        String currentDate = "2022-10-15";
        //String h1 = String.valueOf(java.time.LocalDate.now());
        String h1 = currentDate;
        String h2 = inputLine.split(",")[0];
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
          Date date = formatDate.parse(h1);
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          int weekday = cal.get(Calendar.DAY_OF_WEEK);
          if (weekday == Calendar.SATURDAY) {
            // set one day back i.e. friday date.
            LocalDate fridayDate = LocalDate.parse(formatDate.format(date)).minusDays(1);
            h1 = String.valueOf(fridayDate);
            //LocalDate fridayDate = LocalDate.now().minusDays(2);
            //h1 = String.valueOf(fridayDate);
          } else if (weekday == Calendar.SUNDAY) {
            // set 2 days back date i.e. friday date.
            //LocalDate fridayDate = LocalDate.now().minusDays(2);
            //h1 = String.valueOf(fridayDate);
            LocalDate fridayDate = LocalDate.parse(formatDate.format(date)).minusDays(2);
            h1 = String.valueOf(fridayDate);
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
        if(h1.equals(h2)){
          System.out.println(h1);
          System.out.println(inputLine.split(",")[1]);
          System.out.println(inputLine.split(",")[2]);
          System.out.println(inputLine.split(",")[3]);
          //Float num = Float.valueOf(inputLine.split(",")[4]);
          System.out.println(inputLine.split(",")[5]);
        }
      }
      int b;
      while ((b=in.read())!=-1) {
        output.append((char)b);
      }
      String newString = output.toString();

      //System.out.println(output.toString().split(",")[1]);
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+stockSymbol);
    }
    System.out.println("Return value: ");
    String h = output.toString();
    System.out.println(h);
  }
}
