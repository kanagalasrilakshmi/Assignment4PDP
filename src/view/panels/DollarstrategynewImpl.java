package view.panels;

import javax.swing.*;

/**
 * Create panel for implementing functionalities for applying dollar averaging strategy,
 * start-to-finish.
 */
public class DollarstrategynewImpl implements Dollarstrategynew{
  private JPanel dollarPanelNew;
  private JLabel dollarNewStatus;
  private JButton dollarCostNew;

  /**
   * Creating panel for applying dollar cost averaging start-to-finish.
   */
  public void dollarCostNew() {
    dollarPanelNew = new JPanel();
    dollarPanelNew.setBorder(BorderFactory.createTitledBorder("Create a start-to-finish dollar " +
            "cost averaging portfolio"));
    dollarCostNew = new JButton("Create dollar cost averaging start-to-finish for a new" +
            "portfolio");
    dollarNewStatus = new JLabel();
    dollarPanelNew.add(dollarCostNew);
    dollarPanelNew.add(dollarNewStatus);
  }

  /**
   * Return dollar cost averaging panel for start-to-finish panel.
   * @return Jpanel type object for dollar cost averaging panel start-to-finish
   */
  public JPanel getdollarCostNew(){
    return this.dollarPanelNew;
  }

  /**
   * Status after clicking the dollar cost averaging start-to-finish panel.
   * @return Jlabel object for getting status after accessing dollar cost start-to-finish panel
   */
  public JLabel getDollarNewStatus(){
    return this.dollarNewStatus;
  }

  /**
   * Return button for applying the dollar cost averaging start-to-finish.
   *
   * @return Jbutton object for accessing dollar cost averaging start-to-finish panel
   */
  public JButton returndollarCostNew(){
    return this.dollarCostNew;
  }
}
