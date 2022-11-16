import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;

public class Testincg {
  public static void main(String args[]) throws ParseException {
    Date date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse("Jan");
    System.out.println(date);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int month = cal.get(Calendar.MONTH);
    System.out.println(month+1);
    /**
    String h = "Feb 2021";
    String h2 = "Feb 2004";
    String arr1[] = h.split(" ");
    String arr2[] = h2.split(" ");
    LocalDate yearMonth =  YearMonth.of(Integer.valueOf(arr2[1]),mapMonths.get(arr2[0])).atEndOfMonth();
    System.out.println(yearMonth.toString());
     **/
  }

}
