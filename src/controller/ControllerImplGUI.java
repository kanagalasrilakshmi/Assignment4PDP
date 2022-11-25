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

import model.PortfolioStratergy;
import view.GUIView;

public class ControllerImplGUI implements Controller, ActionListener {
  private GUIView guiView;
  private PortfolioStratergy portfolio;
  private String rootDir;
  private String rootDirUser;
  private JSONObject addTickr;


  public ControllerImplGUI(PortfolioStratergy portfolio, GUIView guiView) {
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
      guiView.setpathStore("Portfolios can be accessed in the " + this.rootDirUser + " location ");
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
      } else {
        portfolio.savePortfolio(this.rootDir + pfNameCreate + ".json",
                this.addTickr);
        guiView.setcreateDialogStatus("Successfully created the portfolio " + pfNameCreate);
      }
    }
  }

  private boolean checkPortfolioField(String pfName, String label) {
    boolean fileCheck = new File(this.rootDir + pfName + ".json").exists();
    if (label.equals("add") || label.equals("save")) {
      if (!checkValidpfName(pfName)) {
        guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
        guiView.setCreatePfValue(null);
        return false;
      }
    }
    if (fileCheck) {
      if (label.equals("add") || label.equals("save")) {
        guiView.setcreateDialogStatus("Portfolio with this name" +
                pfName + " already exists!!");
        guiView.setCreatePfValue(null);
        return false;
      }
    } else {
      if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("Portfolio with this name" +
                pfName + " does not exist!!");
        return false;
      } else if (label.equals("costBasis")) {
        guiView.setCostBasisDialogStatus("Portfolio with this name" +
                pfName + " does not exist!!");
        return false;
      } else if (label.equals("valDate")) {
        guiView.setvalueDialogStatus("Portfolio with this name" +
                pfName + "does not exist!!");
      }
    }
    return true;
  }

  private boolean checkDateField(String date, String label) {
    if (!(portfolio.checkIfRightFormat(date)) || date.length() < 10) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("Date is not entered in YYYY-DD-MM format!");
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("Date is not entered in YYYY-DD-MM format!");
      } else if (label.equals("costBasis")) {
        guiView.setCostBasisDialogStatus("Date is not entered in YYYY-DD-MM format!");
      } else if (label.equals("valDate")) {
        guiView.setvalueDialogStatus("Date is not entered in YYYY-DD-MM format!");
      }
      return false;
    } else if (portfolio.checkFutureDate(date) || portfolio.checkTodayDateAndTime(date)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
      } else if (label.equals("costBasis")) {
        guiView.setCostBasisDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
      } else if (label.equals("valDate")) {
        guiView.setvalueDialogStatus("You can only enter past date or present(if after " +
                "9:30am).! Please enter new date");
      }
      return false;
    }
    return true;
  }

  private boolean checkStocksField(String numStocks, String label) {
    if (!portfolio.checkValidInteger(numStocks)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("Number of stocks given is not a valid integer");
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("Number of stocks given is not a valid integer");
      }
      return false;
    }
    return true;
  }

  private boolean checkTickrField(String tickr, String label) throws FileNotFoundException {
    if (!portfolio.validateTickrSymbol(tickr)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                "tickr symbol!");
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                "tickr symbol!");
      }
      return false;
    } else if (portfolio.checkTickrJSONArray(this.addTickr, tickr)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("The tickr symbol already exists");
        return false;
      }
    }
    return true;
  }

  private boolean checkAllfields(String pfName, String tickr, String numStocks,
                                 String date) {
    if (pfName == null || tickr == null || numStocks == null || date == null ||
            pfName.length() == 0 || tickr.length() == 0 || numStocks.length() == 0 ||
            date.length() == 0) {
      return true;
    }
    return false;
  }

  private boolean checkCommissionField(String commissionCreate, String label) {
    if (commissionCreate != null && commissionCreate.length() > 0) {
      if (!(portfolio.checkValidInteger(commissionCreate)) ||
              !(portfolio.checkValidFloat(commissionCreate))) {
        if (label.equals("add")) {
          guiView.setcreateDialogStatus("Commission fees given is not a valid number");
        } else if (label.equals("modify")) {
          guiView.setmodifyDialogStatus("Commission fees given is not a valid number");
        }
        return false;
      } else if (Float.valueOf(commissionCreate) < 0) {
        if (label.equals("add")) {
          guiView.setcreateDialogStatus("Commission fees cannot be negative!");
        } else if (label.equals("modify")) {
          guiView.setmodifyDialogStatus("Commission fees cannot be negative!");
        }
        return false;
      }
    }
    return true;
  }

  private void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
                            String dateCreate, String commissionCreate)
          throws FileNotFoundException {
    if (checkAllfields(pfNameCreate, tickrCreate, numStocksCreate, dateCreate)) {
      guiView.setcreateDialogStatus("All the fields are not given!!");
    } else {
      Float commission = 0.0f;
      if (checkPortfolioField(pfNameCreate, "add")) {
        if (checkDateField(dateCreate, "add")) {
          if (checkStocksField(numStocksCreate, "add")) {
            if (checkTickrField(tickrCreate, "add")) {
              if (checkCommissionField(commissionCreate, "add")) {
                if (commissionCreate != null || commissionCreate.length() > 0) {
                  commission = Float.valueOf(commissionCreate);
                }
                JSONObject addEntry = portfolio.makeTransactionRecord(dateCreate,
                        commission, Integer.valueOf(numStocksCreate), tickrCreate);
                JSONArray listEntry = new JSONArray();
                listEntry.add(addEntry);
                this.addTickr.put(tickrCreate, listEntry);
                guiView.setcreateDialogStatus("Entry added sucessfully!. Click Add to add more " +
                        "entries or Save to save the portfolio.");
              }
            }
          }
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

  private void validateTickrModify(String pfNameModify, String tickrModify, String numStocksModify,
                                   String dateModify, Float commission, String label,
                                   String labelStatus) throws FileNotFoundException,
          ParseException {
    if (labelStatus.equals("sell")) {
      if (!portfolio.ifTickrInPf(this.rootDir + pfNameModify + ".json", tickrModify)) {
        guiView.setmodifyDialogStatus("No stocks for this tickr exists to sell.");
      } else if (!portfolio.checkValidSell(this.rootDir + pfNameModify + ".json",
              Integer.valueOf(numStocksModify), tickrModify, dateModify)) {
        guiView.setmodifyDialogStatus("The number entered for selling stocks is more than " +
                "stocks purchased");
      } else {
        portfolio.modifyJson(commission, Integer.valueOf(numStocksModify) * (-1),
                dateModify, tickrModify, this.rootDir + pfNameModify + ".json");
        guiView.setmodifyDialogStatus("The portfolio " + pfNameModify +
                "is successfully modified");
      }
    } else if (labelStatus.equals("purchase")) {
      if (checkTickrField(tickrModify, label)) {
        portfolio.modifyJson(commission, Integer.valueOf(numStocksModify), dateModify,
                tickrModify, this.rootDir + pfNameModify + ".json");
        guiView.setmodifyDialogStatus("The portfolio " + pfNameModify +
                " is successfully modified");
      }
    }
  }

  private void modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                              String dateModify, String commissionModify, String statuslabel)
          throws FileNotFoundException, ParseException {
    if (checkAllfields(pfNameModify, tickrModify, numStocksModify, dateModify)) {
      guiView.setmodifyDialogStatus("All the fields are not given!!");
    } else {
      Float commission = 0.0f;
      if (checkPortfolioField(pfNameModify, "modify")) {
        if (checkDateField(dateModify, "modify")) {
          if (checkStocksField(numStocksModify, "modify")) {
            if (checkCommissionField(commissionModify, "add")) {
              if (commissionModify != null || commissionModify.length() > 0) {
                commission = Float.valueOf(commissionModify);
              }
              validateTickrModify(pfNameModify, tickrModify, numStocksModify, dateModify,
                      commission, "modify", statuslabel);
            }
          }
        }
      }
    }
  }

  private void validateDateVal(String pfNamedate, String dateValue)
          throws FileNotFoundException, ParseException {
    if (pfNamedate == null || pfNamedate.length() == 0 ||
            dateValue == null || dateValue.length() == 0) {
      guiView.setvalueDialogStatus("All the values are not given!!");
    } else {
      if (checkPortfolioField(pfNamedate, "valDate")) {
        if (checkDateField(dateValue, "valDate")) {
          float totVal = portfolio.portfolioValueDate(this.rootDir, pfNamedate, dateValue);
          guiView.setvalueDialogStatus("Portfolio Value on " + dateValue + " is : $" + totVal);
        }
      }
    }
  }

  private void validateCostBasis(String pfNameBasis, String dateBasis) throws ParseException {
    if (pfNameBasis == null || pfNameBasis.length() == 0 ||
            dateBasis == null || dateBasis.length() == 0) {
      guiView.setCostBasisDialogStatus("All the values are not given!!");
    } else {
      if (checkPortfolioField(pfNameBasis, "costBasis")) {
        if (checkDateField(dateBasis, "costBasis")) {
          float costBasis = portfolio.getCostBasis(this.rootDir + pfNameBasis
                  + ".json", dateBasis);
          guiView.setCostBasisDialogStatus("The cost basis till date, " + dateBasis + " is: $" +
                  costBasis);
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
        try {
          addOperation(guiView.getCreatePfValue(), guiView.gettickrcreateValue(),
                  guiView.getnumstockscreateValue(), guiView.getdateofcreationValue(),
                  guiView.getcommissionfeescreateValue());
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
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
        try {
          modifyValidate(guiView.getModifyPfValue(), guiView.gettickrmodifyValue(),
                  guiView.getnumstocksmodifyValue(), guiView.getdateofmodifynValue(),
                  guiView.getcommissionfeesmodifyValue(), "purchase");
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
      break;
      case "Sell": {
        try {
          modifyValidate(guiView.getModifyPfValue(), guiView.gettickrmodifyValue(),
                  guiView.getnumstocksmodifyValue(), guiView.getdateofmodifynValue(),
                  guiView.getcommissionfeesmodifyValue(), "sell");
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
      break;
      case "Get Value": {
        if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
          guiView.setValueLabelStatus("Please specify the root directory path!!");
        } else {
          guiView.displayValuepf();
        }
      }
      break;
      case "Compute Value of Pf": {
        try {
          validateDateVal(guiView.getpfnameVal(), guiView.getdateVal());
        } catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
      break;
      case "Get Cost Basis": {
        if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
          guiView.setLabelCostBasisStatus("Please specify the root directory path!!");
        } else {
          guiView.displayCostBasis();
        }
      }
      break;
      case "Compute Cost Basis": {
        try {
          validateCostBasis(guiView.pfNameCostBasis(), guiView.getDate());
        } catch (ParseException e) {
          throw new RuntimeException(e);
        }
      }
      break;
    }
  }
}
