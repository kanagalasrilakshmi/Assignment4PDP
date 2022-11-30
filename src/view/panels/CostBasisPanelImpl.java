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
   *
   * @return
   */
  public JPanel getcostBasisPanel(){
    return this.costBasisPanel;
  }

  /**
   *
   * @return
   */
  public JButton returncostBasisButton(){
    return this.costBasisButton;
  }

  /**
   *
   * @return
   */
  public JLabel returncostBasisStatus(){
    return this.costBasisStatus;
  }

}
