package view.panels;

import javax.swing.*;

/**
 * Create view panel for implementing functionalities for creating view panel.
 */
public class ValuePanelImpl implements ValuePanel {
  private JPanel valueDatePanel;
  private JButton getValueButton;
  private final JLabel valueStatus = new JLabel();

  /**
   * Create view panel.
   */
  public void valDatePanel() {
    valueDatePanel = new JPanel();
    getValueButton = new JButton("Get Value of Portfolio on a Specific Date");
    valueDatePanel.setBorder(BorderFactory.createTitledBorder("Get Value of a flexible " +
            "portfolio on a specific date"));
    valueDatePanel.add(getValueButton);
    valueDatePanel.add(valueStatus);
  }

  /**
   * Return value date panel.
   * @return Jpanel type object for value panel
   */
  public JPanel getValueDatePanel(){
    return this.valueDatePanel;
  }

  /**
   * Return button for opening value dialog panel.
   * @return Jbutton object for value panel
   */
  public JButton returnValueButton(){
    return this.getValueButton;
  }

  /**
   * Status after clicking the value panel
   * @return Jlabel type object for getting the status after accessing value panel
   */
  public JLabel returnValueStatus(){
    return this.valueStatus;
  }

}
