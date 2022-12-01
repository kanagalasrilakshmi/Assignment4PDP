package view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.text.ParseException;

import controller.ControllerGUI;
import view.panels.costbasis.CostBasisPanel;
import view.panels.costbasis.CostBasisPanelImpl;
import view.panels.create.CreatePanel;
import view.panels.create.CreatePanelImpl;
import view.panels.dollarexisting.Dollarstrategyexist;
import view.panels.dollarexisting.DollarstrategyexistImpl;
import view.panels.dollarstarttofinish.Dollarstrategynew;
import view.panels.dollarstarttofinish.DollarstrategynewImpl;
import view.panels.mainpanel.MainPanel;
import view.panels.mainpanel.MainPanelImpl;
import view.panels.modify.ModifyPanel;
import view.panels.modify.ModifyPanelImpl;
import view.panels.quit.QuitPanel;
import view.panels.quit.QuitPanelImpl;
import view.panels.retrievepf.RetrievePanel;
import view.panels.retrievepf.RetrievePanelImpl;
import view.panels.userpath.UserPanel;
import view.panels.userpath.UserPanelImpl;
import view.panels.valuedate.ValuePanel;
import view.panels.valuedate.ValuePanelImpl;

/**
 * Class that implements interface methods of the gui view for creating flexible portfolio,
 * for modifying the portfolio, retrieving the portfolio, getting cost basis,
 * value of the portfolio, applying dollar cost strategies.
 */
public class GUIViewImpl extends JFrame implements GUIView {
  MainPanel mainPanelObj = new MainPanelImpl();
  UserPanel userPanelObj = new UserPanelImpl();
  CreatePanel createPanelObj = new CreatePanelImpl();
  ModifyPanel modifyPanelObj = new ModifyPanelImpl();
  ValuePanel valueDatePanelObj = new ValuePanelImpl();
  CostBasisPanel costBasisPanelObj = new CostBasisPanelImpl();
  RetrievePanel retrievePanelObj = new RetrievePanelImpl();
  Dollarstrategyexist dollarPanelExistingObj = new DollarstrategyexistImpl();
  Dollarstrategynew dollarPanelNewObj = new DollarstrategynewImpl();

  /**
   * Class for implementing GUIView interface.
   * implementing GUI based view for flexible portfolios,
   * and implement dollar cost averaging strategy.
   * Helps provide graphical interface for viewing, creating, modifying,
   * and applying strategies using gui based interface.
   */
  public GUIViewImpl() {
    super();
    finalPanel();
  }

  /**
   * extends JFrame for setting main panel as visible.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Set the status of the user panel based on the given input path by the user.
   *
   * @param setMessage is the message that needs to be set
   */
  public void setpathStore(String setMessage) {
    userPanelObj.returnPathStore().setText(setMessage);
  }

  /**
   * Take the user input of the path where the portfolio needs to be created.
   *
   * @return input given by the user
   */
  public String givenPath() {
    return JOptionPane.showInputDialog("Please enter path to store portfolios");
  }

  /**
   * While applying dollar strategy on existing portfolio,
   * the name of the strategy field is set to null,
   * if user gives invalid input or the strategy is set.
   *
   * @param message is the value to be set for the strategy field in existing dollar portfolio pane
   */
  public void setstrategynameexist(String message) {
    dollarPanelExistingObj.getStratergydollarexistname().setText(message);
  }

  /**
   * While applying dollar strategy on during start-to-finish dollar averaging strategy,
   * the name of the strategy field is set to null,
   * if user gives invalid input, or the dialog pane is closed the strategy is set.
   *
   * @param message that needs to be set for the strategy field in start to finish dialog pane
   */
  public void setstrategynamenewexist(String message) {
    dollarPanelNewObj.getStratergydollarnewname().setText(message);
  }

  /**
   * Set the status of the existing dollar strategy pane based on the user inputs.
   * Tells if portfolio strategy is applied, or the dialog pane is closed ,
   * or not or any invalid inputs is given.
   *
   * @param message set the status of the panel based on the user inputs given
   */
  public void setdollarexistpanestatus(String message) {
    dollarPanelExistingObj.getDollarexistpanestatus().setText(message);
  }

  /**
   * Set the portfolio name in the start to finish dollar strategy pane based on the user input.
   * If invalid input is given, or the dialog pane is closed or strategy is applied null is set.
   *
   * @param message is the value that needs to be set for the portfolio field
   */
  public void setpfnamedollarnew(String message) {
    dollarPanelNewObj.getDollarnewcreatepfname().setText(message);
  }

  /**
   * Set the money to be invested in dollars while creating start-to-finish portfolio,
   * based on the input given.if the user gives invalid values or the dialog pane is closed,
   * this field is set to null.
   *
   * @param message is the value that needs to be set for the value field
   */
  public void setdollarnewval(String message) {
    dollarPanelNewObj.getDollarnewval().setText(message);
  }

  /**
   * Set the start date for the start-to-finish portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the start date field
   */
  public void setstartdatenew(String message) {
    dollarPanelNewObj.getDollarnewstartdate().setText(message);
  }

  /**
   * Set the end date for the start-to-finish portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the end date field
   */
  public void setenddatenew(String message) {
    dollarPanelNewObj.getDollarnewenddate().setText(message);
  }

  /**
   * Set the number of days the transaction needs to recur in a given start and end date range.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the days field
   */
  public void setdollardays(String message) {
    dollarPanelNewObj.getDollarnewdays().setText(message);
  }

  /**
   * Set the commission value while applying start-to-finish dollar cost averaging.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the commission field
   */
  public void setdollarcommissionnew(String message) {
    dollarPanelNewObj.getdollarnewcommission().setText(message);
  }

  /**
   * Set the weights value field while applying start-to-finish dollar cost averaging.
   * If the user gives invalid format ,sum not equals to 100 or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the weights input field
   */
  public void setweightsnew(String message) {
    dollarPanelNewObj.getWeightsnew().setText(message);
  }

  /**
   * Set the stocks value field while applying start-to-finish dollar cost averaging.
   * If the user gives invalid format, or invalid tickr symbols or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the stocks input field
   */
  public void setstocksnew(String message) {
    dollarPanelNewObj.getStocksnew().setText(message);
  }

  /**
   * Set the stocks value field while applying dollar averaging on existing portfolio.
   * If the user gives invalid format, or invalid tickr symbols or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the stocks input field
   */
  public void setstocksexist(String message) {
    dollarPanelExistingObj.getStocksexist().setText(message);
  }

  /**
   * Set the weights value field while applying dollar averaging on existing portfolio.
   * If the user gives invalid format ,sum not equals to 100 or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the weights input field
   */
  public void setweightsexist(String message) {
    dollarPanelExistingObj.getWeightsexist().setText(message);
  }


  /**
   * Set the money to be invested in dollars,
   * while applying dollar cost averaging on the existing portfolio.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is values that needs to be set for the value field
   */
  public void setdollarexistval(String message) {
    dollarPanelExistingObj.getDollarexistval().setText(message);
  }

  /**
   * Set the status of the start-to-finish dollar strategy pane based on the user inputs.
   * Tells if portfolio strategy is applied, or the dialog pane is closed,
   * or not or any invalid inputs is given.
   *
   * @param message set the status of the panel based on the user inputs given
   */
  public void setdollarnewpanestatus(String message) {
    dollarPanelNewObj.getDollarnewpanestatus().setText(message);
  }

  /**
   * Set the status of the portfolio path while creating new flexible portfolio.
   * If invalid path or existing path or dialog pane is given then this field is set to null.
   *
   * @param message is value that needs to be set for the pfname field
   */
  public void setCreatePfValue(String message) {
    createPanelObj.getPfnamecreate().setText(message);
  }

  /**
   * Set the date of purchase of the stock while creating new flexible portfolio.
   * If invalid date format, future date is given or dialog pane is closed,
   * then this field is set to null.
   *
   * @param message is value that needs to be set for the date field
   */
  public void setdateofcreationValue(String message) {
    createPanelObj.getDateofcreation().setText(message);
  }

  /**
   * Set the number of stocks field while creating new flexible portfolio.
   * If invalid number of stocks, negative stocks or fractional stocks are given,
   * dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the num of stocks field
   */
  public void setnumstockscreateValue(String message) {
    createPanelObj.getNumstockscreate().setText(message);
  }

  /**
   * Set the tickr value for which company stocks needs to be purchased,
   * while creating a flexible portfolio.
   * If invalid tickr symbol or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the tickr symbol field
   */
  public void settickrcreateValue(String message) {
    createPanelObj.getTickrcreate().setText(message);
  }

  /**
   * Set the commission value while creating a flexible portfolio.
   * If negative stocks, or invalid value is given or dialog pane is closed,
   * then this field is set to null.
   *
   * @param message is value that needs to be set for the commission field
   */
  public void setcommissionfeescreateValue(String message) {
    createPanelObj.getCommissionfeescreate().setText(message);
  }

  /**
   * Set the portfolio path field while modifying the portfolio.
   * If a portfolio that does not exist is given or modify dialog pane is closed,
   * then it is set to null.
   *
   * @param message is value that needs to be set for the pf name field
   */
  public void setModifyPfValue(String message) {
    modifyPanelObj.getPfnamemodify().setText(message);
  }

  /**
   * Set the date of purchase or sell in the modify pane, if invalid date format,
   * future date or modify dialog pane,
   * then set to null.
   *
   * @param message is value that needs to be set for the date field
   */
  public void setdateofmodifynValue(String message) {
    modifyPanelObj.getDateofmodify().setText(message);
  }

  /**
   * Set the number of stocks field while modifying flexible portfolio.
   * If invalid number of stocks, negative stocks or fractional stocks are given,
   * dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the num of stocks field
   */
  public void setnumstocksmodifyValue(String message) {
    modifyPanelObj.getNumstocksmodify().setText(message);
  }

  /**
   * Set the tickr value for which company stocks needs to be purchased or sold,
   * while modifying a flexible portfolio.If invalid tickr symbol,
   * or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the tickr symbol field
   */
  public void settickrmodifyValue(String message) {
    modifyPanelObj.getTickrmodify().setText(message);
  }

  /**
   * Set the commission value while modifying a flexible portfolio.
   * If negative stocks, or invalid value is given or dialog pane is closed,
   * then this field is set to null.
   *
   * @param message is value that needs to be set for the commission field
   */
  public void setcommissionfeesmodifyValue(String message) {
    modifyPanelObj.getCommissionfessmodify().setText(message);
  }

  /**
   * Set the status of the create dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are invalid,
   * show which are wrong.
   * If all the correct inputs are given, then show that portfolio is created correctly.
   *
   * @param message status of while creating portfolio
   */
  public void setcreateDialogStatus(String message) {
    createPanelObj.getCreateDialogStatus().setText(message);
  }

  /**
   * Set the status of the modify dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are invalid,
   * show which are wrong, if all the correct inputs are given, then show that portfolio,
   * is modified successfully.
   *
   * @param message status of while modifying portfolio
   */
  public void setmodifyDialogStatus(String message) {
    modifyPanelObj.getModifyDialogStatus().setText(message);
  }

  /**
   * Set the status of the value dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty, if inputs given are,
   * invalid show which are wrong.
   * If all the correct inputs are given, then show the portfolio value is,
   * successfully retrieved and display.
   *
   * @param message status while querying value of a portfolio
   */
  public void setvalueDialogStatus(String message) {
    valueDatePanelObj.getValDialogStatus().setText(message);
  }

  /**
   * Set the status of the composition dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty,
   * if inputs given are invalid show which are wrong.
   *
   * @param message status while retrieving the composition of portfolio
   */
  public void setretrieveDialogStatus(String message) {
    retrievePanelObj.getRetrieveDialogStatus().setText(message);
  }

  /**
   * Set the status of the cost basis dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty,
   * if inputs given are invalid show which are wrong.
   *
   * @param message gives the cost basis of the given portfolio
   */
  public void setCostBasisDialogStatus(String message) {
    costBasisPanelObj.getCostBasisDialogStatus().setText(message);
  }

  /**
   * Get the list of all the portfolios to be modified in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setportfoliosListModify(String message) {
    modifyPanelObj.getPortfoliosListModify().setText(message);
  }

  /**
   * Get the list of all the portfolios to query the value in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setPortfoliosListVal(String message) {
    valueDatePanelObj.getPortfoliosListVal().setText(message);
  }

  /**
   * Get the list of all the portfolios to query the cost basis in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setPortfoliosListBasis(String message) {
    costBasisPanelObj.getPortfoliosListBasis().setText(message);
  }

  /**
   * Get the list of all the portfolios to query the composition in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setPortfoliosListRetrieve(String message) {
    retrievePanelObj.getPortfoliosListRetrieve().setText(message);
  }

  /**
   * Get the list of all the portfolios,
   * to apply dollar strategy on existing portfolio in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setportfolioslistdollarexist(String message) {
    dollarPanelExistingObj.getPortfolioslistdollarexist().setText(message);
  }

  /**
   * Set the portfolio name on existing dollar strategy pane based on the user input.
   * If invalid input is given, or the dialog pane is closed or strategy is applied null is set.
   *
   * @param message is the value that needs to be set for the portfolio field
   */
  public void setpfNameExistDollar(String message) {
    dollarPanelExistingObj.getDollarexistpfname().setText(null);
  }

  /**
   * Set the date for the existing dollar cost straegy portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the date field
   */
  public void setdollardateexist(String message) {
    dollarPanelExistingObj.getDollarexistdate().setText(message);
  }

  /**
   * Set the commission value on existing dollar cost averaging pane.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the commission field
   */
  public void setdollarexistcommisionval(String message) {
    dollarPanelExistingObj.getDollarexistcommision().setText(message);
  }

  /**
   * Set the portfolio name to be retrieved while getting composition of a portfolio.
   *
   * @param message is the value that needs to be set for the pf name field in retrieve dialog pane
   */
  public void setpfnameretrieve(String message) {
    retrievePanelObj.getPfnameretrieve().setText(message);
  }

  /**
   * Set the composition of the portfolio given.
   *
   * @param message is the composition of a particular portfolio
   */
  public void setPortfoliosListComposition(String message) {
    retrievePanelObj.getPortfolioComposition().setText(message);
  }

  /**
   * Set the name of the portfolio name for which value needs to be calculated on a specific date.
   * If name given is not present in the existing portfolios then it sets to null.
   *
   * @param message value that needs to be set for pf name field
   */
  public void setpfnameVal(String message) {
    valueDatePanelObj.getPfnamevalue().setText(message);
  }

  /**
   * Set the date value in the dialog pane that computes date.
   * Set to null if invalid date is entered or future date is entered by the user.
   *
   * @param message value that needs to be set for date field
   */
  public void setdateVal(String message) {
    valueDatePanelObj.getDatevalue().setText(message);
  }

  /**
   * Set the name of the portfolio name for which cost basis needs,
   * to be calculated till a specific date.
   * If name given is not present in the existing portfolios then it sets to null.
   *
   * @param message value that needs to be set for pf name field
   */
  public void setpfNameCostBasis(String message) {
    costBasisPanelObj.getPfNameCostBasis().setText(message);
  }

  /**
   * Set date of the portfolio in the cost basis dialog pane.
   * if the date is in future or in invalid format then set to null.
   *
   * @param message value that needs to be set for date field
   */
  public void setDate(String message) {
    costBasisPanelObj.getDateCostBasis().setText(message);
  }

  /**
   * Set the status of the cost basis label when get cost basis button is clicked.
   * If button is clicked before setting user path then the label prompts,
   * to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for cost basis label based on users input
   */
  public void setLabelCostBasisStatus(String message) {
    costBasisPanelObj.returncostBasisStatus().setText(message);
  }

  /**
   * Set the status of the retrieve label when get retrieve composition button is clicked.
   * If button is clicked before setting user path then, label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting composition label based on users input
   */
  public void setRetrievePanelStatus(String message) {
    retrievePanelObj.returnretrievePanelStatus().setText(message);
  }

  /**
   * When get cost basis button is clicked in the main panel,
   * this opens the dialog pane that would let user,
   * to enter required entries to compute cost basis of a portfolio.
   */
  public void displayCostBasis() {
    costBasisPanelObj.displayCostBasis();
  }

  /**
   * Displays the create portfolio dialog pane that lets users create a flexible portfolio,
   * when create portfolio button is clicked.
   */
  public void displayCreatePf() {
    createPanelObj.displayCreatePf();
  }

  /**
   * Displays modify portfolio dialog pane,
   * lets users modify a flexible portfolio by purchasing and selling,
   * when modify portfolio button is clicked.
   */
  public void displayModifyPf() {
    modifyPanelObj.displayModifyPf();
  }

  /**
   * Display the value portfolio dialog pane,
   * lets users get the value of a flexible portfolio on a specific date.
   */
  public void displayValuepf() {
    valueDatePanelObj.displayValuepf();
  }

  /**
   * Display the composition of the portfolio pane,
   * gives the portfolio composition on a given date for a portfolio.
   */
  public void displayRetrievepf() {
    retrievePanelObj.displayRetrievepf();
  }

  /**
   * Display the pane that lets users apply dollar strategy on existing flexible portfolios.
   */
  public void displayDollarExistingpf() {
    dollarPanelExistingObj.displayDollarExistingpf();
  }

  /**
   * Display the pane that lets users apply start-to-finish dollar averaging strategy.
   */
  public void displayDollarNewpf() {
    dollarPanelNewObj.displayDollarNewpf();
  }

  /**
   * Set the status of the creation label when get create portfolio button is clicked.
   * If button is clicked before setting user path, the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting creation label based on users input
   */
  public void setCreateLabelStatus(String message) {
    createPanelObj.getCreateStatus().setText(message);
  }

  /**
   * Set the status of the modify label when get modify portfolio button is clicked.
   * If button is clicked before setting user path, the label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting modify label based on users input
   */
  public void setModifyLabelStatus(String message) {
    modifyPanelObj.getModifyStatus().setText(message);
  }

  /**
   * Set the status of the value label when get value of portfolio button is clicked.
   * If button is clicked before setting user path then, label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting value label based on users input
   */
  public void setValueLabelStatus(String message) {
    valueDatePanelObj.returnValueStatus().setText(message);
  }

  /**
   * Set the status of the value label when exist dollar cost strategy button is clicked.
   * If button is clicked before setting user path then, label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for applying dollar strategy on existing portfolio
   */
  public void setdollarExistingStatus(String message) {
    dollarPanelExistingObj.getDollarExistingStatus().setText(message);
  }

  /**
   * Set the status of the value label start-to-finish dollar portfolio button is clicked.
   * If button is clicked before setting user path then, label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for applying  start-to-finish dollar strategy label
   */
  public void setdollarNewStatus(String message) {
    dollarPanelNewObj.getDollarNewStatus().setText(message);
  }

  private void finalPanel() {
    setTitle("Stocks Program");
    mainPanelObj.mainPanel();
    userPanelObj.getUserPanel();
    createPanelObj.createPanel();
    createPanelObj.createPanel();
    modifyPanelObj.modifyPanel();
    valueDatePanelObj.valDatePanel();
    costBasisPanelObj.costBasisPanel();
    retrievePanelObj.retrievePfPanel();
    dollarPanelExistingObj.dollarCostExisting();
    dollarPanelNewObj.dollarCostNew();
    QuitPanel quitPanelObj = new QuitPanelImpl();
    quitPanelObj.getQuitPanel();
    mainPanelObj.getMainJPanel().add(userPanelObj.returnUserPanel());
    mainPanelObj.getMainJPanel().add(createPanelObj.getcreatePanel());
    mainPanelObj.getMainJPanel().add(modifyPanelObj.getModifyPanel());
    mainPanelObj.getMainJPanel().add(costBasisPanelObj.getcostBasisPanel());
    mainPanelObj.getMainJPanel().add(valueDatePanelObj.getValueDatePanel());
    mainPanelObj.getMainJPanel().add(retrievePanelObj.getRetrievePanel());
    mainPanelObj.getMainJPanel().add(dollarPanelExistingObj.getDollarCostExisting());
    mainPanelObj.getMainJPanel().add(dollarPanelNewObj.getdollarCostNew());
    mainPanelObj.getMainJPanel().add(quitPanelObj.quitPanelfinal());
    add(mainPanelObj.getMainJPanel());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 1000);
    setLocationRelativeTo(null);
  }

  /**
   * Add features for the buttons in main panel for them to carry out portfolio giving user path,
   * creation, modification,computing value basis, computing cost basis,
   * applying stratergy on existing portfolios,
   * start-to-finish dollar cost averaging and quit the program.
   *
   * @param features is the controller type object that consists of the functionalities to be done
   */
  public void addFeatures(ControllerGUI features) {
    userPanelObj.returninputButton().addActionListener(evt -> features.displaysetrootpane());
    createPanelObj.getCreatePfButton().addActionListener(evt ->
            features.displayDialogPane("create"));
    createPanelObj.getAddCreate().addActionListener(evt -> {
      try {
        features.addOperation(createPanelObj.getPfnamecreate().getText(),
                createPanelObj.getTickrcreate().getText().toUpperCase(),
                createPanelObj.getNumstockscreate().getText(),
                createPanelObj.getDateofcreation().getText(),
                createPanelObj.getCommissionfeescreate().getText());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });
    createPanelObj.getSaveCreate().addActionListener(evt -> features.saveOperation(
            createPanelObj.getPfnamecreate().getText()));
    modifyPanelObj.returngetModifyButton().addActionListener(evt ->
            features.displayDialogPane("modify"));
    modifyPanelObj.getPurchasemodify().addActionListener(evt -> {
      try {
        features.modifyValidate(modifyPanelObj.getPfnamemodify().getText(),
                modifyPanelObj.getTickrmodify().getText().toUpperCase(),
                modifyPanelObj.getNumstocksmodify().getText(),
                modifyPanelObj.getDateofmodify().getText(),
                modifyPanelObj.getCommissionfessmodify().getText(),
                "purchase");
      } catch (FileNotFoundException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    modifyPanelObj.getSellmodify().addActionListener(evt -> {
      try {
        features.modifyValidate(modifyPanelObj.getPfnamemodify().getText(),
                modifyPanelObj.getTickrmodify().getText().toUpperCase(),
                modifyPanelObj.getNumstocksmodify().getText(),
                modifyPanelObj.getDateofmodify().getText(),
                modifyPanelObj.getCommissionfessmodify().getText(), "sell");
      } catch (FileNotFoundException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    valueDatePanelObj.returnValueButton().addActionListener(evt ->
            features.displayDialogPane("getDateVal"));
    valueDatePanelObj.getComputeval().addActionListener(evt -> {
      try {
        features.validateDateVal(valueDatePanelObj.getPfnamevalue().getText(),
                valueDatePanelObj.getDatevalue().getText());
      } catch (FileNotFoundException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    costBasisPanelObj.returncostBasisButton().addActionListener(evt ->
            features.displayDialogPane("costBasis"));
    costBasisPanelObj.getCostBasisButton().addActionListener(evt -> {
      try {
        features.validateCostBasis(costBasisPanelObj.getPfNameCostBasis().getText(),
                costBasisPanelObj.getDateCostBasis().getText());
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    });
    retrievePanelObj.returnretrievePf().addActionListener(evt ->
            features.displayDialogPane("composition"));
    retrievePanelObj.getComputecomposition().addActionListener(evt ->
            features.getCompositionpf(retrievePanelObj.getPfnameretrieve().getText()));
    dollarPanelExistingObj.returndollarCostExisting().addActionListener(evt ->
            features.displayDialogPane("dollarexist"));
    dollarPanelNewObj.returndollarCostNew().addActionListener(evt ->
            features.displayDialogPane("dollarnew"));
    dollarPanelExistingObj.getDollarexistcreate().addActionListener(evt ->
            features.validateExistingDollar(
                    dollarPanelExistingObj.getStratergydollarexistname().getText(),
                    dollarPanelExistingObj.getDollarexistpfname().getText(),
                    dollarPanelExistingObj.getStocksexist().getText().toUpperCase(),
                    dollarPanelExistingObj.getWeightsexist().getText(),
                    dollarPanelExistingObj.getDollarexistval().getText(),
                    dollarPanelExistingObj.getDollarexistdate().getText(),
                    dollarPanelExistingObj.getDollarexistcommision().getText()));
    dollarPanelNewObj.getDollarnewcreate().addActionListener(evt -> {
      try {
        features.validateNewDollar(
                dollarPanelNewObj.getStratergydollarnewname().getText(),
                dollarPanelNewObj.getDollarnewcreatepfname().getText(),
                dollarPanelNewObj.getStocksnew().getText().toUpperCase(),
                dollarPanelNewObj.getWeightsnew().getText(),
                dollarPanelNewObj.getDollarnewval().getText(),
                dollarPanelNewObj.getDollarnewdays().getText(),
                dollarPanelNewObj.getDollarnewstartdate().getText(),
                dollarPanelNewObj.getDollarnewenddate().getText(),
                dollarPanelNewObj.getdollarnewcommission().getText());
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
