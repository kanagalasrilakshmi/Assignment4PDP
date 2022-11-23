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
    if(rootDirUser.length() == 0){
      guiView.setpathStore("No path is given hence " +
              this.rootDir + " is set by default.");
    }
    else if (new File(rootDirUser).exists()) {
      guiView.setpathStore("Portfolios can be accessed in the " + rootDirUser + "location ");
      if (!portfolio.checkLastEndingCharacter(rootDirUser)) {
        rootDirUser = rootDirUser + "/";
      }
      this.rootDir = rootDirUser;
    }
    else {
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
        String rootDirUser = JOptionPane.showInputDialog("Please enter path to store portfolios");
        setDirectory(rootDirUser);
        break;
      }
      case "Create Portfolio": {
        // display the dialog box for create portfolio.
        // take the inputs from the dialog box.
        guiView.displayCreatePf();
        /**
         JSONObject addTickr = new JSONObject();
         switch (arg0.getActionCommand()){
         case "Add":
         break;
         case "Save":
         break;
         }*/
        break;
      }
      
    }
  }


}
