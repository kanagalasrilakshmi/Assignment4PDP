package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;
import controller.ControllerImplGUI;

public class GUIViewImpl extends JFrame implements GUIView {
  private JFrame frame;
  private JLabel pathStore;

  private JLabel pfName;
  private String h;
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
  JButton inputButton;

  private JPanel userJPanel;
  private JPanel createJPanel;
  private JPanel modifyPurchaseJPanel;
  private JPanel modifySellJPanel;
  private JPanel costBasisJPanel;
  private JPanel viewJPanel;
  private JPanel quitJPanel;
  private JPanel mainJPanel;
  public GUIViewImpl (){
    super();
    setTitle("Stocks Program");
    // main panel.
    mainJPanel = new JPanel();
    // user panel.
    JPanel userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("User Path"));
    inputButton = new JButton("Create User Path");
    inputButton.setActionCommand("Input");
    userPanel.add(inputButton);
    pathStore = new JLabel();
    userPanel.add(pathStore);
    // adding quit button.
    // adding user panel.
    mainJPanel.add(userPanel);
    add(mainJPanel);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000,1000);
    setLocationRelativeTo(null);
  }
  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    inputButton.addActionListener(actionEvent);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public void setpathStore(String setMessage){
    pathStore.setText(setMessage);
  }

}
