package view.panels.dollarexisting;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 * Create panel for implementing functionalities for applying dollar averaging strategy,
 * on existing portfolio.
 */
public class DollarstrategyexistImpl implements Dollarstrategyexist {
  private JPanel dollarPanelExisting;
  private JButton dollarCostExisting;
  private JLabel dollarExistingStatus;
  private final JTextArea portfolioslistdollarexist = new JTextArea();
  private JTextField stratergydollarexistname;
  private JTextField dollarexistpfname;
  private JTextField stocksexist;
  private JTextField weightsexist;
  private JTextField dollarexistval;
  private JTextField dollarexistdate;
  private JTextField dollarexistcommision;
  private final JButton dollarexistcreate = new JButton("Create");
  private final JLabel dollarexistpanestatus = new JLabel();

  /**
   * Status after applying dollar averaging on existing pf.
   *
   * @return Label shown after applying strategy
   */
  public JLabel getDollarexistpanestatus() {
    return dollarexistpanestatus;
  }

  /**
   * button for applying dollar averaging strategy on existing pf.
   *
   * @return Jbutton object for applying dollar averaging strategy
   */
  public JButton getDollarexistcreate() {
    return dollarexistcreate;
  }

  /**
   * Jtext field that takes in commission fees.
   *
   * @return Jtextfield of commission fees
   */
  public JTextField getDollarexistcommision() {
    return dollarexistcommision;
  }

  /**
   * Jtext field that takes in date when strategy needs to be applied.
   *
   * @return Jtextfield of date when strategy needs to be applied
   */
  public JTextField getDollarexistdate() {
    return dollarexistdate;
  }

  /**
   * Jtext field that takes money needs to be put in portfolio.
   *
   * @return Jtextfield of money that needs to be invested
   */
  public JTextField getDollarexistval() {
    return dollarexistval;
  }

  /**
   * Jtextfield that takes in weights in percentages.
   *
   * @return Jtextfield that takes in weights
   */
  public JTextField getWeightsexist() {
    return weightsexist;
  }

  /**
   * Jtextfield that takes in weights in stocks.
   *
   * @return Jtextfield that takes in stocks
   */
  public JTextField getStocksexist() {
    return stocksexist;
  }

  /**
   * Jtextfield that takes in portfolio name.
   *
   * @return Jtextfield that takes in pf name
   */
  public JTextField getDollarexistpfname() {
    return this.dollarexistpfname;
  }

  /**
   * Jtextfield that takes in strategy to be applied to the given portfolio.
   *
   * @return Jtextfield that takes in name of the strategy to be applied
   */
  public JTextField getStratergydollarexistname() {
    return this.stratergydollarexistname;
  }

  /**
   * List of all the portfolios stored in the given user path.
   *
   * @return JTextArea object that has list of portfolios stored in the given user path.
   */
  public JTextArea getPortfolioslistdollarexist() {
    return this.portfolioslistdollarexist;
  }

  /**
   * Creating panel for applying dollar cost averaging on existing portfolio.
   */
  public void dollarCostExisting() {
    dollarPanelExisting = new JPanel();
    dollarCostExisting = new JButton("Calculate dollar cost averaging for existing portfolio");
    dollarPanelExisting.setBorder(BorderFactory.createTitledBorder("Create dollar cost " +
            "averaging for existing flexible portfolios"));
    dollarExistingStatus = new JLabel();
    dollarPanelExisting.add(dollarCostExisting);
    dollarPanelExisting.add(dollarExistingStatus);
  }

  /**
   * Return dollar cost averaging panel for existing portfolio panel.
   *
   * @return Jpanel type object for dollar cost averaging panel for existing portfolio
   */
  public JPanel getDollarCostExisting() {
    return this.dollarPanelExisting;
  }

  /**
   * Status after clicking the dollar cost averaging on existing portfolio panel.
   *
   * @return Jlabel object for getting status after accessing dollar cost averaging portfolio panel
   */
  public JLabel getDollarExistingStatus() {
    return this.dollarExistingStatus;
  }

  /**
   * Return button for applying the dollar cost averaging on existing portfolio.
   *
   * @return Jbutton object for accessing dollar cost averaging on existing portfolio panel
   */
  public JButton returndollarCostExisting() {
    return this.dollarCostExisting;
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
}
