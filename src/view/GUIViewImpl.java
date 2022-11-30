package view;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.FileNotFoundException;
import java.text.ParseException;
import javax.swing.JDialog;
import javax.swing.JTextField;
import controller.ControllerGUI;
import view.panels.*;

/**
 * Class that implements interface methods of the gui view for creating flexible portfolio,
 * for modifying the portfolio, retrieving the portfolio, getting cost basis,
 * value of the portfolio, applying dollar cost strategies.
 */
public class GUIViewImpl extends JFrame implements GUIView {
  UserPanel userPanelObj = new UserPanelImpl();
  CreatePanel createPanelObj = new CreatePanelImpl();
  ModifyPanel modifyPanelObj = new ModifyPanelImpl();
  ValuePanel valueDatePanelObj = new ValuePanelImpl();

  private JButton costBasisButton;
  private JPanel mainJPanel;
  private JPanel dollarPanelExisting;
  private JPanel dollarPanelNew;
  private JPanel costBasisPanel;
  private JLabel retrievePanelStatus;
  private JLabel dollarExistingStatus;
  private JLabel dollarNewStatus;
  private JPanel retrievePanel;
  private JButton retrievePf = new JButton("Retrieve Portfolio");
  private JButton getCostBasis;
  private JButton dollarCostExisting;
  private JButton dollarCostNew;
  private JLabel costBasisStatus = new JLabel();
  private JTextField pfName;
  private JTextField date;

  // fields for create.
  private JTextField pfnamecreate;
  private JTextField dateofcreation;
  private JTextField tickrcreate;
  private JTextField numstockscreate;
  private JTextField commissionfeescreate;
  private final JTextArea portfoliosListModify = new JTextArea();
  private final JTextArea portfoliosListVal = new JTextArea();
  private final JTextArea portfoliosListBasis = new JTextArea();
  private final JTextArea portfoliosListRetrieve = new JTextArea();
  private final JTextArea portfolioComposition = new JTextArea();
  private final JButton add = new JButton("Add");
  private final JButton save = new JButton("Save");

  // fields for modify.
  private JTextField pfnamemodify;
  private JTextField dateofmodify;
  private JTextField tickrmodify;
  private JTextField numstocksmodify;
  private JTextField commissionfessmodify;
  private final JButton purchase = new JButton("Purchase");
  private final JButton sell = new JButton("Sell");

  // fields for value.
  private JTextField pfnamevalue;
  private JTextField datevalue;
  private JTextField pfnameretrieve;
  private final JButton computeval = new JButton("Compute Value of Portfolio");
  private final JButton computecomposition = new JButton("Get Portfolio Composition");

  private final JLabel createDialogStatus = new JLabel();
  private final JLabel modifyDialogStatus = new JLabel();
  private final JLabel valDialogStatus = new JLabel();
  private final JLabel costBasisDialogStatus = new JLabel();
  private final JLabel retrieveDialogStatus = new JLabel();

  private JTextField stratergydollarnewname;
  private JTextField dollarnewcreatepfname;
  private JTextField stocksnew;
  private JTextField weightsnew;
  private JTextField dollarnewval;
  private JTextField dollarnewdays;
  private JTextField dollarnewstartdate;
  private JTextField dollarnewenddate;
  private JTextField dollarnewcommission;
  private final JButton dollarnewcreate = new JButton("Create");
  private final JLabel dollarnewpanestatus = new JLabel();

  private JTextField stratergydollarexistname;
  private final JTextArea portfolioslistdollarexist = new JTextArea();
  private JTextField dollarexistpfname;
  private JTextField stocksexist;
  private JTextField weightsexist;
  private JTextField dollarexistval;
  private JTextField dollarexistdate;
  private JTextField dollarexistcommision;
  private final JButton dollarexistcreate = new JButton("Create");
  private final JLabel dollarexistpanestatus = new JLabel();

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
    this.stratergydollarexistname.setText(message);
  }

  /**
   * While applying dollar strategy on during start-to-finish dollar averaging strategy,
   * the name of the strategy field is set to null,
   * if user gives invalid input, or the dialog pane is closed the strategy is set.
   *
   * @param message that needs to be set for the strategy field in start to finish dialog pane
   */
  public void setstrategynamenewexist(String message) {
    this.stratergydollarnewname.setText(message);
  }

  /**
   * Set the status of the existing dollar strategy pane based on the user inputs.
   * Tells if portfolio strategy is applied, or the dialog pane is closed ,
   * or not or any invalid inputs is given.
   *
   * @param message set the status of the panel based on the user inputs given
   */
  public void setdollarexistpanestatus(String message) {
    this.dollarexistpanestatus.setText(message);
  }

  /**
   * Set the portfolio name in the start to finish dollar strategy pane based on the user input.
   * If invalid input is given, or the dialog pane is closed or strategy is applied null is set.
   *
   * @param message is the value that needs to be set for the portfolio field
   */
  public void setpfnamedollarnew(String message) {
    this.dollarnewcreatepfname.setText(message);
  }

  /**
   * Set the money to be invested in dollars while creating start-to-finish portfolio,
   * based on the input given.if the user gives invalid values or the dialog pane is closed,
   * this field is set to null.
   *
   * @param message is the value that needs to be set for the value field
   */
  public void setdollarnewval(String message) {
    this.dollarnewval.setText(message);
  }

  /**
   * Set the start date for the start-to-finish portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the start date field
   */
  public void setstartdatenew(String message) {
    this.dollarnewstartdate.setText(message);
  }

  /**
   * Set the end date for the start-to-finish portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the end date field
   */
  public void setenddatenew(String message) {
    this.dollarnewenddate.setText(message);
  }

  /**
   * Set the number of days the transaction needs to recur in a given start and end date range.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the days field
   */
  public void setdollardays(String message) {
    this.dollarnewdays.setText(message);
  }

  /**
   * Set the commission value while applying start-to-finish dollar cost averaging.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the commission field
   */
  public void setdollarcommissionnew(String message) {
    this.dollarnewcommission.setText(message);
  }

  /**
   * Set the weights value field while applying start-to-finish dollar cost averaging.
   * If the user gives invalid format ,sum not equals to 100 or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the weights input field
   */
  public void setweightsnew(String message) {
    this.weightsnew.setText(message);
  }

  /**
   * Set the stocks value field while applying start-to-finish dollar cost averaging.
   * If the user gives invalid format, or invalid tickr symbols or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the stocks input field
   */
  public void setstocksnew(String message) {
    this.stocksnew.setText(message);
  }

  /**
   * Set the stocks value field while applying dollar averaging on existing portfolio.
   * If the user gives invalid format, or invalid tickr symbols or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the stocks input field
   */
  public void setstocksexist(String message) {
    this.stocksexist.setText(message);
  }

  /**
   * Set the weights value field while applying dollar averaging on existing portfolio.
   * If the user gives invalid format ,sum not equals to 100 or the dialog pane,
   * is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the weights input field
   */
  public void setweightsexist(String message) {
    this.weightsexist.setText(message);
  }


  /**
   * Set the money to be invested in dollars,
   * while applying dollar cost averaging on the existing portfolio.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is values that needs to be set for the value field
   */
  public void setdollarexistval(String message) {
    this.dollarexistval.setText(message);
  }

  /**
   * Set the status of the start-to-finish dollar strategy pane based on the user inputs.
   * Tells if portfolio strategy is applied, or the dialog pane is closed,
   * or not or any invalid inputs is given.
   *
   * @param message set the status of the panel based on the user inputs given
   */
  public void setdollarnewpanestatus(String message) {
    this.dollarnewpanestatus.setText(message);
  }

  private JPanel getCreatePfDialog() {
    JPanel createPanelDialog = new JPanel();
    this.pfnamecreate = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name to be created: ");
    dateofcreation = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase in YYYY-MM-DD format. " +
            "Ex: 2022-02-01 :");
    JLabel numstocksLabel = new JLabel("Enter number of stocks to purchase. Negative " +
            "and Fractional stock values are not allowed :");
    numstockscreate = new JTextField(25);
    JLabel tickrLabel = new JLabel("Enter Tickr symbol of a company you want to purchase." +
            " Ex: for company google tickr symbol - GOOG :");
    tickrcreate = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees. Negative values are not " +
            "allowed (It is an optional parameter):");
    commissionfeescreate = new JTextField(25);
    createPanelDialog.setPreferredSize(new Dimension(1400, 300));
    createPanelDialog.setLayout(new BoxLayout(createPanelDialog, BoxLayout.Y_AXIS));
    createPanelDialog.add(pfNameLabel);
    createPanelDialog.add(pfnamecreate);
    createPanelDialog.add(dateLabel);
    createPanelDialog.add(dateofcreation);
    createPanelDialog.add(numstocksLabel);
    createPanelDialog.add(numstockscreate);
    createPanelDialog.add(tickrLabel);
    createPanelDialog.add(tickrcreate);
    createPanelDialog.add(commissionLabel);
    createPanelDialog.add(commissionfeescreate);
    createPanelDialog.add(add);
    createPanelDialog.add(save);
    createPanelDialog.add(createDialogStatus);
    return createPanelDialog;
  }

  /**
   * Set the status of the portfolio path while creating new flexible portfolio.
   * If invalid path or existing path or dialog pane is given then this field is set to null.
   *
   * @param message is value that needs to be set for the pfname field
   */
  public void setCreatePfValue(String message) {
    this.pfnamecreate.setText(message);
  }

  /**
   * Set the date of purchase of the stock while creating new flexible portfolio.
   * If invalid date format, future date is given or dialog pane is closed,
   * then this field is set to null.
   *
   * @param message is value that needs to be set for the date field
   */
  public void setdateofcreationValue(String message) {
    this.dateofcreation.setText(message);
  }

  /**
   * Set the number of stocks field while creating new flexible portfolio.
   * If invalid number of stocks, negative stocks or fractional stocks are given,
   * dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the num of stocks field
   */
  public void setnumstockscreateValue(String message) {
    this.numstockscreate.setText(message);
  }

  /**
   * Set the tickr value for which company stocks needs to be purchased,
   * while creating a flexible portfolio.
   * If invalid tickr symbol or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the tickr symbol field
   */
  public void settickrcreateValue(String message) {
    this.tickrcreate.setText(message);
  }

  /**
   * Set the commission value while creating a flexible portfolio.
   * If negative stocks, or invalid value is given or dialog pane is closed,
   * then this field is set to null.
   *
   * @param message is value that needs to be set for the commission field
   */
  public void setcommissionfeescreateValue(String message) {
    this.commissionfeescreate.setText(message);
  }

  /**
   * Set the portfolio path field while modifying the portfolio.
   * If a portfolio that does not exist is given or modify dialog pane is closed,
   * then it is set to null.
   *
   * @param message is value that needs to be set for the pf name field
   */
  public void setModifyPfValue(String message) {
    this.pfnamemodify.setText(message);
  }

  /**
   * Set the date of purchase or sell in the modify pane, if invalid date format,
   * future date or modify dialog pane,
   * then set to null.
   *
   * @param message is value that needs to be set for the date field
   */
  public void setdateofmodifynValue(String message) {
    this.dateofmodify.setText(message);
  }

  /**
   * Set the number of stocks field while modifying flexible portfolio.
   * If invalid number of stocks, negative stocks or fractional stocks are given,
   * dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the num of stocks field
   */
  public void setnumstocksmodifyValue(String message) {
    this.numstocksmodify.setText(message);
  }

  /**
   * Set the tickr value for which company stocks needs to be purchased or sold,
   * while modifying a flexible portfolio.If invalid tickr symbol,
   * or dialog pane is closed then this field is set to null.
   *
   * @param message is value that needs to be set for the tickr symbol field
   */
  public void settickrmodifyValue(String message) {
    this.tickrmodify.setText(message);
  }

  /**
   * Set the commission value while modifying a flexible portfolio.
   * If negative stocks, or invalid value is given or dialog pane is closed,
   * then this field is set to null.
   *
   * @param message is value that needs to be set for the commission field
   */
  public void setcommissionfeesmodifyValue(String message) {
    this.commissionfessmodify.setText(message);
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
    createDialogStatus.setText(message);
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
    modifyDialogStatus.setText(message);
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
    valDialogStatus.setText(message);
  }

  /**
   * Set the status of the composition dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty,
   * if inputs given are invalid show which are wrong.
   *
   * @param message status while retrieving the composition of portfolio
   */
  public void setretrieveDialogStatus(String message) {
    retrieveDialogStatus.setText(message);
  }

  /**
   * Set the status of the cost basis dialog pane based on the given inputs.
   * If no fields are set then set it to fields are empty,
   * if inputs given are invalid show which are wrong.
   *
   * @param message gives the cost basis of the given portfolio
   */
  public void setCostBasisDialogStatus(String message) {
    costBasisDialogStatus.setText(message);
  }

  private JPanel getCostBasisPanelDialog() {
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListBasis.setEditable(false);
    JPanel costBasisDialog = new JPanel();
    pfName = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name:");
    date = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute cost basis:");
    costBasisDialog.setLayout(new BoxLayout(costBasisDialog, BoxLayout.Y_AXIS));
    costBasisDialog.setPreferredSize(new Dimension(700, 700));
    costBasisDialog.setMaximumSize(new Dimension(900, 500));
    costBasisDialog.setMinimumSize(new Dimension(400, 400));
    costBasisDialog.add(listPortfolios);
    costBasisDialog.add(portfoliosListBasis);
    costBasisDialog.add(pfNameLabel);
    costBasisDialog.add(pfName);
    costBasisDialog.add(dateLabel);
    costBasisDialog.add(date);
    costBasisDialog.add(getCostBasis);
    costBasisDialog.add(costBasisDialogStatus);
    return costBasisDialog;
  }

  /**
   * Get the list of all the portfolios to be modified in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setportfoliosListModify(String message) {
    this.portfoliosListModify.setText(message);
  }

  /**
   * Get the list of all the portfolios to query the value in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setPortfoliosListVal(String message) {
    this.portfoliosListVal.setText(message);
  }

  /**
   * Get the list of all the portfolios to query the cost basis in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setPortfoliosListBasis(String message) {
    this.portfoliosListBasis.setText(message);
  }

  /**
   * Get the list of all the portfolios to query the composition in the given user path.
   * If the folder is empty then say the portfolio is empty. If a file that does not exist is given,
   * say given pfname does not exist.
   *
   * @param message is the list of existing portfolios in the given user path
   */
  public void setPortfoliosListRetrieve(String message) {
    this.portfoliosListRetrieve.setText(message);
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
    this.portfolioslistdollarexist.setText(message);
  }

  /**
   * Set the portfolio name on existing dollar strategy pane based on the user input.
   * If invalid input is given, or the dialog pane is closed or strategy is applied null is set.
   *
   * @param message is the value that needs to be set for the portfolio field
   */
  public void setpfNameExistDollar(String message) {
    this.dollarexistpfname.setText(null);
  }

  /**
   * Set the date for the existing dollar cost straegy portfolio based on the input given.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the date field
   */
  public void setdollardateexist(String message) {
    this.dollarexistdate.setText(message);
  }

  /**
   * Set the commission value on existing dollar cost averaging pane.
   * If the user gives invalid values or the dialog pane is closed this field is set to null.
   *
   * @param message is the value that needs to be set for the commission field
   */
  public void setdollarexistcommisionval(String message) {
    this.dollarexistcommision.setText(message);
  }

  /**
   * Set the portfolio name to be retrieved while getting composition of a portfolio.
   *
   * @param message is the value that needs to be set for the pf name field in retrieve dialog pane
   */
  public void setpfnameretrieve(String message) {
    this.pfnameretrieve.setText(message);
  }

  /**
   * Set the composition of the portfolio given.
   *
   * @param message is the composition of a particular portfolio
   */
  public void setPortfoliosListComposition(String message) {
    this.portfolioComposition.setText(message);
  }

  private JPanel getModifyPanelDialog() {
    JPanel modifyDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListModify.setEditable(false);
    pfnamemodify = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter portfolio name to be modified. Enter only names " +
            "from the given list of portfolios. If no portfolios exist then close this dialog " +
            "pane by clicking 'X' on top left");
    dateofmodify = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase/sell " +
            "in YYYY-DD-MM format only. Ex-2021-02-02");
    tickrmodify = new JTextField(25);
    JLabel tickrlabel = new JLabel("Enter Tickr symbol of a company you want to purchase " +
            "or sell. Ex: for company google tickr symbol - GOOG.");
    numstocksmodify = new JTextField(25);
    JLabel numstockslabel = new JLabel("Enter the number of stocks to be purchased/sold." +
            "Negative and Fractional shares are not allowed");
    commissionfessmodify = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees. Negative values are not " +
            "allowed. (Optional parameter)");
    modifyDialog.setLayout(new BoxLayout(modifyDialog, BoxLayout.Y_AXIS));
    modifyDialog.setPreferredSize(new Dimension(1000, 700));
    modifyDialog.setMaximumSize(new Dimension(700, 500));
    modifyDialog.setMinimumSize(new Dimension(500, 500));
    modifyDialog.add(listPortfolios);
    modifyDialog.add(portfoliosListModify);
    modifyDialog.add(pfNameLabel);
    modifyDialog.add(pfnamemodify);
    modifyDialog.add(dateLabel);
    modifyDialog.add(dateofmodify);
    modifyDialog.add(numstockslabel);
    modifyDialog.add(numstocksmodify);
    modifyDialog.add(tickrlabel);
    modifyDialog.add(tickrmodify);
    modifyDialog.add(commissionLabel);
    modifyDialog.add(commissionfessmodify);
    modifyDialog.add(purchase);
    modifyDialog.add(sell);
    modifyDialog.add(modifyDialogStatus);
    return modifyDialog;
  }

  private JPanel getValuePanelDialog() {
    JPanel valDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListVal.setEditable(false);
    pfnamevalue = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name for which value needs to be " +
            "fetched. Enter only names from the given list of portfolios. If no portfolios exist " +
            "then close this dialog pane by clicking 'X' on top left");
    datevalue = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute value of the portfolio in " +
            "YYYY-DD-MM format only. Ex-2021-02-02");
    valDialog.setLayout(new BoxLayout(valDialog, BoxLayout.Y_AXIS));
    valDialog.setPreferredSize(new Dimension(700, 700));
    valDialog.setMaximumSize(new Dimension(900, 500));
    valDialog.setMinimumSize(new Dimension(400, 400));
    valDialog.add(listPortfolios);
    valDialog.add(portfoliosListVal);
    valDialog.add(pfNameLabel);
    valDialog.add(pfnamevalue);
    valDialog.add(dateLabel);
    valDialog.add(datevalue);
    valDialog.add(computeval);
    valDialog.add(valDialogStatus);
    return valDialog;
  }

  private JPanel getRetrievePanelDialog() {
    JPanel retrievePfDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    JLabel listcomposition = new JLabel("Portfolio composition");
    portfoliosListRetrieve.setEditable(false);
    portfolioComposition.setEditable(false);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name for which composition needs to be " +
            "fetched. Enter only names from the given list of portfolios. If no portfolios exist " +
            "then close this dialog pane by clicking 'X' on top left");
    pfnameretrieve = new JTextField(25);
    retrievePfDialog.setLayout(new BoxLayout(retrievePfDialog, BoxLayout.Y_AXIS));
    retrievePfDialog.setPreferredSize(new Dimension(700, 700));
    retrievePfDialog.setMaximumSize(new Dimension(1000, 1000));
    retrievePfDialog.setMinimumSize(new Dimension(400, 400));
    retrievePfDialog.add(listPortfolios);
    retrievePfDialog.add(portfoliosListRetrieve);
    retrievePfDialog.add(pfNameLabel);
    retrievePfDialog.add(pfnameretrieve);
    retrievePfDialog.add(computecomposition);
    retrievePfDialog.add(retrieveDialogStatus);
    retrievePfDialog.add(listcomposition);
    retrievePfDialog.add(portfolioComposition);
    return retrievePfDialog;
  }

  private JPanel getDollarCostNewDialog() {
    JPanel dollarnewPfDialog = new JPanel();
    JLabel stratergyname = new JLabel("Enter the name of the strategy");
    stratergydollarnewname = new JTextField(25);
    JLabel createpf = new JLabel("Enter the name of the portfolio to be created");
    dollarnewcreatepfname = new JTextField(25);

    JLabel stocksweightsdesc = new JLabel("Enter the stocks tickr symbols, if multiple " +
            "entries separate them by delimeter ','. DO NOT end with ',' after adding" +
            " all the entries");
    JLabel stocksone = new JLabel("For single entry - GOOG");
    JLabel stockstwo = new JLabel("For multiple entries - ex-GOOG,UBER,....");
    JLabel stocksthree = new JLabel("GOOG,UBER, (or) GOOG, is a wrong format");
    stocksnew = new JTextField(100);
    JLabel weightsone = new JLabel("Enter the corresponding weights for the given stocks " +
            "seperated by delimeter ','.DO NOT end with ',' after adding all the entries. " +
            "Ex- 10,90. Values entered are considered to be percentages.");
    weightsnew = new JTextField(100);

    JLabel money = new JLabel("Enter the money to be invested in the portfolio");
    dollarnewval = new JTextField(25);
    JLabel startdate = new JLabel("Enter the start date of investment");
    dollarnewstartdate = new JTextField(25);
    dollarnewenddate = new JTextField(25);
    JLabel enddate = new JLabel("Enter the end date of investment (Optional Parameter, " +
            "If not given then end date is assumed to be one year after date of the " +
            "given start date)");
    dollarnewdays = new JTextField(25);
    JLabel days = new JLabel("Enter the number of days after which this investment " +
            "should recur");
    dollarnewcommission = new JTextField(25);
    JLabel commission = new JLabel("Enter the commission fees (Optional parameter)");
    dollarnewPfDialog.setLayout(new BoxLayout(dollarnewPfDialog, BoxLayout.Y_AXIS));
    dollarnewPfDialog.add(stratergyname);
    dollarnewPfDialog.add(stratergydollarnewname);
    dollarnewPfDialog.add(createpf);
    dollarnewPfDialog.add(dollarnewcreatepfname);
    dollarnewPfDialog.add(stocksweightsdesc);
    dollarnewPfDialog.add(stocksone);
    dollarnewPfDialog.add(stockstwo);
    dollarnewPfDialog.add(stocksthree);
    dollarnewPfDialog.add(stocksnew);
    dollarnewPfDialog.add(weightsone);
    dollarnewPfDialog.add(weightsnew);
    dollarnewPfDialog.add(money);
    dollarnewPfDialog.add(dollarnewval);
    dollarnewPfDialog.add(startdate);
    dollarnewPfDialog.add(dollarnewstartdate);
    dollarnewPfDialog.add(enddate);
    dollarnewPfDialog.add(dollarnewenddate);
    dollarnewPfDialog.add(days);
    dollarnewPfDialog.add(dollarnewdays);
    dollarnewPfDialog.add(commission);
    dollarnewPfDialog.add(dollarnewcommission);
    dollarnewPfDialog.add(dollarnewcreate);
    dollarnewPfDialog.add(dollarnewpanestatus);
    return dollarnewPfDialog;
  }

  private JPanel getDollarCostExistDialog() {
    JPanel dollarexistingPfDialog = new JPanel();
    JLabel listallportfolios = new JLabel("List of all the portfolios present in the " +
            "given path");
    JLabel pfname = new JLabel("Enter the name of the portfolio to add dollar cost " +
            "averaging statergy");
    portfolioslistdollarexist.setEditable(false);
    dollarexistpfname = new JTextField(25);
    JLabel stratergynamedollarexist = new JLabel("Enter the name of the strategy");
    stratergydollarexistname = new JTextField(25);
    JLabel stocksweightsdesc = new JLabel("Enter the stocks tickr symbols, " +
            "if multiple entries separate them by delimeter ','.");
    JLabel stocksone = new JLabel("For single entry - ex-GOOG");
    JLabel stockstwo = new JLabel("For multiple entries - ex-GOOG,UBER,....");
    JLabel stocksthree = new JLabel("Ending delimeter ',' is ignored - ex-GOOG,UBER,,,,");
    stocksexist = new JTextField(100);
    JLabel weightsone = new JLabel("Enter the corresponding weights for the given stocks " +
            "seperated by delimeter ',' Ending delimeter ',' is ignored - ex-10,90,,,,.");
    weightsexist = new JTextField(100);
    JLabel money = new JLabel("Enter the money to be invested in the portfolio " +
            "(Taken in dollars)");
    dollarexistval = new JTextField(25);
    JLabel date = new JLabel("Enter the date of investment");
    dollarexistdate = new JTextField(25);
    JLabel commission = new JLabel("Enter the commission fees (Optional Parameter)");
    dollarexistcommision = new JTextField(25);
    dollarexistingPfDialog.setLayout(new BoxLayout(dollarexistingPfDialog, BoxLayout.Y_AXIS));
    dollarexistingPfDialog.setPreferredSize(new Dimension(1000, 700));
    dollarexistingPfDialog.setMaximumSize(new Dimension(1200, 1200));
    dollarexistingPfDialog.setMinimumSize(new Dimension(800, 800));
    dollarexistingPfDialog.add(listallportfolios);
    dollarexistingPfDialog.add(portfolioslistdollarexist);
    dollarexistingPfDialog.add(pfname);
    dollarexistingPfDialog.add(dollarexistpfname);
    dollarexistingPfDialog.add(stratergynamedollarexist);
    dollarexistingPfDialog.add(stratergydollarexistname);
    dollarexistingPfDialog.add(stocksweightsdesc);
    dollarexistingPfDialog.add(stocksone);
    dollarexistingPfDialog.add(stockstwo);
    dollarexistingPfDialog.add(stocksthree);
    dollarexistingPfDialog.add(stocksexist);
    dollarexistingPfDialog.add(weightsone);
    dollarexistingPfDialog.add(weightsexist);
    dollarexistingPfDialog.add(money);
    dollarexistingPfDialog.add(dollarexistval);
    dollarexistingPfDialog.add(date);
    dollarexistingPfDialog.add(dollarexistdate);
    dollarexistingPfDialog.add(commission);
    dollarexistingPfDialog.add(dollarexistcommision);
    dollarexistingPfDialog.add(dollarexistcreate);
    dollarexistingPfDialog.add(dollarexistpanestatus);
    return dollarexistingPfDialog;
  }

  /**
   * Set the name of the portfolio name for which value needs to be calculated on a specific date.
   * If name given is not present in the existing portfolios then it sets to null.
   *
   * @param message value that needs to be set for pf name field
   */
  public void setpfnameVal(String message) {
    pfnamevalue.setText(message);
  }

  /**
   * Set the date value in the dialog pane that computes date.
   * Set to null if invalid date is entered or future date is entered by the user.
   *
   * @param message value that needs to be set for date field
   */
  public void setdateVal(String message) {
    this.datevalue.setText(message);
  }

  /**
   * Set the name of the portfolio name for which cost basis needs,
   * to be calculated till a specific date.
   * If name given is not present in the existing portfolios then it sets to null.
   *
   * @param message value that needs to be set for pf name field
   */
  public void setpfNameCostBasis(String message) {
    pfName.setText(message);
  }

  /**
   * Set date of the portfolio in the cost basis dialog pane.
   * if the date is in future or in invalid format then set to null.
   *
   * @param message value that needs to be set for date field
   */
  public void setDate(String message) {
    date.setText(message);
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
    costBasisStatus.setText(message);
  }

  /**
   * Set the status of the retrieve label when get retrieve composition button is clicked.
   * If button is clicked before setting user path then, label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for getting composition label based on users input
   */
  public void setRetrievePanelStatus(String message) {
    retrievePanelStatus.setText(message);
  }

  /**
   * When get cost basis button is clicked in the main panel,
   * this opens the dialog pane that would let user,
   * to enter required entries to compute cost basis of a portfolio.
   */
  public void displayCostBasis() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCostBasisPanelDialog(),
            JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
            new Object[]{}, null);
    dialog = optionPane.createDialog("Get Cost Basis");
    dialog.setVisible(true);
  }

  /**
   * Displays the create portfolio dialog pane that lets users create a flexible portfolio,
   * when create portfolio button is clicked.
   */
  public void displayCreatePf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCreatePfDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Create Portfolio");
    dialog.setVisible(true);
  }

  /**
   * Displays modify portfolio dialog pane,
   * lets users modify a flexible portfolio by purchasing and selling,
   * when modify portfolio button is clicked.
   */
  public void displayModifyPf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getModifyPanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Modify Portfolio");
    dialog.setVisible(true);
  }

  /**
   * Display the value portfolio dialog pane,
   * lets users get the value of a flexible portfolio on a specific date.
   */
  public void displayValuepf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getValuePanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Value of the Portfolio");
    dialog.setVisible(true);
  }

  /**
   * Display the composition of the portfolio pane,
   * gives the portfolio composition on a given date for a portfolio.
   */
  public void displayRetrievepf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getRetrievePanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Composition of the Portfolio");
    dialog.setVisible(true);
  }

  /**
   * Display the pane that lets users apply dollar strategy on existing flexible portfolios.
   */
  public void displayDollarExistingpf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getDollarCostExistDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Dollar cost Averaging for existing portfolios!");
    dialog.setVisible(true);
  }

  /**
   * Display the pane that lets users apply start-to-finish dollar averaging strategy.
   */
  public void displayDollarNewpf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getDollarCostNewDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Start-to-Finish dollar cost averaging");
    dialog.setVisible(true);
  }

  private void getMainPanel() {
    mainJPanel = new JPanel();
    mainJPanel.setLayout(new BoxLayout(mainJPanel, BoxLayout.Y_AXIS));
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
    this.dollarExistingStatus.setText(message);
  }

  /**
   * Set the status of the value label start-to-finish dollar portfolio button is clicked.
   * If button is clicked before setting user path then, label prompts to get user directory path.
   * If user path was not specified then sets to null.
   *
   * @param message that needs to be set for applying  start-to-finish dollar strategy label
   */
  public void setdollarNewStatus(String message) {
    this.dollarNewStatus.setText(message);
  }

  private void getCostBasisPanel() {
    getCostBasis = new JButton("Compute Cost Basis");
    costBasisPanel = new JPanel();
    costBasisPanel.setBorder(BorderFactory.createTitledBorder("Get Cost Basis of portfolio till a" +
            " specific date "));
    costBasisButton = new JButton("Get cost basis");
    costBasisPanel.add(costBasisButton);
    costBasisStatus = new JLabel();
    costBasisPanel.add(costBasisStatus);
  }

  private void getRetrievePfPanel() {
    retrievePanel = new JPanel();
    retrievePf = new JButton("Retrieve Portfolio");
    retrievePanel.setBorder(BorderFactory.createTitledBorder("Retrieve Composition of a " +
            "flexible portfolio"));
    retrievePanel.add(retrievePf);
    retrievePanelStatus = new JLabel();
    retrievePanel.add(retrievePanelStatus);
  }

  private void getDollarCostExisting() {
    dollarPanelExisting = new JPanel();
    dollarCostExisting = new JButton("Calculate dollar cost averaging for existing portfolio");
    dollarPanelExisting.setBorder(BorderFactory.createTitledBorder("Create dollar cost " +
            "averaging for existing flexible portfolios"));
    dollarExistingStatus = new JLabel();
    dollarPanelExisting.add(dollarCostExisting);
    dollarPanelExisting.add(dollarExistingStatus);
  }

  private void getDollarCostNew() {
    dollarPanelNew = new JPanel();
    dollarPanelNew.setBorder(BorderFactory.createTitledBorder("Create a start-to-finish dollar " +
            "cost averaging portfolio"));
    dollarCostNew = new JButton("Create dollar cost averaging start-to-finish for a new" +
            "portfolio");
    dollarNewStatus = new JLabel();
    dollarPanelNew.add(dollarCostNew);
    dollarPanelNew.add(dollarNewStatus);
  }

  private void finalPanel() {
    setTitle("Stocks Program");
    getMainPanel();
    userPanelObj.getUserPanel();
    createPanelObj.createPanel();
    createPanelObj.createPanel();
    modifyPanelObj.modifyPanel();
    valueDatePanelObj.valDatePanel();
    getCostBasisPanel();
    getRetrievePfPanel();
    getDollarCostExisting();
    getDollarCostNew();
    QuitPanel quitPanelObj = new QuitPanelImpl();
    quitPanelObj.getQuitPanel();
    mainJPanel.add(userPanelObj.returnUserPanel());
    mainJPanel.add(createPanelObj.getcreatePanel());
    mainJPanel.add(modifyPanelObj.getModifyPanel());
    mainJPanel.add(costBasisPanel);
    mainJPanel.add(valueDatePanelObj.getValueDatePanel());
    mainJPanel.add(retrievePanel);
    mainJPanel.add(dollarPanelExisting);
    mainJPanel.add(dollarPanelNew);
    mainJPanel.add(quitPanelObj.quitPanelfinal());
    add(mainJPanel);
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
    add.addActionListener(evt -> {
      try {
        features.addOperation(this.pfnamecreate.getText(), this.tickrcreate.getText().toUpperCase(),
                this.numstockscreate.getText(), this.dateofcreation.getText(),
                this.commissionfeescreate.getText());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    });
    save.addActionListener(evt -> features.saveOperation(this.pfnamecreate.getText()));
    modifyPanelObj.returngetModifyButton().addActionListener(evt ->
            features.displayDialogPane("modify"));
    purchase.addActionListener(evt -> {
      try {
        features.modifyValidate(this.pfnamemodify.getText(),
                this.tickrmodify.getText().toUpperCase(), this.numstocksmodify.getText(),
                this.dateofmodify.getText(), this.commissionfessmodify.getText(),
                "purchase");
      } catch (FileNotFoundException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    sell.addActionListener(evt -> {
      try {
        features.modifyValidate(this.pfnamemodify.getText(),
                this.tickrmodify.getText().toUpperCase(),
                this.numstocksmodify.getText(), this.dateofmodify.getText(),
                this.commissionfessmodify.getText(), "sell");
      } catch (FileNotFoundException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    valueDatePanelObj.returnValueButton().addActionListener(evt -> features.displayDialogPane("getDateVal"));
    computeval.addActionListener(evt -> {
      try {
        features.validateDateVal(this.pfnamevalue.getText(), this.datevalue.getText());
      } catch (FileNotFoundException | ParseException e) {
        throw new RuntimeException(e);
      }
    });
    costBasisButton.addActionListener(evt -> features.displayDialogPane("costBasis"));
    getCostBasis.addActionListener(evt -> {
      try {
        features.validateCostBasis(this.pfName.getText(), this.date.getText());
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    });
    retrievePf.addActionListener(evt -> features.displayDialogPane("composition"));
    computecomposition.addActionListener(evt ->
            features.getCompositionpf(this.pfnameretrieve.getText()));
    dollarCostExisting.addActionListener(evt -> features.displayDialogPane("dollarexist"));
    dollarCostNew.addActionListener(evt -> features.displayDialogPane("dollarnew"));
    dollarexistcreate.addActionListener(evt -> features.validateExistingDollar(
            stratergydollarexistname.getText(), dollarexistpfname.getText(),
            stocksexist.getText().toUpperCase(), weightsexist.getText(), dollarexistval.getText(),
            dollarexistdate.getText(), dollarexistcommision.getText()));
    dollarnewcreate.addActionListener(evt -> {
      try {
        features.validateNewDollar(
                stratergydollarnewname.getText(), dollarnewcreatepfname.getText(),
                stocksnew.getText().toUpperCase(), weightsnew.getText(), dollarnewval.getText(),
                dollarnewdays.getText(), dollarnewstartdate.getText(), dollarnewenddate.getText(),
                dollarnewcommission.getText());
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    });
  }
}
