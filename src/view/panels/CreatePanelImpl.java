package view.panels;

import view.panels.CreatePanel;

import javax.swing.*;
import java.awt.*;

/**
 * CLass that implements CreatePanel interface adds create panel, gets create pf button,
 * gets status of the create pane.
 */
public class CreatePanelImpl implements CreatePanel {
  private JPanel createPanel;
  private JButton createPfButton;
  private JLabel createStatus;

  // fields for create.
  private JTextField pfnamecreate;
  private JTextField dateofcreation;
  private JTextField tickrcreate;
  private JTextField numstockscreate;
  private JTextField commissionfeescreate;
  private final JButton add = new JButton("Add");
  private final JButton save = new JButton("Save");
  private final JLabel createDialogStatus = new JLabel();

  /**
   * input tickr symbol.
   * @return tickr symbol entered
   */
  public JTextField getTickrcreate(){
    return this.tickrcreate;
  }

  /**
   * return date of creation while adding entries to portfolio.
   * @return date of creation
   */
  public JTextField getDateofcreation(){
    return this.dateofcreation;
  }

  /**
   * return portfolio name field.
   * @return portfolio name text field
   */
  public JTextField getPfnamecreate(){
    return this.pfnamecreate;
  }

  /**
   * num of stocks given to this field.
   * @return num of stocks field
   */
  public JTextField getNumstockscreate(){
    return this.numstockscreate;
  }

  /**
   * commission field given to this field.
   * @return commission field
   */
  public JTextField getCommissionfeescreate(){
    return this.commissionfeescreate;
  }

  /**
   * Status after saving or adding entries in portfolio.
   * @return status of the create dialog
   */
  public JLabel getCreateDialogStatus(){
    return this.createDialogStatus;
  }

  /**
   * Button to save entries to the portfolio.
   * @return save button
   */
  public JButton getSaveCreate(){
    return this.save;
  }

  /**
   * Button to add entries in portfolio.
   * @return add button
   */
  public JButton getAddCreate(){
    return this.add;
  }

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
   * Returns status of the create panel portfolio.
   * @return status of the create panel
   */
  public JLabel getCreateStatus(){
    return this.createStatus;
  }

  private JPanel getCreatePfDialog() {
    JPanel createPanelDialog = new JPanel();
    this.pfnamecreate = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name to be created: ");
    dateofcreation = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter the date of purchase in YYYY-MM-DD format. " +
            "Ex: 2022-02-01 :");
    JLabel numstocksLabel = new JLabel("Enter number of stocks to purchase. Negative " +
            "and Fractional stock values are not allowed :");
    numstockscreate = new JTextField(25);
    JLabel tickrLabel = new JLabel("Enter Tickr symbol of a company you want to purchase." +
            " Ex: for company google tickr symbol - GOOG :");
    tickrcreate = new JTextField(25);
    JLabel commissionLabel = new JLabel("Enter commission fees. Negative values are not " +
            "allowed (It is an optional parameter):");
    commissionfeescreate = new JTextField(25);
    createPanelDialog.setPreferredSize(new Dimension(1400, 300));
    createPanelDialog.setLayout(new BoxLayout(createPanelDialog, BoxLayout.Y_AXIS));
    createPanelDialog.add(pfNameLabel);
    createPanelDialog.add(pfnamecreate);
    createPanelDialog.add(dateLabel);
    createPanelDialog.add(dateofcreation);
    createPanelDialog.add(numstocksLabel);
    createPanelDialog.add(numstockscreate);
    createPanelDialog.add(tickrLabel);
    createPanelDialog.add(tickrcreate);
    createPanelDialog.add(commissionLabel);
    createPanelDialog.add(commissionfeescreate);
    createPanelDialog.add(add);
    createPanelDialog.add(save);
    createPanelDialog.add(createDialogStatus);
    return createPanelDialog;
  }

  /**
   * Displays the create portfolio dialog pane that lets users create a flexible portfolio,
   * when create portfolio button is clicked.
   */
  public void displayCreatePf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getCreatePfDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Create Portfolio");
    dialog.setVisible(true);
  }
}
