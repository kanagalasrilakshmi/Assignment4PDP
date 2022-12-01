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

  /**
   * List of portfolios for which value needs to be computed.
   *
   * @return Jtextarea object of portfolios list
   */
  JTextArea getPortfoliosListVal();

  /**
   * gives the value computed upon a button click.
   *
   * @return label that records the value on a date
   */
  JButton getComputeval();

  /**
   * gives the value computed upon a button click.
   *
   * @return label that records the value on a date
   */
  JLabel getValDialogStatus();

  /**
   * Return date Jtext field on which value needs to be calculated.
   *
   * @return JText field date field
   */
  JTextField getDatevalue();

  /**
   * Return JTextfield portfolio name.
   *
   * @return Jtext field type pf name
   */
  JTextField getPfnamevalue();
}
