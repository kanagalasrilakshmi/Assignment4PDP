package view.panels;

import javax.swing.*;

/**
 * Interface for accessing retrieve portfolio panel.
 */
public interface retrievePanel {
  /**
   * Creating a Retrieving a portfolio panel.
   */
  void retrievePfPanel();
  /**
   * Return retrieving a portfolio panel.
   * @return Jpanel type object for retrieving a portfolio
   */
  JPanel getRetrievePanel();

  /**
   * Return button for returning portfolio composition.
   * @return Jbutton object for accessing retrieving portfolio panel
   */
  JButton returnretrievePf();

  /**
   * Status after clicking the retrieving portfolio panel.
   * @return Jlabel type object for getting the status after accessing retrieving panel
   */
  JLabel returnretrievePanelStatus();
  void displayRetrievepf();
  JTextArea getPortfoliosListRetrieve();

  JTextArea getPortfolioComposition();

  JTextField getPfnameretrieve();

  JButton getComputecomposition();

  JLabel getRetrieveDialogStatus();

}
