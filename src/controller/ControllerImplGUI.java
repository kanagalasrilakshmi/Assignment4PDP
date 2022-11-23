package controller;

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
  private String rootDirUser;

  public ControllerImplGUI(Portfolio portfolio, GUIView guiView) {
    this.guiView = guiView;
    this.portfolio = portfolio;
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }

  public void setDirectory(String rootDirUser) {
    if (rootDirUser.length() == 0) {
      guiView.setpathStore("No path is given hence " +
              this.rootDir + " is set by default.");
    } else if (new File(rootDirUser).exists()) {
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
    switch (arg0.getActionCommand()) {
      case "Input": {
        rootDirUser = JOptionPane.showInputDialog("Please enter path to store portfolios");
        setDirectory(rootDirUser);
      }
      break;
      case "Create Portfolio": {
        // in create portfolio.
        // if path is null then ask the user to first specify the root directory and
        // then only click on buttons
        // check pf name,date,numStocksLabel,tickr Label,commission label.
        // if commission value is not given take it as zero only.
        // if pf name||date label|| numStocks Label|| tickr label is null then take return a ,
        // message that all values are not given.
        // if pf name exists then print already exists.
        // validate date.
        // validate num of stocks.
        // validate tickr symbol.
        guiView.displayCreatePf();
      }
      break;
      case "Modify Portfolio": {
        // in modify portfolio.
        // if path is null then ask the user to first specify the root directory and.
        // then only click on buttons.
        // check pf name,date,numStocksLabel,tickr Label,commission label.
        // if commission value is not given take it as zero only.
        // if pf name||date label|| numStocks Label|| tickr label is null then take return a ,
        // message that all values are not given.
        // if pf does not exist then print does not exist.
        // validate date.
        // validate num of stocks.
        // validate tickr symbol.
        // if purchase validate differently.
        // if sell validate differently.
        guiView.displayModifyPf();
      }
      break;
      case "Get Value": {
        // if path is null then ask the user to first specify the root directory and.
        // then only click on buttons.
        // if pf name||date label is null then return a message that all values are not given.
        // validate date.
        // validate num of stocks.
        guiView.displayValuepf();
      }
      break;
      case "Get Cost Basis": {
        // if path is null then ask the user to first specify the root directory and.
        // then only click on buttons.
        // if pf name||date label is null then return a message that all values are not given.
        // validate date.
        // validate num of stocks.
        guiView.displayCostBasis();
      }
      break;
    }
  }


}
