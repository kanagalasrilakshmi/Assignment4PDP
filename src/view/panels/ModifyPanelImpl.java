package view.panels;

import javax.swing.*;

/**
 * Modify panel that implements functionalities for getting modify panel.
 */
public class ModifyPanelImpl implements ModifyPanel{
  private JPanel modifyPanel;
  private JButton getModifyButton;
  private JLabel modifyStatus = new JLabel();

  /**
   * Creating modify panel.
   */
  public void modifyPanel() {
    modifyPanel = new JPanel();
    getModifyButton = new JButton("Modify a Portfolio");
    modifyPanel.setBorder(BorderFactory.createTitledBorder("Modify an existing portfolio by " +
            "purchasing and selling stocks"));
    modifyPanel.add(getModifyButton);
    modifyPanel.add(modifyStatus);
  }

  /**
   * Return modify panel.
   * @return Jpanel for modifying
   */
  public JPanel getModifyPanel(){
    return this.modifyPanel;
  }

  /**
   * Modification of portfolios on this button click.
   * @return Return button for modification of portfolios on this button click.
   */
  public JButton returngetModifyButton(){
    return this.getModifyButton;
  }

  /**
   * Status after getting modifying pane.
   * @return status message after getting modifying pane
   */
  public JLabel getModifyStatus(){
    return this.modifyStatus;
  }
}
