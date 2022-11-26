package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.text.ParseException;

import javax.swing.*;

import controller.ControllerGUI;

public class GUIViewImpl extends JFrame implements GUIView {
  private JLabel pathStore;
  private JLabel createStatus;
  private final JLabel modifyStatus = new JLabel();
  private final JLabel valueStatus = new JLabel();
  private JButton inputButton;
  private JButton costBasisButton;
  private JButton quitButton;
  private JButton createPfButton;
  private JPanel mainJPanel;
  private JPanel userPanel;
  private JPanel createPanel;
  private JPanel modifyPanel;
  private JPanel valueDatePanel;
  private JPanel dollarPanelExisting;
  private JPanel dollarPanelNew;
  private JPanel quitPanel;
  private JPanel costBasisPanel;
  private JLabel retrievePanelStatus;
  private JPanel retrievePanel;
  private JButton retrievePf = new JButton("Retrieve Portfolio");
  private JButton getCostBasis;
  private JButton getValueButton;
  private JButton getModifyButton;
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

  public GUIViewImpl() {
    super();
    finalPanel();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public void setpathStore(String setMessage) {
    pathStore.setText(setMessage);
  }

  private JPanel getCreatePfDialog() {
    JPanel createPanelDialog = new JPanel();
    this.pfnamecreate = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name to be created: ");
    dateofcreation = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase in YYYY-MM-DD format: ");
    JLabel numstocksLabel = new JLabel("Enter number of stocks to purchase: ");
    numstockscreate = new JTextField(25);
    JLabel tickrLabel = new JLabel("Enter Tickr symbol: ");
    tickrcreate = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees: ");
    commissionfeescreate = new JTextField(25);
    createPanelDialog.setPreferredSize(new Dimension(700, 300));
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

  public void setCreatePfValue(String message) {
    this.pfnamecreate.setText(message);
  }

  public void setdateofcreationValue(String message) {
    this.dateofcreation.setText(message);
  }

  public void setnumstockscreateValue(String message) {
    this.numstockscreate.setText(message);
  }

  public void settickrcreateValue(String message) {
    this.tickrcreate.setText(message);
  }

  public void setcommissionfeescreateValue(String message) {
    this.commissionfeescreate.setText(message);
  }

  public void setModifyPfValue(String message) {
    this.pfnamemodify.setText(message);
  }

  public void setdateofmodifynValue(String message) {
    this.dateofmodify.setText(message);
  }

  public void setnumstocksmodifyValue(String message) {
    this.numstocksmodify.setText(message);
  }

  public void settickrmodifyValue(String message) {
    this.tickrmodify.setText(message);
  }

  public void setcommissionfeesmodifyValue(String message) {
    this.commissionfessmodify.setText(message);
  }

  public void setcreateDialogStatus(String message) {
    createDialogStatus.setText(message);
  }

  public void setmodifyDialogStatus(String message) {
    modifyDialogStatus.setText(message);
  }

  public void setvalueDialogStatus(String message) {
    valDialogStatus.setText(message);
  }
  public void setretrieveDialogStatus(String message){
    retrieveDialogStatus.setText(message);
  }

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

  public void setportfoliosListModify(String message) {
    this.portfoliosListModify.append(message);
  }

  public void setPortfoliosListVal(String message) {
    this.portfoliosListVal.append(message);
  }

  public void setPortfoliosListBasis(String message) {
    this.portfoliosListBasis.append(message);
  }
  public void setPortfoliosListRetrieve(String message){
    this.portfoliosListRetrieve.append(message);
  }

  private JPanel getModifyPanelDialog() {
    JPanel modifyDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListModify.setEditable(false);
    pfnamemodify = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio name to be modified");
    dateofmodify = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase/sell " +
            "in YYYY-DD-MM format only!!");
    tickrmodify = new JTextField(25);
    JLabel tickrlabel = new JLabel("Enter tickr symbol");
    numstocksmodify = new JTextField(25);
    JLabel numstockslabel = new JLabel("Enter the number of stocks to be purchased/sold");
    commissionfessmodify = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees");
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
    modifyDialog.add(tickrlabel);
    modifyDialog.add(tickrmodify);
    modifyDialog.add(numstockslabel);
    modifyDialog.add(numstocksmodify);
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
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name");
    datevalue = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute value of the portfolio");
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

  private JPanel getRetrievePanelDialog(){
    JPanel retrievePfDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListRetrieve.setEditable(false);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name");
    pfnameretrieve = new JTextField(25);
    retrievePfDialog.setLayout(new BoxLayout(retrievePfDialog, BoxLayout.Y_AXIS));
    retrievePfDialog.setPreferredSize(new Dimension(700, 700));
    retrievePfDialog.setMaximumSize(new Dimension(900, 500));
    retrievePfDialog.setMinimumSize(new Dimension(400, 400));
    retrievePfDialog.add(listPortfolios);
    retrievePfDialog.add(portfoliosListRetrieve);
    retrievePfDialog.add(pfNameLabel);
    retrievePfDialog.add(pfnameretrieve);
    retrievePfDialog.add(computecomposition);
    retrievePfDialog.add(retrieveDialogStatus);
    return retrievePfDialog;
  }

  public void setpfnameVal(String message) {
    pfnamevalue.setText(message);
  }

  public void setdateVal(String message) {
    this.datevalue.setText(message);
  }

  public void setpfNameCostBasis(String message) {
    pfName.setText(message);
  }

  public void setDate(String message) {
    date.setText(message);
  }

  public void setLabelCostBasisStatus(String message) {
    costBasisStatus.setText(message);
  }
  public void setRetrievePanelStatus(String message){
    retrievePanelStatus.setText(message);
  }

  public void displayCostBasis() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCostBasisPanelDialog(),
            JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
            new Object[]{}, null);
    dialog = optionPane.createDialog("Get Cost Basis");
    dialog.setVisible(true);
  }

  public void displayCreatePf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCreatePfDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Create Portfolio");
    dialog.setVisible(true);
  }

  public void displayModifyPf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getModifyPanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Modify Portfolio");
    dialog.setVisible(true);
  }

  public void displayValuepf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getValuePanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Value of the Portfolio");
    dialog.setVisible(true);
  }

  public void displayRetrievepf(){
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getRetrievePanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Composition of the Portfolio");
    dialog.setVisible(true);
  }

  private void getMainPanel() {
    mainJPanel = new JPanel();
    mainJPanel.setLayout(new BoxLayout(mainJPanel, BoxLayout.Y_AXIS));
  }

  private void getUserPanel() {
    userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("User Path"));
    inputButton = new JButton("Create User Path");
    userPanel.add(inputButton);
    pathStore = new JLabel();
    userPanel.add(pathStore);
  }

  private void getCreatePanel() {
    createPanel = new JPanel();
    createPanel.setBorder(BorderFactory.createTitledBorder("Create Flexible Portfolio"));
    createPfButton = new JButton("Create a New Portfolio");
    createStatus = new JLabel();
    createPanel.add(createPfButton);
    createPanel.add(createStatus);
  }

  public void setCreateLabelStatus(String message) {
    this.createStatus.setText(message);
  }

  public void setModifyLabelStatus(String message) {
    this.modifyStatus.setText(message);
  }

  public void setValueLabelStatus(String message) {
    this.valueStatus.setText(message);
  }

  private void getModifyPanel() {
    modifyPanel = new JPanel();
    getModifyButton = new JButton("Modify a Portfolio");
    modifyPanel.setBorder(BorderFactory.createTitledBorder("Modify a specific Portfolio"));
    modifyPanel.add(getModifyButton);
    modifyPanel.add(modifyStatus);
  }

  private void getValueDatePanel() {
    valueDatePanel = new JPanel();
    getValueButton = new JButton("Get Value of Portfolio on a Specific Date");
    valueDatePanel.setBorder(BorderFactory.createTitledBorder("Get Value of portfolio on a " +
            "specific date"));
    valueDatePanel.add(getValueButton);
    valueDatePanel.add(valueStatus);
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

  private void getRetrievePfPanel(){
    retrievePanel = new JPanel();
    retrievePf = new JButton("Retrieve Portfolio");
    retrievePanel.setBorder(BorderFactory.createTitledBorder("Retrieve Composition of a " +
            "Portfolio"));
    retrievePanel.add(retrievePf);
    retrievePanelStatus = new JLabel();
    retrievePanel.add(retrievePanelStatus);
  }

  private void getQuitPanel() {
    quitPanel = new JPanel();
    quitPanel.setBorder(BorderFactory.createTitledBorder("Quit the stocks program"));
    quitButton = new JButton("Quit the Program");
    quitButton.addActionListener((ActionEvent e) -> {
      System.exit(0);
    });
    quitPanel.add(quitButton);
  }

  private void getDollarCostExisting() {
    dollarPanelExisting = new JPanel();
    dollarCostExisting = new JButton("Calculate dollar cost averaging for existing portfolio");
    dollarPanelExisting.setBorder(BorderFactory.createTitledBorder("Create dollar cost " +
            "averaging for existing flexible portfolios"));
    dollarCostExisting.setActionCommand("Calculate dollar cost");
    dollarPanelExisting.add(dollarCostExisting);
  }

  private void getDollarCostNew() {
    dollarPanelNew = new JPanel();
    dollarPanelNew.setBorder(BorderFactory.createTitledBorder("Create a start-to-finish dollar " +
            "cost averaging portfolio"));
    dollarCostNew = new JButton("Create dollar cost averaging start-to-finish for a new" +
            "portfolio");
    dollarCostNew.setActionCommand("Start-to-Finish");
    dollarPanelNew.add(dollarCostNew);
  }

  private void finalPanel() {
    setTitle("Stocks Program");
    getMainPanel();
    getUserPanel();
    getCreatePanel();
    getModifyPanel();
    getValueDatePanel();
    getCostBasisPanel();
    getRetrievePfPanel();
    getDollarCostExisting();
    getDollarCostNew();
    getQuitPanel();
    mainJPanel.add(userPanel);
    mainJPanel.add(createPanel);
    mainJPanel.add(modifyPanel);
    mainJPanel.add(costBasisPanel);
    mainJPanel.add(valueDatePanel);
    mainJPanel.add(retrievePanel);
    mainJPanel.add(dollarPanelExisting);
    mainJPanel.add(dollarPanelNew);
    mainJPanel.add(quitPanel);
    add(mainJPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 1000);
    setLocationRelativeTo(null);
  }

  public void addFeatures(ControllerGUI features) {
    inputButton.addActionListener(evt -> features.setDirectory());
    createPfButton.addActionListener(evt -> features.displayDialogPane("create"));
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
    getModifyButton.addActionListener(evt -> features.displayDialogPane("modify"));
    purchase.addActionListener(evt -> {
      try {
        features.modifyValidate(this.pfnamemodify.getText(),
                this.tickrmodify.getText().toUpperCase(), this.numstocksmodify.getText(),
                this.dateofmodify.getText(), this.commissionfessmodify.getText(),
                "purchase");
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    });
    sell.addActionListener(evt -> {
      try {
        features.modifyValidate(this.pfnamemodify.getText(),
                this.tickrmodify.getText().toUpperCase(),
                this.numstocksmodify.getText(), this.dateofmodify.getText(),
                this.commissionfessmodify.getText(), "sell");
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      } catch (ParseException e) {
        throw new RuntimeException(e);
      }
    });
    getValueButton.addActionListener(evt -> features.displayDialogPane("getDateVal"));
    computeval.addActionListener(evt -> {
      try {
        features.validateDateVal(this.pfnamevalue.getText(), this.datevalue.getText());
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      } catch (ParseException e) {
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
    retrievePf.addActionListener(evt->features.displayDialogPane("composition"));
    //computecomposition;
  }
}
