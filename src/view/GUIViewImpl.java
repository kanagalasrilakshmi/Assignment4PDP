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
  private JLabel pathStore;
  private JButton inputButton;
  private JButton costBasisButton;
  private JButton quitButton;
  private JButton createPfButton;
  private JPanel mainJPanel;
  private JPanel userPanel;
  private JPanel createPanel;
  private JPanel costBasisPanel;
  private JLabel costBasisResult;
  private JPanel quitPanel;

  public GUIViewImpl (){
    super();
    setTitle("Stocks Program");

    // main panel.
    mainJPanel = new JPanel();
    mainJPanel.setLayout(new BoxLayout(mainJPanel,BoxLayout.Y_AXIS));

    // adding user panel.
    userPanel = new JPanel();
    userPanel.setBorder(BorderFactory.createTitledBorder("User Path"));
    inputButton = new JButton("Create User Path");
    inputButton.setActionCommand("Input");
    userPanel.add(inputButton);
    pathStore = new JLabel();
    userPanel.add(pathStore);

    // adding create new portfolio panel.

    // adding modify portfolio panel.

    // adding cost basis and value of a portfolio panel.
    costBasisPanel = new JPanel();
    costBasisPanel.setBorder(BorderFactory.createTitledBorder("Get Cost Basis and value of a " +
            "portfolio"));
    costBasisButton = new JButton("Get cost basis");
    costBasisButton.setActionCommand("Get Cost Basis");
    costBasisPanel.add(costBasisButton);
    costBasisResult = new JLabel();
    costBasisPanel.add(costBasisResult);

    // adding value of a portfolio panel.

    // adding dollar cost basis panel.

    // adding start-end dollar cost basis panel.

    // adding quit panel.
    quitPanel = new JPanel();
    quitPanel.setBorder(BorderFactory.createTitledBorder("Quit the stocks program"));
    quitButton = new JButton("Quit the Program");
    quitButton.addActionListener((ActionEvent e)->{
      System.exit(0);
    });
    quitPanel.add(quitButton);

    // adding panels.
    mainJPanel.add(userPanel);
    mainJPanel.add(quitPanel);
    mainJPanel.add(costBasisPanel);
    add(mainJPanel);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000,1000);
    setLocationRelativeTo(null);
  }
  @Override
  public void setCommandButtonListener(ActionListener actionEvent) {
    inputButton.addActionListener(actionEvent);
    createPfButton.addActionListener(actionEvent);
    costBasisButton.addActionListener(actionEvent);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  public void setpathStore(String setMessage){
    pathStore.setText(setMessage);
  }

  public void setCostBasisResult(String message){
    costBasisResult.setText(message);
  }

}
