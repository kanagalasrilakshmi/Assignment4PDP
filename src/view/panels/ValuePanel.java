package view.panels;

import javax.swing.*;

/**
 * Interface that helps to access value panel.
 */
public interface ValuePanel {
  /**
   * Create view panel.
   */
  void valDatePanel();

  /**
   * Return value date panel.
   *
   * @return Jpanel type object for value panel
   */
  JPanel getValueDatePanel();

  /**
   * Return button for opening value dialog panel.
   *
   * @return Jbutton object for value panel
   */
  JButton returnValueButton();

  /**
   * Status after clicking the value panel
   *
   * @return Jlabel type object for getting the status after accessing value panel
   */
  JLabel returnValueStatus();
}
