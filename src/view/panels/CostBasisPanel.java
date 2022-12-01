package view.panels;

import javax.swing.*;

/**
 * Interface that helps to cost basis panel.
 */
public interface CostBasisPanel {
  /**
   * creating cost basis panel.
   */
  void costBasisPanel();
  /**
   * Return cost basis panel
   * @return Jpanel type object for cost basis panel
   */
  JPanel getcostBasisPanel();
  /**
   *Return button for opening cost basis dialog panel.
   * @return Jbutton object for accessing cost basis panel
   */
  JButton returncostBasisButton();
  /**
   * Status after clicking the cost basis panel
   * @return Jlabel type object for getting the status after accessing cost basis panel
   */
  JLabel returncostBasisStatus();
  void displayCostBasis();
  JTextField getPfNameCostBasis();

  JTextField getDateCostBasis();

  JTextArea getPortfoliosListBasis();

  JButton getCostBasisButton();

  JLabel getCostBasisDialogStatus();
}
