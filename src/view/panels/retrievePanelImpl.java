package view.panels;

import javax.swing.*;

/**
 * Create retrieve portfolio panel for implementing functionalities for retrieving a portfolio.
 */
public class retrievePanelImpl implements retrievePanel{
  private JPanel retrievePanel;
  private JLabel retrievePanelStatus;
  private JButton retrievePf = new JButton("Retrieve Portfolio");

  /**
   * Creating a Retrieving a portfolio panel.
   */
  public void retrievePfPanel() {
    retrievePanel = new JPanel();
    retrievePf = new JButton("Retrieve Portfolio");
    retrievePanel.setBorder(BorderFactory.createTitledBorder("Retrieve Composition of a " +
            "flexible portfolio"));
    retrievePanel.add(retrievePf);
    retrievePanelStatus = new JLabel();
    retrievePanel.add(retrievePanelStatus);
  }

  /**
   * Return retrieving a portfolio panel.
   * @return Jpanel type object for retrieving a portfolio
   */
  public JPanel getRetrievePanel(){
    return this.retrievePanel;
  }

  /**
   * Return button for returning portfolio composition.
   * @return Jbutton object for accessing retrieving portfolio panel
   */
  public JButton returnretrievePf(){
    return this.retrievePf;
  }

  /**
   * Status after clicking the retrieving portfolio panel.
   * @return Jlabel type object for getting the status after accessing retrieving panel
   */
  public JLabel returnretrievePanelStatus(){
    return this.retrievePanelStatus;
  }
}
