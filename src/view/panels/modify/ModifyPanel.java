package view.panels.modify;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

/**
 * Interface that helps to access modify panel.
 */
public interface ModifyPanel {
  /**
   * Creating modify panel.
   */
  void modifyPanel();

  /**
   * Return modify panel.
   *
   * @return Jpanel for modifying
   */
  JPanel getModifyPanel();

  /**
   * Modification of portfolios on this button click.
   *
   * @return Return button for modification of portfolios on this button click.
   */
  JButton returngetModifyButton();

  /**
   * Status after getting modifying pane.
   *
   * @return status message after getting modifying pane
   */
  JLabel getModifyStatus();

  /**
   * Displays modify portfolio dialog pane,
   * lets users modify a flexible portfolio by purchasing and selling,
   * when modify portfolio button is clicked.
   */
  void displayModifyPf();

  /**
   * commission field given to this field.
   *
   * @return commission field
   */
  JTextField getCommissionfessmodify();

  /**
   * num of stocks given to this field for purchase or sell.
   *
   * @return num of stocks field
   */
  JTextField getNumstocksmodify();

  /**
   * input tickr symbol.
   *
   * @return tickr symbol entered
   */
  JTextField getTickrmodify();

  /**
   * return date of purchase or sell to portfolio.
   *
   * @return date of creation
   */
  JTextField getDateofmodify();

  /**
   * return portfolio name field to be modified.
   *
   * @return portfolio name text field
   */
  JTextField getPfnamemodify();

  /**
   * Used for purchasing the stocks.
   *
   * @return Jbutton for making purchase of stocks
   */
  JButton getPurchasemodify();

  /**
   * Used for selling the stocks.
   *
   * @return Jbutton for making selling of stocks
   */
  JButton getSellmodify();

  /**
   * List of portfolios to be modified.
   *
   * @return Jtextarea object of portfolios list
   */
  JTextArea getPortfoliosListModify();

  /**
   * Status after purchasing or selling entries in portfolio.
   *
   * @return status of the modify dialog
   */
  JLabel getModifyDialogStatus();
}
