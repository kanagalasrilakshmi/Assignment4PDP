package view;

import javax.swing.*;
/**
 * CLass that implements CreatePanel interface adds create panel, gets create pf button,
 * gets status of the create pane.
 */
public class CreatePanelImpl implements CreatePanel{
  private JPanel createPanel;
  private JButton createPfButton;
  private JLabel createStatus;

  /**
   * Gets create panel.
   */
  public void createPanel() {
    createPanel = new JPanel();
    createPanel.setBorder(BorderFactory.createTitledBorder("Give a valid name for the portfolio " +
            "you want to create. Name should not have spaces, special characters and " +
            "length less than 25 characters."));
    createPfButton = new JButton("Create a New Portfolio");
    createStatus = new JLabel();
    createPanel.add(createPfButton);
    createPanel.add(createStatus);
  }
  /**
   * Returns created create panel.
   * @return Jpanel for create
   */
  public JPanel getcreatePanel(){
    return this.createPanel;
  }
  /**
   * Returns create portfolio button.
   * @return Jbutton for create
   */
  public JButton getCreatePfButton(){
    return this.createPfButton;
  }
  /**
   * Returns status of the create panel
   * @return status of the create panel
   */
  public JLabel getCreateStatus(){
    return this.createStatus;
  }
}
