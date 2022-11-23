package controller;

import org.json.simple.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.swing.*;

import model.Portfolio;
import view.GUIView;

public class ControllerImplGUI implements Controller, ActionListener {
  private GUIView guiView;
  private Portfolio portfolio;
  private String rootDir;

  public ControllerImplGUI(Portfolio portfolio, GUIView guiView) {
    this.guiView = guiView;
    this.portfolio = portfolio;
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }

  public void setDirectory(String rootDirUser) {
    if (new File(rootDirUser).exists()) {
      guiView.setpathStore("Portfolios can be accessed in the " + rootDirUser + "location ");
      if (!portfolio.checkLastEndingCharacter(rootDirUser)) {
        rootDirUser = rootDirUser + "/";
      }
      this.rootDir = rootDirUser;
    } else {
      guiView.setpathStore("Invalid path given so portfolios will be stored in " +
              this.rootDir + " by default.");
      if (!new File(this.rootDir).exists()) {
        try {
          Path path = Paths.get(this.rootDir);
          Files.createDirectories(path);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void goStocks() throws ParseException, IOException {
    guiView.makeVisible();
    guiView.setCommandButtonListener(this);
  }

  public void actionPerformed(ActionEvent arg0) {
    // TODO Auto-generated method stub
    switch (arg0.getActionCommand()) {
      case "Input":
        String rootDirUser = JOptionPane.showInputDialog("Please enter path to store portfolios");
        setDirectory(rootDirUser);
        break;
      case "Create Portfolio": {
        JSONObject addTickr = new JSONObject();
        String pfName = JOptionPane.showInputDialog("Enter the name of the portfolio you " +
                "want to create : ");

        String tickr = JOptionPane.showInputDialog("Enter the Tickr symbol to be added : ");
        String numStocks = JOptionPane.showInputDialog("Enter the Tickr symbol to be added : ");
        String dateOfPurchase = JOptionPane.showInputDialog("Enter the date of purchase of " +
                "stocks : ");
        String commisionFees = JOptionPane.showInputDialog("Enter the commission fees : ");
        switch (arg0.getActionCommand()) {
          case "Add":
            // check if pfName already exists.
            // check if tickr symbol is valid.
            // check if valid stocks are entered.
            // check if date of purchase is valid.
            if (pfName == null || tickr == null || numStocks == null || dateOfPurchase == null) {

            }
            // check if commission fees is null.
            // check if commission fees is float or integer.
            else if (commisionFees == null) {

            }
            break;
          case "Save":
            // if any one of the field apart from commission fees is null the save empty portfolio.
            portfolio.savePortfolio(this.rootDir + pfName + ".json",
                    addTickr);
            break;
        }
        break;
      }
      case "Modify Portfolio":
        break;
      case "Get Cost Basis":
        String pfNameChosen = JOptionPane.showInputDialog("Enter valid portfolio name:");
        String dateCostBasis = JOptionPane.showInputDialog("Enter the date at which you want to " +
                "get cost basis in YYYY-MM-DD format only!");
        // check pfname exists.
        // check if date is in valid format, future date, today's date before 9:30 am.
        try {
          float costBasis = portfolio.getCostBasis(this.rootDir + pfNameChosen + ".json",
                  dateCostBasis);
          guiView.setCostBasisResult("The cost basis till date, " + dateCostBasis
                  + " is: $" + costBasis);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
        break;
      case "Get Value by Date":

        break;
      case "dollar-cost":
        break;
      case "start-to-finish":
        break;
    }
  }


}
