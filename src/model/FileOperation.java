package model;

import org.json.simple.JSONObject;

/**
The interface for file operations that takes care of all the save and retrieve operations
 throughout the program.
 */
public interface FileOperation {
    /**
     * Save a portfolio.
     *
     * @param path portfolio path where json needs to be saved
     * @param data portfolio json object
     */
    void savePortfolio(String path, JSONObject data);

    /**
     * Read the Portfolio for the given path for the portfolio.
     *
     * @param path portfolio absolute path
     * @return a json object that consists of all the entries in the input portfolio path
     */
    JSONObject readPortfolio(String path);
}
