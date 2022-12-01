package view.panels.valuedate;

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

  /**
   * Display the value portfolio dialog pane,
   * lets users get the value of a flexible portfolio on a specific date.
   */
  void displayValuepf();
  JTextArea getPortfoliosListVal();
  JButton getComputeval();
  JLabel getValDialogStatus();
  JTextField getDatevalue();
  JTextField getPfnamevalue();
}
