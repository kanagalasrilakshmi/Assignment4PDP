package view.panels.retrievepf;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * Interface for accessing retrieve portfolio panel.
 */
public interface RetrievePanel {
  /**
   * Creating a Retrieving a portfolio panel.
   */
  void retrievePfPanel();

  /**
   * Return retrieving a portfolio panel.
   *
   * @return Jpanel type object for retrieving a portfolio
   */
  JPanel getRetrievePanel();

  /**
   * Return button for returning portfolio composition.
   *
   * @return Jbutton object for accessing retrieving portfolio panel
   */
  JButton returnretrievePf();

  /**
   * Status after clicking the retrieving portfolio panel.
   *
   * @return Jlabel type object for getting the status after accessing retrieving panel
   */
  JLabel returnretrievePanelStatus();

  /**
   * Display the composition of the portfolio pane,
   * gives the portfolio composition on a given date for a portfolio.
   */
  void displayRetrievepf();

  /**
   * List of portfolios to be retrieved.
   *
   * @return Jtextarea object of portfolios list
   */
  JTextArea getPortfoliosListRetrieve();

  /**
   * List of all entries, stock price, date of purchase in the portfolio.
   *
   * @return JText area object consisting of portfolio composition
   */
  JTextArea getPortfolioComposition();

  /**
   * return portfolio name field to get composition for.
   *
   * @return portfolio name text field
   */
  JTextField getPfnameretrieve();

  /**
   * Helps to compute composition of the portfolio.
   *
   * @return Jbutton object that helps to compute composition
   */
  JButton getComputecomposition();

  /**
   * Status after retrieving the portfolio.
   *
   * @return status of the modify dialog
   */
  JLabel getRetrieveDialogStatus();

}
