package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.ControllerImplGUI;

public class GUIViewImpl extends JFrame implements GUIView {
  private JLabel pathStore;
  private JLabel createStatus;
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

  private JLabel costBasisResult;

  private JTextField pfName;
  private JTextField date;

  // fields for create.
  private JTextField pfnamecreate;
  private JTextField dateofcreation;
  private JTextField tickrcreate;
  private JTextField numstockscreate;
  private JTextField commissionfeescreate;
  private JButton add;
  private JButton save;

  // fields for modify.
  private JTextField pfnamemodify;
  private JTextField dateofmodify;
  private JTextField tickrmodify;
  private JTextField numstocksmodify;
  private JTextField commissionfessmodify;
  private JButton purchase;
  private JButton sell;

  // fields for value.
  private JTextField pfnamevalue;
  private JTextField datevalue;
  private JButton computeval;

  // fields for cost basis.
  private JTextField pfnamebasis;
  private JTextField datebasis;
  private JTextField computebasis;



  private JLabel setCostBasis = new JLabel();
  public GUIViewImpl (){
    super();
    finalPanel();
  }
  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    inputButton.addActionListener(actionEvent);
    getCostBasis.addActionListener(actionEvent);
    costBasisButton.addActionListener(actionEvent);
    createPfButton.addActionListener(actionEvent);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public void setpathStore(String setMessage){
    pathStore.setText(setMessage);
  }

  public void setCostBasisResult(String message){
    costBasisResult.setText(message);
  }

  private JPanel getCreatePfDialog(){
    JPanel createPanelDialog = new JPanel();
    pfnamecreate = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name to be created: ");
    dateofcreation = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase in YYYY-MM-DD format: ");
    JLabel numstocksLabel = new JLabel("Enter number of stocks to purchase: ");
    numstockscreate = new JTextField(25);
    JLabel tickrLabel = new JLabel("Enter Tickr symbol: ");
    tickrcreate = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees: ");
    commissionfeescreate = new JTextField(25);
    add = new JButton("Add");
    save = new JButton("Save");
    add.setActionCommand("Add");
    save.setActionCommand("Save");
    createPanelDialog.setLayout(new BoxLayout(createPanelDialog,BoxLayout.Y_AXIS));
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
    return createPanelDialog;
  }
  private JPanel getCostBasisPanelDialog(){
    JPanel costBasisDialog = new JPanel();
    pfName = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name:");
    date = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute cost basis:");
    getCostBasis.setActionCommand("Compute Cost Basis");
    costBasisDialog.setLayout(new BoxLayout(costBasisDialog,BoxLayout.Y_AXIS));
    costBasisDialog.add(pfNameLabel);
    costBasisDialog.add(pfName);
    costBasisDialog.add(dateLabel);
    costBasisDialog.add(date);
    costBasisDialog.add(getCostBasis);
    costBasisDialog.add(setCostBasis);
    return costBasisDialog;
  }

  public String pfNameCostBasis(){
    return pfName.getText();
  }

  public String getDate(){
    return date.getText();
  }

  public void setLabelCostBasis(String message){
    setCostBasis.setText(message);
  }

  public void displayCostBasis(){
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCostBasisPanelDialog(),
            JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
            new Object[] {}, null);
    dialog = optionPane.createDialog("Get Cost Basis");
    dialog.setVisible(true);
  }
  public void displayCreatePf(){
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCreatePfDialog(),JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[] {}, null);
    dialog = optionPane.createDialog("Create Portfolio");
    dialog.setVisible(true);
  }

  private void getMainPanel(){
    mainJPanel = new JPanel();
    mainJPanel.setLayout(new BoxLayout(mainJPanel,BoxLayout.Y_AXIS));
  }
  private void getUserPanel(){
    userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("User Path"));
    inputButton = new JButton("Create User Path");
    inputButton.setActionCommand("Input");
    userPanel.add(inputButton);
    pathStore = new JLabel();
    userPanel.add(pathStore);
  }
  private void getCreatePanel(){
    createPanel = new JPanel();
    createPanel.setBorder(BorderFactory.createTitledBorder("Create Flexible Portfolio"));
    createPfButton = new JButton("Create a New Portfolio");
    createPfButton.setActionCommand("Create Portfolio");
    createStatus = new JLabel();
    createPanel.add(createPfButton);
  }
  private void getModifyPanel(){
    modifyPanel = new JPanel();
    getModifyButton = new JButton("Modify a Portfolio");
    modifyPanel.setBorder(BorderFactory.createTitledBorder("Modify a specific Portfolio"));
    getModifyButton.setActionCommand("Modify Portfolio");
    modifyPanel.add(getModifyButton);

  }
  private void getValueDatePanel(){
    valueDatePanel = new JPanel();
    getValueButton = new JButton("Get Value of Portfolio on a Specific Date");
    valueDatePanel.setBorder(BorderFactory.createTitledBorder("Get Value of  portfolio on a " +
            "specific date"));
    getValueButton.setActionCommand("Get Value");
    valueDatePanel.add(getValueButton);
  }
  private void getCostBasisPanel(){
    getCostBasis = new JButton("Compute Cost Basis");
    costBasisPanel = new JPanel();
    costBasisPanel.setBorder(BorderFactory.createTitledBorder("Get Cost Basis and value of a " +
            "portfolio"));
    costBasisButton = new JButton("Get cost basis");
    costBasisButton.setActionCommand("Get Cost Basis");
    costBasisPanel.add(costBasisButton);
    costBasisResult = new JLabel();
    costBasisPanel.add(costBasisResult);
  }

  private void getQuitPanel(){
    quitPanel = new JPanel();
    quitPanel.setBorder(BorderFactory.createTitledBorder("Quit the stocks program"));
    quitButton = new JButton("Quit the Program");
    quitButton.addActionListener((ActionEvent e)->{
      System.exit(0);
    });
    quitPanel.add(quitButton);
  }
  private void getDollarCostExisting(){
    dollarPanelExisting = new JPanel();
    dollarCostExisting = new JButton("Calculate dollar cost averaging for existing portfolio");
    dollarPanelExisting.setBorder(BorderFactory.createTitledBorder("Create dollar cost " +
            "averaging for existing flexible portfolios"));
    dollarCostExisting.setActionCommand("Calculate dollar cost");
    dollarPanelExisting.add(dollarCostExisting);
  }
  private void getDollarCostNew(){
    dollarPanelNew = new JPanel();
    dollarPanelNew.setBorder(BorderFactory.createTitledBorder("Create a start-to-finish dollar " +
            "cost averaging portfolio"));
    dollarCostNew = new JButton("Create dollar cost averaging start-to-finish for a new" +
            "portfolio");
    dollarCostNew.setActionCommand("Start-to-Finish");
    dollarPanelNew.add(dollarCostNew);
  }

  private void finalPanel(){
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
    setSize(1000,1000);
    setLocationRelativeTo(null);
  }

}
