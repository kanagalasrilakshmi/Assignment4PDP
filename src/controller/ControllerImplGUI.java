package controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
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
  private String pfNameCreate;
  private String tickrCreate;
  private String numStocksCreate;
  private String dateCreate;
  private String commissionCreate;
  private JSONObject addTickr;


  public ControllerImplGUI(Portfolio portfolio, GUIView guiView) {
    this.guiView = guiView;
    this.portfolio = portfolio;
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
  }

  private void setDirectory() {
    if (this.rootDirUser.length() == 0) {
      this.rootDirUser = this.rootDir;
      guiView.setpathStore("No path is given hence " +
              this.rootDir + " is set by default.");
    } else if (new File(this.rootDirUser).exists()) {
      guiView.setpathStore("Portfolios can be accessed in the " + this.rootDirUser + "location ");
      if (!portfolio.checkLastEndingCharacter(this.rootDirUser)) {
        this.rootDirUser = this.rootDirUser + "/";
      }
      this.rootDir = this.rootDirUser;
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

  public boolean checkValidpfName(String pfName) {
    if (pfName == null || pfName.length() > 25 || pfName.isEmpty() || pfName.contains(" ")) {
      return false;
    }
    for (int i = 0; i < pfName.length(); i++) {
      if (!Character.isLetter(pfName.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public void goStocks() throws ParseException, IOException {
    guiView.makeVisible();
    guiView.setCommandButtonListener(this);
  }

  public void actionPerformed(ActionEvent arg0) {
    switch (arg0.getActionCommand()) {
      case "Input": {
        this.rootDirUser = JOptionPane.showInputDialog("Please enter path to store portfolios");
        setDirectory();
      }
      break;
      case "Create Portfolio": {
        if (this.rootDirUser.length() == 0) {
          // set the label text.
          guiView.setCreateLabelStatus("Please specify the root directory path!!");
        } else {
          this.addTickr = new JSONObject();
          guiView.displayCreatePf();
        }
      }
      break;
      case "Add": {
        this.pfNameCreate = guiView.getCreatePfValue();
        this.tickrCreate = guiView.gettickrcreateValue();
        this.numStocksCreate = guiView.getnumstockscreateValue();
        this.dateCreate = guiView.getdateofcreationValue();
        this.commissionCreate = guiView.getcommissionfeescreateValue();
        if (this.pfNameCreate.length() == 0 || this.dateCreate.length() == 0 ||
                this.numStocksCreate.length() == 0 ||
                this.tickrCreate.length() == 0) {
          guiView.setcreateDialogStatus("All the values are not given!!");
        }
        else {
          Float commission = 0.0f;
          if (this.commissionCreate.length() != 0) {
            // validate commision fees.
            if(portfolio.checkValidInteger(this.commissionCreate) || portfolio.checkValidFloat(this.commissionCreate)){
              guiView.setcreateDialogStatus("Commision fees given is not a valid number");
            }
            else if(Float.valueOf(this.commissionCreate)<0){
              guiView.setcreateDialogStatus("Commision fees cannot be negative!");
            }
            else{
              commission = Float.valueOf(this.commissionCreate);
            }
          }
          else {
            // if pf name exists then print already exists.
            if (!checkValidpfName(pfNameCreate)) {
              guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
            } else if (new File(this.rootDir
                    + pfNameCreate + ".json").exists()) {
              guiView.setcreateDialogStatus("Portfolio with this name" +
                      pfNameCreate + "already exists!!");
            }
            // validate date.
            else if (!portfolio.checkIfRightFormat(dateCreate) || dateCreate.length() < 10) {
              guiView.setcreateDialogStatus("Date is not entered in YYYY-DD-MM format!");
            } else if (portfolio.checkFutureDate(dateCreate) ||
                    portfolio.checkTodayDateAndTime(dateCreate)) {
              guiView.setcreateDialogStatus("You can only enter past date or present(if after " +
                      "9:30am).! Please enter new date");
            }
            // validate num of stocks.
            else if (!portfolio.checkValidInteger(this.numStocksCreate)) {
              guiView.setcreateDialogStatus("Number of stocks given is not a valid integer");
            }
            // validate tickr symbol.
            else {
              try {
                if(!portfolio.validateTickrSymbol(tickrCreate)){
                  guiView.setcreateDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                          "tickr symbol!");
                }
                else{
                  if (portfolio.checkTickrJSONArray(addTickr, this.tickrCreate)) {
                    guiView.setcreateDialogStatus("The tickr symbol already exists");
                  }
                  else if (!portfolio.checkTickrJSONArray(addTickr, this.tickrCreate)){
                    JSONObject addEntry = portfolio.makeTransactionRecord(this.dateCreate,
                            commission, Integer.valueOf(this.numStocksCreate), this.tickrCreate);
                    JSONArray listEntry = new JSONArray();
                    listEntry.add(addEntry);
                    addTickr.put(this.tickrCreate, listEntry);
                  }
                }
              } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
              }
            }
          }
        }
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
