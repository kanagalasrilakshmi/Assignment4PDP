package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUIViewImpl extends JFrame implements GUIView {
  private JLabel pathStore;
  private JLabel createStatus;
  private JLabel modifyStatus = new JLabel();
  private JLabel valueStatus = new JLabel();
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
  private JButton getCostBasis;
  private JButton createPf;
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
  private JButton add = new JButton("Add");
  private JButton save = new JButton("Save");

  // fields for modify.
  private JTextField pfnamemodify;
  private JTextField dateofmodify;
  private JTextField tickrmodify;
  private JTextField numstocksmodify;
  private JTextField commissionfessmodify;
  private JButton purchase = new JButton("Purchase");
  private JButton sell = new JButton("Sell");

  // fields for value.
  private JTextField pfnamevalue;
  private JTextField datevalue;
  private JButton computeval = new JButton("Compute Value of Portfolio");


  private JLabel createDialogStatus = new JLabel();
  private JLabel modifyDialogStatus = new JLabel();
  private JLabel valDialogStatus = new JLabel();
  private JLabel costBasisDialogStatus = new JLabel();

  public GUIViewImpl() {
    super();
    finalPanel();
  }

  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    inputButton.addActionListener(actionEvent);
    getCostBasis.addActionListener(actionEvent);
    costBasisButton.addActionListener(actionEvent);
    createPfButton.addActionListener(actionEvent);
    add.addActionListener(actionEvent);
    save.addActionListener(actionEvent);
    getModifyButton.addActionListener(actionEvent);
    purchase.addActionListener(actionEvent);
    sell.addActionListener(actionEvent);
    getValueButton.addActionListener(actionEvent);
    computeval.addActionListener(actionEvent);
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
    add.setActionCommand("Add");
    save.setActionCommand("Save");
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

  public String getCreatePfValue(){
    return this.pfnamecreate.getText();
  }

  public void setCreatePfValue(String message){
    this.pfnamecreate.setText(message);
  }
  public String getdateofcreationValue(){
    return this.dateofcreation.getText();
  }

  public void setdateofcreationValue(String message){
    this.dateofcreation.setText(message);
  }

  public String getnumstockscreateValue(){
    return this.numstockscreate.getText();
  }

  public void setnumstockscreateValue(String message){
    this.numstockscreate.setText(message);
  }

  public String gettickrcreateValue(){
    return this.tickrcreate.getText();
  }

  public void settickrcreateValue(String message){
    this.tickrcreate.setText(message);
  }

  public String getcommissionfeescreateValue(){
    return this.commissionfeescreate.getText();
  }

  public void setcommissionfeescreateValue(String message){
    this.commissionfeescreate.setText(message);
  }

  public String getModifyPfValue(){
    return this.pfnamemodify.getText();
  }

  public void setModifyPfValue(String message){
    this.pfnamemodify.setText(message);
  }

  public String getdateofmodifynValue(){
    return this.dateofmodify.getText();
  }

  public void setdateofmodifynValue(String message){
    this.dateofmodify.setText(message);
  }

  public String getnumstocksmodifyValue(){
    return this.numstocksmodify.getText();
  }

  public void setnumstocksmodifyValue(String message){
    this.numstocksmodify.setText(message);
  }

  public String gettickrmodifyValue(){
    return this.tickrmodify.getText();
  }

  public void settickrmodifyValue(String message){
    this.tickrmodify.setText(message);
  }

  public String getcommissionfeesmodifyValue(){
    return this.commissionfessmodify.getText();
  }

  public void setcommissionfeesmodifyValue(String message){
    this.commissionfessmodify.setText(message);
  }

  public void setcreateDialogStatus(String message){
    createDialogStatus.setText(message);
  }

  public void setmodifyDialogStatus(String message){
    modifyDialogStatus.setText(message);
  }
  public void setvalueDialogStatus(String message){
    valDialogStatus.setText(message);
  }

  public void setCostBasisDialogStatus(String message){
    costBasisDialogStatus.setText(message);
  }

  private JPanel getCostBasisPanelDialog() {
    JPanel costBasisDialog = new JPanel();
    pfName = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name:");
    date = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute cost basis:");
    getCostBasis.setActionCommand("Compute Cost Basis");
    costBasisDialog.setLayout(new BoxLayout(costBasisDialog, BoxLayout.Y_AXIS));
    costBasisDialog.add(pfNameLabel);
    costBasisDialog.add(pfName);
    costBasisDialog.add(dateLabel);
    costBasisDialog.add(date);
    costBasisDialog.add(getCostBasis);
    costBasisDialog.add(costBasisDialogStatus);
    return costBasisDialog;
  }

  private JPanel getModifyPanelDialog() {
    JPanel modifyDialog = new JPanel();
    pfnamemodify = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio name to be modified: ");
    dateofmodify = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase/sell: ");
    tickrmodify = new JTextField(25);
    JLabel tickrlabel = new JLabel("Enter tickr symbol: ");
    numstocksmodify = new JTextField(25);
    JLabel numstockslabel = new JLabel("Enter the number of stocks to be purchased/sold: ");
    commissionfessmodify = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees: ");
    modifyDialog.setLayout(new BoxLayout(modifyDialog, BoxLayout.Y_AXIS));
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
    pfnamevalue = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name: ");
    datevalue = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute value of the portfolio:");
    computeval.setActionCommand("Compute Value of Pf");
    valDialog.setLayout(new BoxLayout(valDialog, BoxLayout.Y_AXIS));
    valDialog.add(pfNameLabel);
    valDialog.add(pfnamevalue);
    valDialog.add(dateLabel);
    valDialog.add(datevalue);
    valDialog.add(computeval);
    valDialog.add(valDialogStatus);
    return valDialog;
  }

  public String getpfnameVal(){
    return pfnamevalue.getText();
  }

  public void setpfnameVal(String message){
    pfnamevalue.setText(message);
  }

  public String getdateVal(){
    return datevalue.getText();
  }

  public void setdateVal(String message){
    this.datevalue.setText(message);
  }
  public String pfNameCostBasis() {
    return pfName.getText();
  }

  public void setpfNameCostBasis(String message){
    pfName.setText(message);
  }

  public String getDate() {
    return date.getText();
  }

  public void setDate(String message){
    date.setText(message);
  }

  public void setLabelCostBasisStatus(String message) {
    costBasisStatus.setText(message);
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
    dialog = optionPane.createDialog("Modify Portfolio");
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
    inputButton.setActionCommand("Input");
    userPanel.add(inputButton);
    pathStore = new JLabel();
    userPanel.add(pathStore);
  }

  private void getCreatePanel() {
    createPanel = new JPanel();
    createPanel.setBorder(BorderFactory.createTitledBorder("Create Flexible Portfolio"));
    createPfButton = new JButton("Create a New Portfolio");
    createPfButton.setActionCommand("Create Portfolio");
    createStatus = new JLabel();
    createPanel.add(createPfButton);
    createPanel.add(createStatus);
  }

  public void setCreateLabelStatus(String message){
    this.createStatus.setText(message);
  }

  public void setModifyLabelStatus(String message){
    this.modifyStatus.setText(message);
  }

  public void setValueLabelStatus(String message){
    this.valueStatus.setText(message);
  }

  private void getModifyPanel() {
    modifyPanel = new JPanel();
    getModifyButton = new JButton("Modify a Portfolio");
    modifyPanel.setBorder(BorderFactory.createTitledBorder("Modify a specific Portfolio"));
    getModifyButton.setActionCommand("Modify Portfolio");
    modifyPanel.add(getModifyButton);
    modifyPanel.add(modifyStatus);
  }

  private void getValueDatePanel() {
    valueDatePanel = new JPanel();
    getValueButton = new JButton("Get Value of Portfolio on a Specific Date");
    valueDatePanel.setBorder(BorderFactory.createTitledBorder("Get Value of  portfolio on a " +
            "specific date"));
    getValueButton.setActionCommand("Get Value");
    valueDatePanel.add(getValueButton);
    valueDatePanel.add(valueStatus);
  }

  private void getCostBasisPanel() {
    getCostBasis = new JButton("Compute Cost Basis");
    costBasisPanel = new JPanel();
    costBasisPanel.setBorder(BorderFactory.createTitledBorder("Get Cost Basis and value of a " +
            "portfolio"));
    costBasisButton = new JButton("Get cost basis");
    costBasisButton.setActionCommand("Get Cost Basis");
    costBasisPanel.add(costBasisButton);
    costBasisStatus = new JLabel();
    costBasisPanel.add(costBasisStatus);
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
    getDollarCostExisting();
    getDollarCostNew();
    getQuitPanel();
    mainJPanel.add(userPanel);
    mainJPanel.add(createPanel);
    mainJPanel.add(modifyPanel);
    mainJPanel.add(costBasisPanel);
    mainJPanel.add(valueDatePanel);
    mainJPanel.add(dollarPanelExisting);
    mainJPanel.add(dollarPanelNew);
    mainJPanel.add(quitPanel);
    add(mainJPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 1000);
    setLocationRelativeTo(null);
  }

}
