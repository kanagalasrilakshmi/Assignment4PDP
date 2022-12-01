package view.panels.create;

import javax.swing.*;

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
   * @return Jpanel for create
   */
  JPanel getcreatePanel();

  /**
   * Returns create portfolio button.
   * @return Jbutton for create
   */
  JButton getCreatePfButton();

  /**
   * Returns status of the create panel
   * @return status of the create panel
   */
  JLabel getCreateStatus();

  /**
   * Displays the create portfolio dialog pane that lets users create a flexible portfolio,
   * when create portfolio button is clicked.
   */
  void displayCreatePf();
  JTextField getTickrcreate();
  JTextField getDateofcreation();
  JTextField getPfnamecreate();
  JTextField getNumstockscreate();
  JTextField getCommissionfeescreate();
  JLabel getCreateDialogStatus();
  JButton getSaveCreate();
  JButton getAddCreate();
}
