package view.panels.costbasis;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

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
   *
   * @return Jpanel type object for cost basis panel
   */
  JPanel getcostBasisPanel();

  /**
   * Return button for opening cost basis dialog panel.
   *
   * @return Jbutton object for accessing cost basis panel
   */
  JButton returncostBasisButton();

  /**
   * Status after clicking the cost basis panel
   *
   * @return Jlabel type object for getting the status after accessing cost basis panel
   */
  JLabel returncostBasisStatus();

  /**
   * When get cost basis button is clicked in the main panel,
   * this opens the dialog pane that would let user,
   * to enter required entries to compute cost basis of a portfolio.
   */
  void displayCostBasis();

  /**
   * Return JTextfield portfolio name.
   *
   * @return Jtext field type pf name
   */
  JTextField getPfNameCostBasis();

  /**
   * Return date Jtext field.
   *
   * @return JText field date field
   */
  JTextField getDateCostBasis();

  /**
   * List of all the portfolios in the given path.
   *
   * @return JTextArea type portfolios
   */
  JTextArea getPortfoliosListBasis();

  /**
   * gives cost basis when clicked.
   *
   * @return Jbutton to compute cost basis
   */
  JButton getCostBasisButton();

  /**
   * gives the cost basis comuted upon a button click.
   *
   * @return label that records the cost basis value
   */
  JLabel getCostBasisDialogStatus();
}
