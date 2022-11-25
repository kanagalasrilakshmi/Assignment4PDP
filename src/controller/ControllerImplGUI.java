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
  private Float commisionModify;
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
    if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
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

  private void saveOperation(String pfNameCreate) {
    if (pfNameCreate.length() == 0) {
      guiView.setcreateDialogStatus("Portfolio Name is to be given!!");
    } else if (pfNameCreate.length() > 0) {
      // validate pf name.
      if (!checkValidpfName(pfNameCreate)) {
        guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
      } else if (new File(this.rootDir
              + pfNameCreate + ".json").exists()) {
        guiView.setcreateDialogStatus("Portfolio with this name" +
                pfNameCreate + "already exists!!");
      }
    } else {
      portfolio.savePortfolio(this.rootDir + pfNameCreate + ".json",
              this.addTickr);
      guiView.setcreateDialogStatus("Successfully created the portfolio " + pfNameCreate);
    }
  }

  private void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
                            String dateCreate, String commissionCreate) {
    if (pfNameCreate == null || tickrCreate == null || numStocksCreate == null ||
            dateCreate == null || pfNameCreate.length() == 0 || tickrCreate.length() == 0 ||
            numStocksCreate.length() == 0 ||
            dateCreate.length() == 0) {
      guiView.setcreateDialogStatus("All the fields are not given!!");
    } else {
      Float commission = 0.0f;
      // validate pf name.
      if (!checkValidpfName(pfNameCreate)) {
        guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
      } else if (new File(this.rootDir
              + pfNameCreate + ".json").exists()) {
        guiView.setcreateDialogStatus("Portfolio with this name" +
                pfNameCreate + "already exists!!");
      }
      else if(commissionCreate == null || commissionCreate.length() == 0){
        commission = 0.0f;
      }
      // validate commission fees.
      else if (commissionCreate.length() > 0) {
        if (portfolio.checkValidInteger(commissionCreate) ||
                portfolio.checkValidFloat(commissionCreate)) {
          guiView.setcreateDialogStatus("Commission fees given is not a valid number");
        } else if (Float.valueOf(commissionCreate) < 0) {
          guiView.setcreateDialogStatus("Commission fees cannot be negative!");
        } else {
          commission = Float.valueOf(commissionCreate);
        }
      }
      // validate date.
      else if (!portfolio.checkIfRightFormat(dateCreate) || dateCreate.length() < 10) {
        guiView.setcreateDialogStatus("Date is not entered in YYYY-DD-MM format!");
      } else if
      (portfolio.checkFutureDate(dateCreate) ||
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
          if (!portfolio.validateTickrSymbol(tickrCreate)) {
            guiView.setcreateDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                    "tickr symbol!");
          } else {
            if (portfolio.checkTickrJSONArray(this.addTickr, tickrCreate)) {
              guiView.setcreateDialogStatus("The tickr symbol already exists");
            } else {
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

  private void setCommision(Float commision) {
    this.commisionModify = commision;
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

  private boolean modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                                 String dateModify, String commissionModify) {
    // check pf name,date,numStocksLabel,tickr Label,commission label.
    // if commission value is not given take it as zero only.
    boolean checkModify = true;
    if (pfNameModify == null || tickrModify == null || numStocksModify == null ||
            dateModify == null || pfNameModify.length() == 0 || tickrModify.length() == 0 ||
            numStocksModify.length() == 0 ||
            dateModify.length() == 0) {
      guiView.setmodifyDialogStatus("All the fields are not given!!");
      checkModify = false;
    } else {
      Float commission = 0.0f;
      if (!checkValidpfName(pfNameModify)) {
        guiView.setmodifyDialogStatus("Please enter a valid Portfolio name!!");
        checkModify = false;
      } else if (!(new File(this.rootDir
              + pfNameModify + ".json").exists())) {
        guiView.setmodifyDialogStatus("Portfolio with this name" +
                pfNameModify + "does not exist!!");
        checkModify = false;
      }
      else if(commissionModify == null || commissionModify.length() == 0){
        checkModify = false;
      }
      // validate commission fees.
      else if (commissionModify.length() > 0) {
        if (portfolio.checkValidInteger(commissionModify) ||
                portfolio.checkValidFloat(commissionModify)) {
          guiView.setmodifyDialogStatus("Commission fees given is not a valid number");
          checkModify = false;
        } else if (Float.valueOf(commissionModify) < 0) {
          guiView.setmodifyDialogStatus("Commission fees cannot be negative!");
          checkModify = false;
        } else {
          commission = Float.valueOf(commissionModify);
        }
        setCommision(commission);
      }
      // validate date.
      else if (!portfolio.checkIfRightFormat(dateModify) || dateModify.length() < 10) {
        guiView.setmodifyDialogStatus("Date is not entered in YYYY-DD-MM format!");
        checkModify = false;
      } else if (portfolio.checkFutureDate(dateModify) ||
              portfolio.checkTodayDateAndTime(dateModify)) {
        guiView.setmodifyDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
        checkModify = false;
      }
      // validate num of stocks.
      else if (!portfolio.checkValidInteger(numStocksModify)) {
        guiView.setmodifyDialogStatus("Number of stocks given is not a valid integer");
        checkModify = false;
      }
    }
    return checkModify;
  }

  private void validateTickrModify(String pfNameModify, String tickrModify, String numStocksModify,
                                   String dateModify, Float commissionModify, boolean label)
          throws ParseException {
    if (label) {
      if (!portfolio.ifTickrInPf(this.rootDir + pfNameModify + ".json", tickrModify)) {
        guiView.setmodifyDialogStatus("No stocks for this tickr exists to sell.");
      } else if (!portfolio.checkValidSell(this.rootDir + pfNameModify + ".json",
              Integer.valueOf(numStocksModify), tickrModify, dateModify)) {
        guiView.setmodifyDialogStatus("The number entered for selling stocks is more than " +
                "stocks purchased");
      } else {
        portfolio.modifyJson(commissionModify, Integer.valueOf(numStocksModify) * (-1),
                dateModify, tickrModify, this.rootDir + pfNameModify + ".json");
        guiView.setmodifyDialogStatus("The portfolio " + pfNameModify +
                "is successfully modified");
      }
    } else {
      try {
        if (!portfolio.validateTickrSymbol(pfNameModify)) {
          guiView.setmodifyDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                  "tickr symbol!");
        } else {
          portfolio.modifyJson(commissionModify, Integer.valueOf(numStocksModify), dateModify,
                  tickrModify, this.rootDir + pfNameModify + ".json");
          guiView.setmodifyDialogStatus("The portfolio " + pfNameModify +
                  "is successfully modified");
        }
      } catch (FileNotFoundException e) {
        guiView.setmodifyDialogStatus("Exception while validating tickr symbol.");
        throw new RuntimeException(e);
      }
    }

  }
  private void validateDate(String pfNamedate, String dateValue)
          throws FileNotFoundException, ParseException {
    if(guiView.getpfnameVal() == null || guiView.getpfnameVal().length() == 0 ||
            guiView.getdateVal() == null || guiView.getdateVal().length() == 0 ){
      guiView.setvalueDialogStatus("All the values are not given!!");
    }
    else{
      if (!checkValidpfName(pfNamedate)) {
        guiView.setmodifyDialogStatus("Please enter a valid Portfolio name!!");
      }
      else if (!(new File(this.rootDir
              + pfNamedate + ".json").exists())) {
        guiView.setvalueDialogStatus("Portfolio with this name" +
                pfNamedate + "does not exist!!");
      }
      else if (!portfolio.checkIfRightFormat(dateValue) || dateValue.length() < 10) {
        guiView.setvalueDialogStatus("Date is not entered in YYYY-DD-MM format!");
      } else if (portfolio.checkFutureDate(dateValue) ||
              portfolio.checkTodayDateAndTime(dateValue)) {
        guiView.setvalueDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
      }
      else{
        float totVal = portfolio.portfolioValueDate(this.rootDir, pfNamedate, dateValue);
        guiView.setvalueDialogStatus("Portfolio Value on " + dateValue + " is : $" + totVal);
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
        this.rootDirUser = JOptionPane.showInputDialog("Please enter path to store portfolios");
        setDirectory();
      }
      break;
      case "Create Portfolio": {
        if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
          guiView.setCreateLabelStatus("Please specify the root directory path!!");
        } else {
          this.addTickr = new JSONObject();
          guiView.displayCreatePf();
        }
      }
      break;
      case "Add": {
        addOperation(guiView.getCreatePfValue(), guiView.gettickrcreateValue(),
                guiView.getnumstockscreateValue(), guiView.getdateofcreationValue(),
                guiView.getcommissionfeescreateValue());
      }
      break;
      case "Save": {
        saveOperation(guiView.getCreatePfValue());
      }
      break;
      case "Modify Portfolio": {
        if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
          guiView.setModifyLabelStatus("Please specify the root directory path!!");
        } else {
          guiView.displayModifyPf();
        }
      }
      break;
      case "Purchase": {
        if (modifyValidate(guiView.getModifyPfValue(), guiView.gettickrmodifyValue(),
                guiView.getnumstocksmodifyValue(), guiView.getdateofmodifynValue(),
                guiView.getcommissionfeesmodifyValue())) {
          // validate tickr symbol.
          try {
            validateTickrModify(guiView.getModifyPfValue(), guiView.gettickrmodifyValue(),
                    guiView.getnumstocksmodifyValue(), guiView.getdateofmodifynValue(),
                    this.commisionModify, false);
          } catch (ParseException e) {
            throw new RuntimeException(e);
          }
        }
      }
      break;
      case "Sell": {
        if (modifyValidate(guiView.getModifyPfValue(), guiView.gettickrmodifyValue(),
                guiView.getnumstocksmodifyValue(), guiView.getdateofmodifynValue(),
                guiView.getcommissionfeesmodifyValue())) {
          try {
            validateTickrModify(guiView.getModifyPfValue(), guiView.gettickrmodifyValue(),
                    guiView.getnumstocksmodifyValue(), guiView.getdateofmodifynValue(),
                    this.commisionModify, true);
          } catch (ParseException e) {
            throw new RuntimeException(e);
          }
        }
      }
      break;
      case "Get Value": {
        if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
          guiView.setValueLabelStatus("Please specify the root directory path!!");
        }
        else{
          guiView.displayValuepf();
        }
      }
      break;
      case "Compute Value of Pf":{
        try {
          validateDate(guiView.getpfnameVal(),guiView.getdateVal());
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
      break;
      case "Get Cost Basis": {
        if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
          // guiView.setCreateLabelStatus("Please specify the root directory path!!");
        }
        else{
          // if pf name||date label is null then return a message that all values are not given.
          // validate date.
          // validate num of stocks.
          guiView.displayCostBasis();
        }
      }
      break;
    }
  }
}
