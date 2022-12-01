package view.panels.costbasis;

import javax.swing.*;
import java.awt.*;

/**
 * Create cost basis panel for implementing functionalities for cost basis panel.
 */
public class CostBasisPanelImpl implements CostBasisPanel{
  private JPanel costBasisPanel;
  private JButton costBasisButton;
  private JLabel costBasisStatus;

  private JTextField pfName;
  private JTextField date;
  private final JTextArea portfoliosListBasis = new JTextArea();
  private final JButton getCostBasis = new JButton("Compute Cost Basis");
  private final JLabel costBasisDialogStatus = new JLabel();

  /**
   * Return JTextfield portfolio name.
   * @return Jtext field type pf name
   */
  public JTextField getPfNameCostBasis(){
    return this.pfName;
  }

  /**
   * Return date Jtext field.
   * @return JText field date field
   */
  public JTextField getDateCostBasis(){
    return this.date;
  }

  /**
   * List of all the portfolios in the given path.
   * @return  JTextArea type portfolios
   */
  public JTextArea getPortfoliosListBasis(){
    return this.portfoliosListBasis;
  }

  /**
   * gives cost basis when clicked.
   * @return Jbutton to compute cost basis
   */
  public JButton getCostBasisButton(){
    return this.getCostBasis;
  }

  /**
   * gives the cost basis comuted upon a button click.
   *
   * @return label that records the cost basis value
   */
  public JLabel getCostBasisDialogStatus(){
    return this.costBasisDialogStatus;
  }

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
  /**
   * When get cost basis button is clicked in the main panel,
   * this opens the dialog pane that would let user,
   * to enter required entries to compute cost basis of a portfolio.
   */
  public void displayCostBasis() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCostBasisPanelDialog(),
            JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
            new Object[]{}, null);
    dialog = optionPane.createDialog("Get Cost Basis");
    dialog.setVisible(true);
  }

  private JPanel getCostBasisPanelDialog() {
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListBasis.setEditable(false);
    JPanel costBasisDialog = new JPanel();
    pfName = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name:");
    date = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute cost basis:");
    costBasisDialog.setLayout(new BoxLayout(costBasisDialog, BoxLayout.Y_AXIS));
    costBasisDialog.setPreferredSize(new Dimension(700, 700));
    costBasisDialog.setMaximumSize(new Dimension(900, 500));
    costBasisDialog.setMinimumSize(new Dimension(400, 400));
    costBasisDialog.add(listPortfolios);
    costBasisDialog.add(portfoliosListBasis);
    costBasisDialog.add(pfNameLabel);
    costBasisDialog.add(pfName);
    costBasisDialog.add(dateLabel);
    costBasisDialog.add(date);
    costBasisDialog.add(getCostBasis);
    costBasisDialog.add(costBasisDialogStatus);
    return costBasisDialog;
  }

}
