package view.panels.dollarstarttofinish;

import javax.swing.*;

/**
 * Create panel for implementing functionalities for applying dollar averaging strategy,
 * start-to-finish.
 */
public class DollarstrategynewImpl implements Dollarstrategynew{
  private JPanel dollarPanelNew;
  private JLabel dollarNewStatus;
  private JButton dollarCostNew;
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

  public JButton getDollarnewcreate(){
    return this.dollarnewcreate;
  }
  public JLabel getDollarnewpanestatus(){
    return this.dollarnewpanestatus;
  }
  public JTextField getDollarnewcreatepfname(){
    return this.dollarnewcreatepfname;
  }
  public JTextField getStocksnew(){
    return this.stocksnew;
  }
  public JTextField getWeightsnew(){
    return this.weightsnew;
  }
  public JTextField getDollarnewval(){
    return this.dollarnewval;
  }
  public JTextField getDollarnewdays(){
    return this.dollarnewdays;
  }
  public JTextField getDollarnewenddate(){
    return this.dollarnewenddate;
  }
  public JTextField getDollarnewstartdate(){
    return this.dollarnewstartdate;
  }
  public JTextField getdollarnewcommission(){
    return this.dollarnewcommission;
  }
  public JTextField getStratergydollarnewname(){
    return this.stratergydollarnewname;
  }

  /**
   * Creating panel for applying dollar cost averaging start-to-finish.
   */
  public void dollarCostNew() {
    dollarPanelNew = new JPanel();
    dollarPanelNew.setBorder(BorderFactory.createTitledBorder("Create a start-to-finish dollar " +
            "cost averaging portfolio"));
    dollarCostNew = new JButton("Create dollar cost averaging start-to-finish for a new" +
            "portfolio");
    dollarNewStatus = new JLabel();
    dollarPanelNew.add(dollarCostNew);
    dollarPanelNew.add(dollarNewStatus);
  }

  /**
   * Return dollar cost averaging panel for start-to-finish panel.
   * @return Jpanel type object for dollar cost averaging panel start-to-finish
   */
  public JPanel getdollarCostNew(){
    return this.dollarPanelNew;
  }

  /**
   * Status after clicking the dollar cost averaging start-to-finish panel.
   * @return Jlabel object for getting status after accessing dollar cost start-to-finish panel
   */
  public JLabel getDollarNewStatus(){
    return this.dollarNewStatus;
  }

  /**
   * Return button for applying the dollar cost averaging start-to-finish.
   *
   * @return Jbutton object for accessing dollar cost averaging start-to-finish panel
   */
  public JButton returndollarCostNew(){
    return this.dollarCostNew;
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
}
