package view.panels.create;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Interface that adds create panel, gets create pf button, gets status of the create pane.
 */
public interface CreatePanel {
  /**
   * Gets create panel.
   */
  void createPanel();

  /**
   * Returns created create panel.
   *
   * @return Jpanel for create
   */
  JPanel getcreatePanel();

  /**
   * Returns create portfolio button.
   *
   * @return Jbutton for create
   */
  JButton getCreatePfButton();

  /**
   * Returns status of the create panel.
   *
   * @return status of the create panel
   */
  JLabel getCreateStatus();

  /**
   * Displays the create portfolio dialog pane that lets users create a flexible portfolio,
   * when create portfolio button is clicked.
   */
  void displayCreatePf();

  /**
   * input tickr symbol.
   *
   * @return tickr symbol entered
   */
  JTextField getTickrcreate();

  /**
   * return date of creation while adding entries to portfolio.
   *
   * @return date of creation
   */
  JTextField getDateofcreation();

  /**
   * return portfolio name field.
   *
   * @return portfolio name text field
   */
  JTextField getPfnamecreate();

  /**
   * num of stocks given to this field.
   *
   * @return num of stocks field
   */
  JTextField getNumstockscreate();

  /**
   * commission field given to this field.
   *
   * @return commission field
   */
  JTextField getCommissionfeescreate();

  /**
   * Status after saving or adding entries in portfolio.
   *
   * @return status of the create dialog
   */
  JLabel getCreateDialogStatus();

  /**
   * Button to save entries to the portfolio.
   *
   * @return save button
   */
  JButton getSaveCreate();

  /**
   * Button to add entries in portfolio.
   *
   * @return add button
   */
  JButton getAddCreate();
}
