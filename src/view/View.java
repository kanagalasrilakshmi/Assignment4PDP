package view;

import org.json.simple.JSONObject;

/**
 * View Interface that consists of all the methods to be implemented by the view.
 * It is used for viewing flexible and rigid portfolios.
 */
public interface View {

    /**
     * Shows the options that can be used by the user to run the stocks program.
     */
    void showOptions();

    /**
     * Prints the given input string when this function is called.
     *
     * @param s to be printed
     */
    void showString(String s);

    /**
     * Displays the list of .txt file present in the given input directory argument.
     *
     * @param rootDir is the directory where .txt files needs to be listed down
     */
    void listTXTFiles(String rootDir);

    /**
     * If an argument other than C,D,V or Q is passed this function is called to display error.
     */
    void showOptionError();

    /**
     * List all the json files.
     *
     * @param rootDir is the path from which json files need to be searched
     */
    void listJSONFiles(String rootDir);

    /**
     * View the composition of the entire portfolio.
     *
     * @param portfolio is the json object of a tickr symbol that consists of values to be displayed.
     */
    void viewFlexibleComposition(JSONObject portfolio);

    /**
     * Shows the options that can be used by the user to run the stocks program.
     */
    void showFlexibleViewOptions();

    /**
     * Shows the options that can be used by the controller main to run the stocks program.
     */
    void showMainOptions();

    /**
     * Shows the options that can be used in flexible portfolios.
     */
    void showFlexibleOptions();
}

