package view.panels.modify;

import javax.swing.*;
import java.awt.*;

/**
 * Modify panel that implements functionalities for getting modify panel.
 */
public class ModifyPanelImpl implements ModifyPanel{
  private JPanel modifyPanel;
  private JButton getModifyButton;
  private JLabel modifyStatus = new JLabel();

  // fields for modify.
  private JTextField pfnamemodify;
  private JTextField dateofmodify;
  private JTextField tickrmodify;
  private JTextField numstocksmodify;
  private JTextField commissionfessmodify;
  private final JButton purchase = new JButton("Purchase");
  private final JButton sell = new JButton("Sell");
  private final JTextArea portfoliosListModify = new JTextArea();
  private final JLabel modifyDialogStatus = new JLabel();

  public JTextField getCommissionfessmodify(){
    return this.commissionfessmodify;
  }
  public JTextField getNumstocksmodify(){
    return this.numstocksmodify;
  }
  public JTextField getTickrmodify(){
    return this.tickrmodify;
  }
  public JTextField getDateofmodify(){
    return this.dateofmodify;
  }
  public JTextField getPfnamemodify(){
    return this.pfnamemodify;
  }
  public JButton getPurchasemodify(){
    return this.purchase;
  }
  public JButton getSellmodify(){
    return this.sell;
  }
  public JTextArea getPortfoliosListModify(){
    return this.portfoliosListModify;
  }
  public JLabel getModifyDialogStatus(){
    return this.modifyDialogStatus;
  }

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

  /**
   * Displays modify portfolio dialog pane,
   * lets users modify a flexible portfolio by purchasing and selling,
   * when modify portfolio button is clicked.
   */
  public void displayModifyPf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getModifyPanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Modify Portfolio");
    dialog.setVisible(true);
  }

  private JPanel getModifyPanelDialog() {
    JPanel modifyDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListModify.setEditable(false);
    pfnamemodify = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter portfolio name to be modified. Enter only names " +
            "from the given list of portfolios. If no portfolios exist then close this dialog " +
            "pane by clicking 'X' on top left");
    dateofmodify = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase/sell " +
            "in YYYY-DD-MM format only. Ex-2021-02-02");
    tickrmodify = new JTextField(25);
    JLabel tickrlabel = new JLabel("Enter Tickr symbol of a company you want to purchase " +
            "or sell. Ex: for company google tickr symbol - GOOG.");
    numstocksmodify = new JTextField(25);
    JLabel numstockslabel = new JLabel("Enter the number of stocks to be purchased/sold." +
            "Negative and Fractional shares are not allowed");
    commissionfessmodify = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees. Negative values are not " +
            "allowed. (Optional parameter)");
    modifyDialog.setLayout(new BoxLayout(modifyDialog, BoxLayout.Y_AXIS));
    modifyDialog.setPreferredSize(new Dimension(1000, 700));
    modifyDialog.setMaximumSize(new Dimension(700, 500));
    modifyDialog.setMinimumSize(new Dimension(500, 500));
    modifyDialog.add(listPortfolios);
    modifyDialog.add(portfoliosListModify);
    modifyDialog.add(pfNameLabel);
    modifyDialog.add(pfnamemodify);
    modifyDialog.add(dateLabel);
    modifyDialog.add(dateofmodify);
    modifyDialog.add(numstockslabel);
    modifyDialog.add(numstocksmodify);
    modifyDialog.add(tickrlabel);
    modifyDialog.add(tickrmodify);
    modifyDialog.add(commissionLabel);
    modifyDialog.add(commissionfessmodify);
    modifyDialog.add(purchase);
    modifyDialog.add(sell);
    modifyDialog.add(modifyDialogStatus);
    return modifyDialog;
  }
}
