package view;

import controller.ControllerGUI;

/**
 * Interface that is used for implementing GUI based view methods.
 * It is used for viewing graphical based interface stocks program.
 */
public interface GUIView {

  /**
   * extends JFrame for setting main panel as visible.
   */
  void makeVisible();

  /**
   * Take the user input of the path where the portfolio needs to be created.
   *
   * @return input given by the user
   */
  String givenPath();

  /**
   * Set the status of the user panel based on the given input path by the user.
   *
   * @param setMessage is the message that needs to be set
   */
  void setpathStore(String setMessage);

  /**
   * When get cost basis button is clicked in the main panel, this opens the dialog pane that would let user,
   * to enter required entries to compute cost basis of a portfolio.
   */
  void displayCostBasis();

  /**
   * Set the status of the cost basis label when get cost basis button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for cost basis label based on users input
   */
  void setLabelCostBasisStatus(String message);

  /**
   * Displays the create portfolio dialog pane that lets users create a flexible portfolio,
   * when create portfolio button is clicked.
   */
  void displayCreatePf();

  /**
   * Displays modify portfolio dialog pane that lets users modify a flexible portfolio by purchasing and selling,
   * when modify portfolio button is clicked.
   */
  void displayModifyPf();

  /**
   * Display the value portfolio dialog pane that lets users get the value of a flexible portfolio on a specific date.
   */
  void displayValuepf();

  /**
   * Set the status of the creation label when get create portfolio button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting creation label based on users input
   */
  void setCreateLabelStatus(String message);

  /**
   * Set the status of the create dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are invalid show which are wrong.
   * If all the correct inputs are given, then show that portfolio is created correctly.
   *
   * @param message status of while creating portfolio
   */
  void setcreateDialogStatus(String message);

  /**
   * Set the status of the modify label when get modify portfolio button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting modify label based on users input
   */
  void setModifyLabelStatus(String message);

  /**
   * Set the status of the modify dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are invalid show which are wrong.
   * If all the correct inputs are given, then show that portfolio is modified successfully.
   *
   * @param message status of while modifying portfolio
   */
  void setmodifyDialogStatus(String message);

  /**
   * Set the status of the value label when get value of portfolio button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting value label based on users input
   */
  void setValueLabelStatus(String message);

  /**
   * Set the status of the value label when get value of portfolio button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting value label based on users input
   */
  void setvalueDialogStatus(String message);

  /**
   * Set the status of the cost basis dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are invalid show which are wrong.
   *
   * @param message gives the cost basis of the given portfolio
   */
  void setCostBasisDialogStatus(String message);

  /**
   * Set the status of the portfolio path while creating new flexible portfolio.
   * If invalid path or existing path or dialog pane is given then this field is set to null.
   *
   * @param message is value that needs to be set for the pfname field
   */
  void setCreatePfValue(String message);

  /**
   * Set the tickr value for which company stocks needs to be purchased or sold,
   * while modifying a flexible portfolio.If invalid tickr symbol,
   * or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the tickr symbol field
   */
  void settickrmodifyValue(String message);

  /**
   * Set the tickr value for which company stocks needs to be purchased while creating a flexible portfolio.
   * If invalid tickr symbol or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the tickr symbol field
   */
  void settickrcreateValue(String message);

  /**
   * Set the number of stocks field while creating new flexible portfolio.
   * If invalid number of stocks, negative stocks or fractional stocks are given,
   * dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the num of stocks field
   */
  void setnumstockscreateValue(String message);

  /**
   * Set the date of purchase of the stock while creating new flexible portfolio.
   * If invalid date format, future date is given or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the date field
   */
  void setdateofcreationValue(String message);

  /**
   * Set the commission value while creating a flexible portfolio.
   * If negative stocks, or invalid value is given or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the commission field
   */
  void setcommissionfeescreateValue(String message);

  /**
   * Set the portfolio path field while modifying the portfolio.
   * If a portfolio that does not exist is given or modify dialog pane is closed then it is set to null.
   *
   * @param message is value that needs to be set for the pf name field
   */
  void setModifyPfValue(String message);

  /**
   * Set the number of stocks field while modifying flexible portfolio.
   * If invalid number of stocks, negative stocks or fractional stocks are given,
   * dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the num of stocks field
   */
  void setnumstocksmodifyValue(String message);

  /**
   * Set the date of purchase or sell in the modify pane, if invalid date format, future date or modify dialog pane,
   * then set to null.
   *
   * @param message is value that needs to be set for the date field
   */
  void setdateofmodifynValue(String message);

  /**
   * Set the commission value while modifying a flexible portfolio.
   * If negative stocks, or invalid value is given or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the commission field
   */
  void setcommissionfeesmodifyValue(String message);

  /**
   * Set the name of the portfolio name for which value needs to be calculated on a specific date.
   * If name given is not present in the existing portfolios then it sets to null.
   *
   * @param message value that needs to be set for pf name field
   */
  void setpfnameVal(String message);

  /**
   * Set the date value in the dialog pane that computes date.
   * Set to null if invalid date is entered or future date is entered by the user.
   *
   * @param message value that needs to be set for date field
   */
  void setdateVal(String message);

  /**
   * Set the name of the portfolio name for which cost basis needs to be calculated till a specific date.
   * If name given is not present in the existing portfolios then it sets to null.
   *
   * @param message value that needs to be set for pf name field
   */
  void setpfNameCostBasis(String message);

  /**
   * Set date of the portfolio in the cost basis dialog pane.
   * if the date is in future or in invalid format then set to null.
   *
   * @param message value that needs to be set for date field
   */
  void setDate(String message);

  /**
   * Get the list of all the portfolios to be modified in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  void setportfoliosListModify(String message);

  /**
   * Get the list of all the portfolios to query the value in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  void setPortfoliosListVal(String message);

  /**
   * Get the list of all the portfolios to query the cost basis in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  void setPortfoliosListBasis(String message);

  /**
   * Add features for the buttons in main panel for them to carry out portfolio giving user path,
   * creation, modification,computing value basis, computing cost basis, applying stratergy on existing portfolios,
   * start-to-finish dollar cost averaging and quit the program.
   *
   * @param features is the controller type object that consists of the functionalities to be performed on button click
   */
  void addFeatures(ControllerGUI features);

  /**
   * Set the status of the retrieve label when get retrieve composition button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting composition label based on users input
   */
  void setRetrievePanelStatus(String message);

  /**
   * Get the list of all the portfolios to query the composition in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  void setPortfoliosListRetrieve(String message);

  /**
   * Display the composition of the portfolio pane that gives the portfolio composition on a given date for a portfolio.
   */
  void displayRetrievepf();

  /**
   * Set the status of the composition dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are invalid show which are wrong.
   *
   * @param message status while retrieving the composition of portfolio
   */
  void setretrieveDialogStatus(String message);

  /**
   * Set the portfolio name to be retrieved while getting composition of a portfolio.
   *
   * @param message is the value that needs to be set for the pf name field in retrieve dialog pane
   */
  void setpfnameretrieve(String message);

  /**
   * Set the composition of the portfolio given.
   *
   * @param message is the composition of a particular portfolio
   */
  void setPortfoliosListComposition(String message);

  /**
   * Display the pane that lets users apply dollar strategy on existing flexible portfolios.
   */
  void displayDollarExistingpf();

  /**
   * Display the pane that lets users apply start-to-finish dollar averaging strategy.
   */
  void displayDollarNewpf();

  /**
   * Set the status of the value label when exist dollar cost strategy button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for applying dollar strategy for existing portfolio based on users input
   */
  void setdollarExistingStatus(String message);

  /**
   * Set the status of the value label when exist dollar cost strategy button is clicked.
   * If button is clicked before setting user path then the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for applying dollar strategy for existing portfolio based on users input
   */
  void setdollarNewStatus(String message);

  /**
   * Set the status of the existing dollar strategy pane based on the user inputs.
   * Tells if portfolio strategy is applied, or the dialog pane is closed or not or any invalid inputs is given.
   *
   * @param message set the status of the panel based on the user inputs given
   */
  void setdollarexistpanestatus(String message);

  /**
   * Set the portfolio name on existing dollar strategy pane based on the user input.
   * If invalid input is given, or the dialog pane is closed or strategy is applied null is set.
   *
   * @param message is the value that needs to be set for the portfolio field
   */
  void setpfNameExistDollar(String message);

  /**
   * Set the date for the existing dollar cost straegy portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the date field
   */
  void setdollardateexist(String message);

  /**
   * Set the commission value on existing dollar cost averaging pane.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the commission field
   */
  void setdollarexistcommisionval(String message);

  /**
   * Set the stocks value field while applying dollar averaging on existing portfolio.
   * If the user gives invalid format, or invalid tickr symbols or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the stocks input field
   */
  void setstocksexist(String message);

  /**
   * Set the weights value field while applying dollar averaging on existing portfolio.
   * If the user gives invalid format ,sum not equals to 100 or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the weights input field
   */
  void setweightsexist(String message);

  /**
   * Set the money to be invested in dollars while applying dollar cost averaging on the existing portfolio.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is values that needs to be set for the value field
   */
  void setdollarexistval(String message);

  /**
   * Set the status of the start-to-finish dollar strategy pane based on the user inputs.
   * Tells if portfolio strategy is applied, or the dialog pane is closed or not or any invalid inputs is given.
   *
   * @param message set the status of the panel based on the user inputs given
   */
  void setdollarnewpanestatus(String message);

  /**
   * Get the list of all the portfolios to apply dollar strategy on existing portfolio in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  void setportfolioslistdollarexist(String message);


  /**
   * Set the portfolio name in the start to finish dollar strategy pane based on the user input.
   * If invalid input is given, or the dialog pane is closed or strategy is applied null is set.
   *
   * @param message is the value that needs to be set for the portfolio field
   */
  void setpfnamedollarnew(String message);

  /**
   * Set the stocks value field while applying start-to-finish dollar cost averaging.
   * If the user gives invalid format, or invalid tickr symbols or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the stocks input field
   */
  void setstocksnew(String message);

  /**
   * Set the weights value field while applying start-to-finish dollar cost averaging.
   * If the user gives invalid format ,sum not equals to 100 or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the weights input field
   */
  void setweightsnew(String message);

  /**
   * Set the start date for the start-to-finish portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the start date field
   */
  void setdollarnewval(String message);

  void setstartdatenew(String message);

  /**
   * Set the end date for the start-to-finish portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the end date field
   */
  void setenddatenew(String message);

  /**
   * Set the number of days the transaction needs to recur in a given start and end date range.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the days field
   */
  void setdollardays(String message);

  /**
   * Sets the value for the commission value for the commission field in start to finish dialog pane
   *
   * @param message is the value
   */
  void setdollarcommissionnew(String message);

  /**
   * Sets the value for the strategy name while adding dollar strategy on existing portfolio.
   *
   * @param message is the value that needs to be set for the strategy field while adding strategy rtfolio pane
   */
  void setstrategynameexist(String message);

  /**
   * Sets the value for the strategy name while creating start to finish portfolio.
   *
   * @param message that needs to be set for the strategy field in start to finish dialog pane
   */
  void setstrategynamenewexist(String message);

}
