package view.panels.userpath;

import javax.swing.*;

/**
 * Class for creating user panel.
 */
public class UserPanelImpl implements UserPanel {
  private JPanel userPanel;
  private JButton inputButton;
  private JLabel pathStore;

  /**
   * Creates user panel.
   */
  public void getUserPanel() {
    userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("Give a valid input path where you want " +
            "to store your portfolios. For example: /Users/PDP/PortfolioBucket/"));
    inputButton = new JButton("Create User Path");
    userPanel.add(inputButton);
    pathStore = new JLabel();
    userPanel.add(pathStore);
  }

  /**
   * Return the user panel.
   * @return user panel of type Jpanel
   */
  public JPanel returnUserPanel(){
    return this.userPanel;
  }

  /**
   * input button for clicking the user panel.
   * @return Jbutton type that takes in input
   */
  public JButton returninputButton(){
    return this.inputButton;
  }

  /**
   * path for storing the portfolios.
   * @return Jlabel that return the path where portfolios are to be stored
   */
  public JLabel returnPathStore(){
    return this.pathStore;
  }
}
