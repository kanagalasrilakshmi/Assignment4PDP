import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiKey {
  private String tickrsymbol;
  public ApiKey(String tickrsymbol){
    this.tickrsymbol = tickrsymbol;
  }
  public String getTickrSymbol(){
    return this.tickrsymbol;
  }
  public float callPresentPrice(){
    String apiKey = "W0M1JOKC82EZEQA8";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + this.getTickrSymbol() + "&apikey="+apiKey+"&datatype=csv");
    }
    catch (MalformedURLException e) {
      throw new RuntimeException("the alphavantage API has either changed or "
              + "no longer works");
    }
    StringBuilder output = new StringBuilder();
    float num =0;
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
      String inputLine;
      while ((inputLine = in.readLine()) != null){
        String currentDate = String.valueOf(java.time.LocalDate.now());
        String h1 = String.valueOf(java.time.LocalDate.now());
        String h2 = inputLine.split(",")[0];
        if(h1.equals(h2)){
          num = Float.valueOf(inputLine.split(",")[4]);
        }
      }
    }
    catch (IOException e) {
      throw new IllegalArgumentException("No price data found for "+this.getTickrSymbol());
    }
    return num;
  }
}
