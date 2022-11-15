import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class testing {
  public static void main(String args[]){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM", Locale.ENGLISH);
    YearMonth startDate = YearMonth.parse("2021-01", formatter);
    YearMonth endDate = YearMonth.parse("2022-11", formatter);

    while(startDate.isBefore(endDate)) {
      System.out.println(startDate.format(formatter));
      startDate = startDate.plusMonths(1);
    }
  }
}
