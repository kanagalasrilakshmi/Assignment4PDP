package model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 The implementation for file operations that takes care of all the save and retrieve operations
 throughout the program.
 */
public class FileOperationImplementation implements FileOperation {

    /**
     * Save a portfolio.
     *
     * @param path portfolio path where json needs to be saved
     * @param data portfolio json object
     */
    public void savePortfolio(String path, JSONObject data) {
        try {
            FileWriter file = new FileWriter(path);
            file.write(data.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Read the Portfolio for the given path for the portfolio.
     *
     * @param path portfolio absolute path
     * @return a json object that consists of all the entries in the input portfolio path
     */
    public JSONObject readPortfolio(String path) {
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(path)) {
            Object parseObj = parser.parse(reader);
            return (JSONObject) parseObj;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
