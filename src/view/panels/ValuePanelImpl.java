package view.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Create view panel for implementing functionalities for creating view panel.
 */
public class ValuePanelImpl implements ValuePanel {
  private JPanel valueDatePanel;
  private JButton getValueButton;
  private final JLabel valueStatus = new JLabel();
  private final JTextArea portfoliosListVal = new JTextArea();
  private final JButton computeval = new JButton("Compute Value of Portfolio");
  private final JLabel valDialogStatus = new JLabel();

  // fields for value.
  private JTextField pfnamevalue;
  private JTextField datevalue;

  public JTextArea getPortfoliosListVal(){
    return this.portfoliosListVal;
  }
  public JButton getComputeval(){
    return this.computeval;
  }
  public JLabel getValDialogStatus(){
    return this.valDialogStatus;
  }
  public JTextField getDatevalue(){
    return this.datevalue;
  }

  public JTextField getPfnamevalue(){
    return this.pfnamevalue;
  }

  /**
   * Create view panel.
   */
  public void valDatePanel() {
    valueDatePanel = new JPanel();
    getValueButton = new JButton("Get Value of Portfolio on a Specific Date");
    valueDatePanel.setBorder(BorderFactory.createTitledBorder("Get Value of a flexible " +
            "portfolio on a specific date"));
    valueDatePanel.add(getValueButton);
    valueDatePanel.add(valueStatus);
  }

  /**
   * Return value date panel.
   * @return Jpanel type object for value panel
   */
  public JPanel getValueDatePanel(){
    return this.valueDatePanel;
  }

  /**
   * Return button for opening value dialog panel.
   * @return Jbutton object for value panel
   */
  public JButton returnValueButton(){
    return this.getValueButton;
  }

  /**
   * Status after clicking the value panel
   * @return Jlabel type object for getting the status after accessing value panel
   */
  public JLabel returnValueStatus(){
    return this.valueStatus;
  }

  /**
   * Display the value portfolio dialog pane,
   * lets users get the value of a flexible portfolio on a specific date.
   */
  public void displayValuepf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getValuePanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Value of the Portfolio");
    dialog.setVisible(true);
  }

  private JPanel getValuePanelDialog() {
    JPanel valDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    portfoliosListVal.setEditable(false);
    pfnamevalue = new JTextField(25);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name for which value needs to be " +
            "fetched. Enter only names from the given list of portfolios. If no portfolios exist " +
            "then close this dialog pane by clicking 'X' on top left");
    datevalue = new JTextField(25);
    JLabel dateLabel = new JLabel("Enter date to compute value of the portfolio in " +
            "YYYY-DD-MM format only. Ex-2021-02-02");
    valDialog.setLayout(new BoxLayout(valDialog, BoxLayout.Y_AXIS));
    valDialog.setPreferredSize(new Dimension(700, 700));
    valDialog.setMaximumSize(new Dimension(900, 500));
    valDialog.setMinimumSize(new Dimension(400, 400));
    valDialog.add(listPortfolios);
    valDialog.add(portfoliosListVal);
    valDialog.add(pfNameLabel);
    valDialog.add(pfnamevalue);
    valDialog.add(dateLabel);
    valDialog.add(datevalue);
    valDialog.add(computeval);
    valDialog.add(valDialogStatus);
    return valDialog;
  }

}
