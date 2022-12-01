package view.panels;

import javax.swing.*;

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
  JLabel getDollarexistpanestatus();
  JButton getDollarexistcreate();
  JTextField getDollarexistcommision();
  JTextField getDollarexistdate();
  JTextField getDollarexistval();
  JTextField getWeightsexist();
  JTextField getStocksexist();
  JTextField getDollarexistpfname();
  JTextField getStratergydollarexistname();
  JTextArea getPortfolioslistdollarexist();
}
