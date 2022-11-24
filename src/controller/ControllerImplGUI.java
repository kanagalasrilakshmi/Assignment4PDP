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

  private void saveOperation(String pfNameCreate){
    if(pfNameCreate.length() == 0){
      guiView.setcreateDialogStatus("Portfolio Name is to be given!!");
    }
    else if(pfNameCreate.length() > 0){
      // validate pf name.
      if (!checkValidpfName(pfNameCreate)) {
        guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
      }
      else if (new File(this.rootDir
              + pfNameCreate + ".json").exists()) {
        guiView.setcreateDialogStatus("Portfolio with this name" +
                pfNameCreate + "already exists!!");
      }
    }
    else{
      portfolio.savePortfolio(this.rootDir + pfNameCreate + ".json",
              this.addTickr);
      guiView.setcreateDialogStatus("Successfully created the portfolio " + pfNameCreate);
    }
  }
  private void addOperation(String pfNameCreate, String tickrCreate,String numStocksCreate,
                String dateCreate,String commissionCreate){
    if (pfNameCreate.length() == 0 || tickrCreate.length() == 0 ||
            numStocksCreate.length() == 0 ||
            dateCreate.length() == 0) {
      guiView.setcreateDialogStatus("All the fields are not given!!");
    }
    else
    {
      Float commission = 0.0f;
      // validate pf name.
      if (!checkValidpfName(pfNameCreate)) {
        guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
      }
      else if (new File(this.rootDir
              + pfNameCreate + ".json").exists()) {
        guiView.setcreateDialogStatus("Portfolio with this name" +
                pfNameCreate + "already exists!!");
      }
      // validate commission fees.
      else if (commissionCreate.length() > 0){
        if(portfolio.checkValidInteger(commissionCreate) ||
                portfolio.checkValidFloat(commissionCreate)){
          guiView.setcreateDialogStatus("Commission fees given is not a valid number");
        }
        else if(Float.valueOf(commissionCreate)<0){
          guiView.setcreateDialogStatus("Commission fees cannot be negative!");
        }
        else{
          commission = Float.valueOf(commissionCreate);
        }
      }
      // validate date.
      else if (!portfolio.checkIfRightFormat(dateCreate) || dateCreate.length() < 10) {
        guiView.setcreateDialogStatus("Date is not entered in YYYY-DD-MM format!");
      }
      else if (portfolio.checkFutureDate(dateCreate) ||
              portfolio.checkTodayDateAndTime(dateCreate)) {
        guiView.setcreateDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
      }
      // validate num of stocks.
      else if (!portfolio.checkValidInteger(numStocksCreate)) {
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
            if (portfolio.checkTickrJSONArray(this.addTickr, tickrCreate)) {
              guiView.setcreateDialogStatus("The tickr symbol already exists");
            }
            else{
              JSONObject addEntry = portfolio.makeTransactionRecord(dateCreate,
                      commission, Integer.valueOf(numStocksCreate), tickrCreate);
              JSONArray listEntry = new JSONArray();
              listEntry.add(addEntry);
              this.addTickr.put(tickrCreate, listEntry);
              guiView.setcreateDialogStatus("Entry added sucessfully!. Click Add to add more " +
                      "entries or Save to save the portfolio.");
            }
          }
        } catch (FileNotFoundException e) {
          guiView.setcreateDialogStatus("Exception while validating tickr symbol.");
          throw new RuntimeException(e);
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
          guiView.setCreateLabelStatus("Please specify the root directory path!!");
        } else {
          this.addTickr = new JSONObject();
          guiView.displayCreatePf();
        }
      }
      break;
      case "Add": {
        addOperation(guiView.getCreatePfValue(),guiView.gettickrcreateValue(),
                guiView.getnumstockscreateValue(), guiView.getdateofcreationValue(),
                guiView.getcommissionfeescreateValue());
      }
      break;
      case "Save":{
        saveOperation(guiView.getCreatePfValue());
      }
      break;
      case "Modify Portfolio": {
        if (this.rootDirUser.length() == 0) {
          guiView.setCreateLabelStatus("Please specify the root directory path!!");
        }
        else{
          guiView.displayModifyPf();
        }
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
