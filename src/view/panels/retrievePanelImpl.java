package view.panels;

import javax.swing.*;
import java.awt.*;

/**
 * Create retrieve portfolio panel for implementing functionalities for retrieving a portfolio.
 */
public class retrievePanelImpl implements retrievePanel{
  private JPanel retrievePanel;
  private JLabel retrievePanelStatus;
  private JButton retrievePf = new JButton("Retrieve Portfolio");
  private final JTextArea portfoliosListRetrieve = new JTextArea();
  private final JTextArea portfolioComposition = new JTextArea();
  private JTextField pfnameretrieve;
  private final JButton computecomposition = new JButton("Get Portfolio Composition");
  private final JLabel retrieveDialogStatus = new JLabel();

  public JTextArea getPortfoliosListRetrieve(){
    return this.portfoliosListRetrieve;
  }

  public JTextArea getPortfolioComposition(){
    return this.portfolioComposition;
  }

  public JTextField getPfnameretrieve(){
    return this.pfnameretrieve;
  }

  public JButton getComputecomposition(){
    return this.computecomposition;
  }

  public JLabel getRetrieveDialogStatus(){
    return this.retrieveDialogStatus;
  }

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

  /**
   * Display the composition of the portfolio pane,
   * gives the portfolio composition on a given date for a portfolio.
   */
  public void displayRetrievepf() {
    JDialog dialog;
    JOptionPane optionPane = new JOptionPane(getRetrievePanelDialog(), JOptionPane.PLAIN_MESSAGE,
            JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
    dialog = optionPane.createDialog("Composition of the Portfolio");
    dialog.setVisible(true);
  }

  private JPanel getRetrievePanelDialog() {
    JPanel retrievePfDialog = new JPanel();
    JLabel listPortfolios = new JLabel("List of all the portfolios in the given path:");
    JLabel listcomposition = new JLabel("Portfolio composition");
    portfoliosListRetrieve.setEditable(false);
    portfolioComposition.setEditable(false);
    JLabel pfNameLabel = new JLabel("Enter Portfolio Name for which composition needs to be " +
            "fetched. Enter only names from the given list of portfolios. If no portfolios exist " +
            "then close this dialog pane by clicking 'X' on top left");
    pfnameretrieve = new JTextField(25);
    retrievePfDialog.setLayout(new BoxLayout(retrievePfDialog, BoxLayout.Y_AXIS));
    retrievePfDialog.setPreferredSize(new Dimension(700, 700));
    retrievePfDialog.setMaximumSize(new Dimension(1000, 1000));
    retrievePfDialog.setMinimumSize(new Dimension(400, 400));
    retrievePfDialog.add(listPortfolios);
    retrievePfDialog.add(portfoliosListRetrieve);
    retrievePfDialog.add(pfNameLabel);
    retrievePfDialog.add(pfnameretrieve);
    retrievePfDialog.add(computecomposition);
    retrievePfDialog.add(retrieveDialogStatus);
    retrievePfDialog.add(listcomposition);
    retrievePfDialog.add(portfolioComposition);
    return retrievePfDialog;
  }
}
