package view.panels;

import javax.swing.*;

/**
 * Create panel for implementing functionalities for applying dollar averaging strategy,
 * on existing portfolio.
 */
public class DollarstrategyexistImpl implements Dollarstrategyexist{
  private JPanel dollarPanelExisting;
  private JButton dollarCostExisting;
  private JLabel dollarExistingStatus;

  /**
   * Creating panel for applying dollar cost averaging on existing portfolio.
   */
  public void dollarCostExisting() {
    dollarPanelExisting = new JPanel();
    dollarCostExisting = new JButton("Calculate dollar cost averaging for existing portfolio");
    dollarPanelExisting.setBorder(BorderFactory.createTitledBorder("Create dollar cost " +
            "averaging for existing flexible portfolios"));
    dollarExistingStatus = new JLabel();
    dollarPanelExisting.add(dollarCostExisting);
    dollarPanelExisting.add(dollarExistingStatus);
  }

  /**
   * Return dollar cost averaging panel for existing portfolio panel.
   * @return Jpanel type object for dollar cost averaging panel for existing portfolio
   */
  public JPanel getDollarCostExisting(){
    return this.dollarPanelExisting;
  }

  /**
   * Status after clicking the dollar cost averaging on existing portfolio panel.
   * @return Jlabel object for getting status after accessing dollar cost averaging portfolio panel
   */
  public JLabel getDollarExistingStatus(){
    return this.dollarExistingStatus;
  }

  /**
   * Return button for applying the dollar cost averaging on existing portfolio.
   *
   * @return Jbutton object for accessing dollar cost averaging on existing portfolio panel
   */
  public JButton returndollarCostExisting(){
    return this.dollarCostExisting;
  }
}
