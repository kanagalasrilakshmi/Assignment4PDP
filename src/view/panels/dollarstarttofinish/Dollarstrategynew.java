package view.panels.dollarstarttofinish;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public interface Dollarstrategynew {
  /**
   * Creating panel for applying dollar cost averaging start-to-finish.
   */
  void dollarCostNew();

  /**
   * Return dollar cost averaging panel for start-to-finish panel.
   *
   * @return Jpanel type object for dollar cost averaging panel start-to-finish
   */
  JPanel getdollarCostNew();

  /**
   * Status after clicking the dollar cost averaging start-to-finish panel.
   *
   * @return Jlabel object for getting status after accessing dollar cost start-to-finish panel
   */
  JLabel getDollarNewStatus();

  /**
   * Return button for applying the dollar cost averaging start-to-finish.
   *
   * @return Jbutton object for accessing dollar cost averaging start-to-finish panel
   */
  JButton returndollarCostNew();

  /**
   * Display the pane that lets users apply start-to-finish dollar averaging strategy.
   */
  void displayDollarNewpf();

  /**
   * Jtextfield that takes in portfolio name.
   *
   * @return Jtextfield that takes in pf name
   */
  JTextField getDollarnewcreatepfname();

  /**
   * Jtextfield that takes in weights in stocks.
   *
   * @return Jtextfield that takes in stocks
   */
  JTextField getStocksnew();

  /**
   * Jtextfield that takes in weights in percentages.
   *
   * @return Jtextfield that takes in weights
   */
  JTextField getWeightsnew();

  /**
   * Jtext field that takes money needs to be put in portfolio.
   *
   * @return Jtextfield of money that needs to be invested
   */
  JTextField getDollarnewval();

  /**
   * Jtext field that takes in number of days after number of days after which transaction,
   * needs to recur.
   *
   * @return Jtextfield of money that needs to be invested
   */
  JTextField getDollarnewdays();

  /**
   * Jtext field that takes in start date when strategy needs to be applied.
   *
   * @return Jtextfield of date when strategy needs to be applied
   */
  JTextField getDollarnewenddate();

  /**
   * Jtext field that takes in end date when strategy needs to be applied.
   *
   * @return Jtextfield of date when strategy needs to be applied
   */
  JTextField getDollarnewstartdate();

  /**
   * Jtext field that takes in commission fees.
   *
   * @return Jtextfield of commission fees
   */
  JTextField getdollarnewcommission();

  /**
   * Jtextfield that takes in strategy to be applied to the given portfolio.
   *
   * @return Jtextfield that takes in name of the strategy to be applied
   */
  JTextField getStratergydollarnewname();

  /**
   * Return button for applying the dollar cost averaging start-to-finish.
   *
   * @return Jbutton object for accessing dollar cost averaging start-to-finish
   */
  JButton getDollarnewcreate();

  /**
   * Status after applying dollar averaging start-to-finish.
   *
   * @return label shown after applying strategy
   */
  JLabel getDollarnewpanestatus();

}
