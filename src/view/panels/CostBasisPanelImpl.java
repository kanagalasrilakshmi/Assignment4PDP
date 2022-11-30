package view.panels;

import javax.swing.*;

public class CostBasisPanelImpl implements CostBasisPanel{
  private JButton getCostBasis;
  private JPanel costBasisPanel;
  private JButton costBasisButton;
  private JLabel costBasisStatus;
  public void getCostBasisPanel(){
    getCostBasis = new JButton("Compute Cost Basis");
    costBasisPanel = new JPanel();
    costBasisPanel.setBorder(BorderFactory.createTitledBorder("Get Cost Basis of portfolio till a" +
            " specific date "));
    costBasisButton = new JButton("Get cost basis");
    costBasisPanel.add(costBasisButton);
    costBasisStatus = new JLabel();
    costBasisPanel.add(costBasisStatus);
  }

}
