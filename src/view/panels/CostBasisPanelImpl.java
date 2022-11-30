package view.panels;

import javax.swing.*;

/**
 * Create cost basis panel for implementing functionalities for cost basis panel.
 */
public class CostBasisPanelImpl implements CostBasisPanel{
  private JPanel costBasisPanel;
  private JButton costBasisButton;
  private JLabel costBasisStatus;

  /**
   * creating cost basis panel.
   */
  public void costBasisPanel(){
    costBasisPanel = new JPanel();
    costBasisPanel.setBorder(BorderFactory.createTitledBorder("Get Cost Basis of portfolio till a" +
            " specific date "));
    costBasisButton = new JButton("Get cost basis");
    costBasisPanel.add(costBasisButton);
    costBasisStatus = new JLabel();
    costBasisPanel.add(costBasisStatus);
  }

  /**
   * Return cost basis panel.
   * @return Jpanel type object for cost basis panel
   */
  public JPanel getcostBasisPanel(){
    return this.costBasisPanel;
  }

  /**
   * Return button for opening cost basis dialog panel.
   * @return Jbutton object for accessing cost basis panel
   */
  public JButton returncostBasisButton(){
    return this.costBasisButton;
  }

  /**
   * Status after clicking the cost basis panel.
   * @return Jlabel type object for getting the status after accessing cost basis panel
   */
  public JLabel returncostBasisStatus(){
    return this.costBasisStatus;
  }

}
