package view;

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
}
