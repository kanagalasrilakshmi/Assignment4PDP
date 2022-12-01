package view.panels;

import javax.swing.*;

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
   * @return Jpanel for modifying
   */
  JPanel getModifyPanel();
  /**
   * Modification of portfolios on this button click.
   * @return Return button for modification of portfolios on this button click.
   */
  JButton returngetModifyButton();
  /**
   * Status after getting modifying pane.
   * @return status message after getting modifying pane
   */
  JLabel getModifyStatus();

  /**
   * Displays modify portfolio dialog pane,
   * lets users modify a flexible portfolio by purchasing and selling,
   * when modify portfolio button is clicked.
   */
  void displayModifyPf();
  JTextField getCommissionfessmodify();
  JTextField getNumstocksmodify();
  JTextField getTickrmodify();
  JTextField getDateofmodify();
  JTextField getPfnamemodify();
  JButton getPurchasemodify();
  JButton getSellmodify();
  JTextArea getPortfoliosListModify();
  JLabel getModifyDialogStatus();
}
