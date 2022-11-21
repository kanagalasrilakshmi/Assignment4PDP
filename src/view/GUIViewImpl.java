package view;

import java.awt.*;

import javax.swing.*;

public class GUIViewImpl implements GUIView{
  private JFrame frame;
  private JLabel pathStore;

  private JLabel pfName;
  private JLabel tickrSymbol;
  private JLabel noStocks;
  private JLabel commisionFees;
  private JLabel datePurchase;
  private JTextField userPath;
  private JTextField pfNameText;
  private JTextField tickrSymbolText;
  private JTextField noStocksText;
  private JTextField commissionFeesText;
  private JTextField datePurchaseText;

  private JButton createPath;
  private JButton addStocks;
  private JButton savePf;

  private JPanel userJPanel;
  private JPanel createJPanel;
  private JPanel modifyPurchaseJPanel;
  private JPanel modifySellJPanel;
  private JPanel costBasisJPanel;
  private JPanel viewJPanel;
  private JPanel quitJPanel;
  private JPanel mainJPanel;
  public GUIViewImpl(){
    frame = new JFrame("Stocks Program");
    this.createJFrame();
    frame.add(mainJPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000,1000);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
  public void createJFrame(){
    mainJPanel = new JPanel();
    userJPanel = createUserPanel();
    mainJPanel.add(userJPanel);
    createJPanel = new JPanel();
    createJPanel.setLayout(new FlowLayout());
    pfName = new JLabel("Enter Portfolio Name :");
    createJPanel.add(pfName);
    pfNameText = new JTextField(25);
    createJPanel.add(pfNameText);
    mainJPanel.add(createJPanel);

  }

  public JPanel createUserPanel(){
    JPanel user = new JPanel();
    user.setLayout(new FlowLayout());
    pathStore = new JLabel("Enter User Path :");
    user.add(pathStore);
    userPath = new JTextField(25);
    user.add(userPath);
    createPath = new JButton("Create Path");
    user.add(createPath);
    return user;
  }
}
