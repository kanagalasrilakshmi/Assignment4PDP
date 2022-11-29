package controller;

import java.io.FileNotFoundException;
import java.text.ParseException;


/**
 * Interface for the controller that implements flexible stocks program, dollar cost averaging using GUI based view.
 */
public interface ControllerGUI extends Controller {
    /**
     * Set the root directory based on the input given by the user.
     */
    void setDirectory();

    /**
     * Displays the dialog pane for the creation of portfolio, modification of the portfolio ,cost basis till a date,
     * getting value of the portfolio on a specific date, retrieving the composition of the portfolio,
     * dollar cost averaging on the existing portfolio, doing dollar cost averaging from start-to-finish and,
     * Quit the program. Every functionality is identified by label given to it.
     *
     * @param label can be the label associated with the functionality that needs to be performed
     */
    void displayDialogPane(String label);


    /**
     * Carries out operation for adding stocks while creating the portfolio.
     *
     * @param pfNameCreate     name of the portfolio to which stocks are to be added
     * @param tickrCreate      tickr symbol that needs to be added
     * @param numStocksCreate  number of stocks to be added to the portfolio
     * @param dateCreate       is the date on which the adding of purchase of stock needs to be done
     * @param commissionCreate is the commission fees for carrying out the transaction
     * @throws FileNotFoundException if file is not found while validating the tickr symbols to the portfolio
     */
    void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
                      String dateCreate, String commissionCreate) throws FileNotFoundException;


    /**
     * Checks the given portfolio name and saves the given entry into the file.
     *
     * @param pfNameCreate name of the portfolio that needs to be saved
     */
    void saveOperation(String pfNameCreate);


    /**
     * Helps to validate the inmput fields and modify the existing portfolio by allowing the user to purchase,
     * and sell stocks.
     *
     * @param pfNameModify     is the name of the portfolio that needs to be modified
     * @param tickrModify      is the name of the tickr symbol that needs to be modified
     * @param numStocksModify  is the number of stocks that needs to be purchase and sold
     * @param dateModify       is the date on which the purchase or sell needs to occur
     * @param commissionModify is commission fees while carrying out purchase or sell
     * @param statuslabel      helps to specify that modification needs to occur and checks the inputs fields
     * @throws FileNotFoundException if portfolio is not found while making modification
     * @throws ParseException        if an exception occurs while parsing the existing portfolio
     */
    void modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                        String dateModify, String commissionModify, String statuslabel)
            throws FileNotFoundException, ParseException;


    /**
     * Helps to validate the input fields and get the value of the portfolio on that particular date.
     *
     * @param pfNamedate is the name of the portfolio for which value needs to be computed
     * @param dateValue  is the date value on which value needs to be computed
     * @throws FileNotFoundException when pf name to be searched is not found
     * @throws ParseException        an error occurs when parsing fails while reading the input file
     */
    void validateDateVal(String pfNamedate, String dateValue)
            throws FileNotFoundException, ParseException;


    /**
     * Helps to validate the input fields and get the cost basis of the portfolio on that particular date.
     *
     * @param pfNameBasis is the name of the portfolio for which cost basis needs to be computed
     * @param dateBasis   is the date value on which cost basis needs to be computed
     * @throws ParseException an error occurs when parsing fails while reading the input file
     */
    void validateCostBasis(String pfNameBasis, String dateBasis) throws ParseException;


    /**
     * Get the composition of the given portfolio name.
     *
     * @param pfNameComposition is the name of the portfolio for which composition needs to be computed
     */
    void getCompositionpf(String pfNameComposition);


    /**
     * This function helps to implement dollar averaging strategy on existing portfolio.
     *
     * @param stratergydollarexistname is the name of the strategy given by the user
     * @param dollarexistpfname        ame of the portfolio over which dollar averaging strategy needs to be performed
     * @param stocksexist              is the list of stocks string given by the user, seperated by delimeter ','
     * @param weightsexist             is the list of weights string given by the user, seperated by delimeter ','
     * @param dollarexistval           is the money to be invested in the portfolio
     * @param dollarexistdate          is date oon which the investment needs to occur
     * @param dollarexistcommision     is the commission fees for the transaction to occur
     */
    void validateExistingDollar(String stratergydollarexistname, String dollarexistpfname,
                                String stocksexist, String weightsexist, String dollarexistval,
                                String dollarexistdate, String dollarexistcommision);

    /**
     * This function helps to implement start-to-finish dollar averaging strategy.
     *
     * @param stratergydollarnewname is the name of the strategy given by the user
     * @param dollarnewcreatepfname  name of the portfolio to be created
     * @param stocksnew              is the list of stocks string given by the user, seperated by delimeter ','
     * @param weightsnew             is the list of weights string given by the user, seperated by delimeter ','
     * @param dollarnewval           is the money to be invested in the portfolio
     * @param dollarnewdays          is the number of days for investment to recur
     * @param dollarnewstartdate     is start date of the investment to occur
     * @param dollarnewenddate       is the end date of the investment to occur
     * @param dollarnewcommission    is the commission fees for the transaction to occur
     * @throws ParseException when there is an error parsing the portfolio and strategy look up json
     */
    void validateNewDollar(String stratergydollarnewname, String dollarnewcreatepfname,
                           String stocksnew, String weightsnew, String dollarnewval,
                           String dollarnewdays, String dollarnewstartdate, String dollarnewenddate,
                           String dollarnewcommission) throws ParseException;

    /**
     * Displays the pane that lets the user set the root path for storing the portfolios that are to be created,
     * modified or perform dollar averaging strategy.
     */
    void displaysetrootpane();
}
