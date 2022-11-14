import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
public class testing {
  public static void main(String args[]){
    JSONObject object = new JSONObject();
    JSONObject object1 = new JSONObject();
    JSONArray array = new JSONArray();
    /*
    object.put("date","2022-02-02");
    object.put("fee",90);
    array.add(object);
    String[] objects=new String[]{"GOOG","TSLA"};
    for(int i =0;i<2;i++){
      object1.put(objects[i],array);
    }
    try {
      FileWriter file = new FileWriter(System.getProperty("user.home")+"/Desktop/"+"output.json");
      file.write(object1.toJSONString());
      file.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }*/
    // read from this file.
    JSONParser parser = new JSONParser();
    try (FileReader reader = new FileReader(
            System.getProperty("user.home")+"/Desktop/"+"output.json")) {
      Object parseObj = parser.parse(reader);

      System.out.println(parseObj);
      JSONObject portfolio = (JSONObject) parseObj;
      System.out.println(portfolio.keySet());
      JSONObject entries1 = new JSONObject();
      entries1.put("date","2021-11-01");
      entries1.put("fee","900");
      JSONArray tickr_record = (JSONArray) portfolio.get("GOOG");
      JSONObject check = (JSONObject) tickr_record.get(1);
      check.put("fee",908);
      tickr_record.set(1,check);
      portfolio.put("GOOG",tickr_record);
      /**
      tickr_record.add(entries1);
      portfolio.put("GOOG",tickr_record);
       **/
      try {
        FileWriter file = new FileWriter(System.getProperty("user.home")+"/Desktop/"+"output.json");
        file.write(portfolio.toJSONString());
        file.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      /**
      JSONObject arrayElementOne = new JSONObject();
      arrayElementOne.put("Date", 2);
      arrayElementOne.put("Commission", 2);
      arrayElementOne.put("no_of_stocks", 2);
      arrayElementOne.put("status", 3);
      arrayElementOne.put("remaining_stocks", 3);
      arrayElementOne.put("current_value", 2);
      tickr_record.add(arrayElementOne);
      System.out.println(tickr_record);*/

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
