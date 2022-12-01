package view.panels.dollarexisting;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * Interface for accessing dollar cost averaging on existing  portfolio panel.
 */
public interface Dollarstrategyexist {
  /**
   * Creating panel for applying dollar cost averaging on existing portfolio.
   */
  void dollarCostExisting();

  /**
   * Return dollar cost averaging panel for existing portfolio panel.
   *
   * @return Jpanel type object for dollar cost averaging panel for existing portfolio
   */
  JPanel getDollarCostExisting();

  /**
   * Status after clicking the dollar cost averaging on existing portfolio panel.
   *
   * @return Jlabel object for getting status after accessing dollar cost averaging portfolio panel
   */
  JLabel getDollarExistingStatus();

  /**
   * Return button for applying the dollar cost averaging on existing portfolio.
   *
   * @return Jbutton object for accessing dollar cost averaging on existing portfolio panel
   */
  JButton returndollarCostExisting();

  /**
   * Display the pane that lets users apply dollar strategy on existing flexible portfolios.
   */
  void displayDollarExistingpf();

  /**
   * Status after applying dollar averaging on existing pf.
   *
   * @return Label shown after applying strategy
   */
  JLabel getDollarexistpanestatus();

  /**
   * button for applying dollar averaging strategy on existing pf.
   *
   * @return Jbutton object for applying dollar averaging strategy
   */
  JButton getDollarexistcreate();

  /**
   * Jtext field that takes in commission fees.
   *
   * @return Jtextfield of commission fees
   */
  JTextField getDollarexistcommision();

  /**
   * Jtext field that takes in date when strategy needs to be applied.
   *
   * @return Jtextfield of date when strategy needs to be applied
   */
  JTextField getDollarexistdate();

  /**
   * Jtext field that takes money needs to be put in portfolio.
   *
   * @return Jtextfield of money that needs to be invested
   */
  JTextField getDollarexistval();

  /**
   * Jtextfield that takes in weights in percentages.
   *
   * @return Jtextfield that takes in weights
   */
  JTextField getWeightsexist();

  /**
   * Jtextfield that takes in weights in stocks.
   *
   * @return Jtextfield that takes in stocks
   */
  JTextField getStocksexist();

  /**
   * Jtextfield that takes in portfolio name.
   *
   * @return Jtextfield that takes in pf name
   */
  JTextField getDollarexistpfname();

  /**
   * Jtextfield that takes in strategy to be applied to the given portfolio.
   *
   * @return Jtextfield that takes in name of the strategy to be applied
   */
  JTextField getStratergydollarexistname();

  /**
   * List of all the portfolios stored in the given user path.
   *
   * @return JTextArea object that has list of portfolios stored in the given user path.
   */
  JTextArea getPortfolioslistdollarexist();
}
