package view.panels.dollarstarttofinish;

import javax.swing.*;

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
  JTextField getDollarnewcreatepfname();
  JTextField getStocksnew();
  JTextField getWeightsnew();
  JTextField getDollarnewval();
  JTextField getDollarnewdays();
  JTextField getDollarnewenddate();
  JTextField getDollarnewstartdate();
  JTextField getdollarnewcommission();
  JTextField getStratergydollarnewname();
  JButton getDollarnewcreate();
  JLabel getDollarnewpanestatus();

}
