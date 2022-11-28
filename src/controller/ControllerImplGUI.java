package controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;

import model.PortfolioStratergy;
import view.GUIView;

public class ControllerImplGUI implements ControllerGUI {
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

  private boolean checkPortfolioField(String pfName, String label) {

    boolean fileCheck = new File(this.rootDir + pfName + ".json").exists();
    if (label.equals("add") || label.equals("save")) {
      if (!checkValidpfName(pfName)) {
        guiView.setcreateDialogStatus("Please enter a valid Portfolio name!!");
        guiView.setCreatePfValue(null);
        return false;
      }
    }
    else if(label.equals("dollarnew")){
      guiView.setdollarnewpanestatus("Please enter a valid Portfolio name!!");
      guiView.setpfnamedollarnew(null);
      return false;
    }
    if (fileCheck) {
      if (label.equals("add") || label.equals("save")) {
        guiView.setcreateDialogStatus("Portfolio with this name " +
                pfName + " already exists!!");
        guiView.setCreatePfValue(null);
        return false;
      }
      else if(label.equals("dollarnew")){
        guiView.setdollarnewpanestatus("Portfolio with this name " +
                pfName + " already exists!!");
        guiView.setpfnamedollarnew(null);
        return false;
      }
    } else {
      switch (label) {
        case "modify":
          guiView.setmodifyDialogStatus("Portfolio with this name " + pfName + " does not exist!!");
          guiView.setModifyPfValue(null);
          return false;
        case "costBasis":
          guiView.setCostBasisDialogStatus("Portfolio with this name " + pfName
                  + " does not exist!!");
          guiView.setpfNameCostBasis(null);
          return false;
        case "valDate":
          guiView.setvalueDialogStatus("Portfolio with this name " + pfName + "does not exist!!");
          guiView.setpfnameVal(null);
          return false;
        case "composition":
          guiView.setretrieveDialogStatus("Portfolio with this name " + pfName + " does not exist!!");
          guiView.setpfnameretrieve(null);
          guiView.setPortfoliosListComposition(null);
          return false;
        case "dollarexist":
          guiView.setdollarexistpanestatus("Portfolio with this name " + pfName
                  + " does not exist!!");
          guiView.setpfNameExistDollar(null);
          return false;
      }
    }
    return true;
  }

  private boolean checkDateField(String date, String label) {
    if (!(portfolio.checkIfRightFormat(date)) || date.length() < 10) {
      switch (label) {
        case "add":
          guiView.setcreateDialogStatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setdateofcreationValue(null);
          break;
        case "modify":
          guiView.setmodifyDialogStatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setdateofmodifynValue(null);
          break;
        case "costBasis":
          guiView.setCostBasisDialogStatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setDate(null);
          break;
        case "valDate":
          guiView.setvalueDialogStatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setdateVal(null);
          break;
        case "dollarexist":
          guiView.setdollarexistpanestatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setdollardateexist(null);
          break;
        case "dollarnewstartdate":
          guiView.setdollarnewpanestatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setstartdatenew(null);
          break;
        case "dollarnewenddate":
          guiView.setdollarnewpanestatus("Date is not entered in YYYY-DD-MM format!");
          guiView.setenddatenew(null);
          break;
      }
      return false;
    } else if (portfolio.checkFutureDate(date) || portfolio.checkTodayDateAndTime(date)) {
      switch (label) {
        case "add":
          guiView.setcreateDialogStatus("You can only enter past date or present(if after " +
                  "9:30am).! Please enter new date");
          guiView.setdateofcreationValue(null);
          break;
        case "modify":
          guiView.setmodifyDialogStatus("You can only enter past date or present(if after " +
                  "9:30am).! Please enter new date");
          guiView.setdateofmodifynValue(null);
          break;
        case "costBasis":
          guiView.setCostBasisDialogStatus("You can only enter past date or present(if after " +
                  "9:30am).! Please enter new date");
          guiView.setDate(null);
          break;
        case "valDate":
          guiView.setvalueDialogStatus("You can only enter past date or present(if after " +
                  "9:30am).! Please enter new date");
          guiView.setdateVal(null);
          break;
        case "dollarexist":
        case "dollarnew":
          return true;
      }
      return false;
    }
    return true;
  }

  private boolean checkStocksField(String numStocks, String label) {
    if (!portfolio.checkValidInteger(numStocks)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("Number of stocks given is not a valid integer");
        guiView.setnumstockscreateValue(null);
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("Number of stocks given is not a valid integer");
        guiView.setnumstocksmodifyValue(null);
      }
      return false;
    } else {
      if (Integer.parseInt(numStocks) < 0) {
        if (label.equals("add")) {
          guiView.setcreateDialogStatus("Number of stocks cannot be negative");
          guiView.setnumstockscreateValue(null);
        } else if (label.equals("modify")) {
          guiView.setmodifyDialogStatus("Number of stocks cannot be negative");
          guiView.setnumstocksmodifyValue(null);
        }
        return false;
      }
    }
    return true;
  }

  private boolean checkTickrField(String tickr, String label) throws FileNotFoundException {
    if (!portfolio.validateTickrSymbol(tickr)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                "tickr symbol!");
        guiView.settickrcreateValue(null);
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus("Invalid Tickr Symbol! Enter valid company " +
                "tickr symbol!");
        guiView.settickrmodifyValue(null);
      }
      return false;
    } else if (portfolio.checkTickrJSONArray(this.addTickr, tickr)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus("The tickr symbol already exists");
        guiView.settickrcreateValue(null);
        return false;
      }
    }
    return true;
  }

  private boolean checkAllfields(String pfName, String tickr, String numStocks,
                                 String date) {
    return pfName == null || tickr == null || numStocks == null || date == null ||
            pfName.length() == 0 || tickr.length() == 0 || numStocks.length() == 0 ||
            date.length() == 0;
  }

  private boolean checkAllFieldsDollarExist(String dollarexistpfname, String stocksexist,
                                            String weightsexist, String dollarexistval,
                                            String dollarexistdate) {
    return dollarexistpfname == null || stocksexist == null || weightsexist == null ||
            dollarexistval == null || dollarexistdate == null || dollarexistpfname.length() == 0 ||
            stocksexist.length() == 0 || weightsexist.length() == 0 ||
            dollarexistval.length() == 0 || dollarexistdate.length() == 0;
  }

  private boolean checkAllFieldsDollarNew(String dollarnewcreatepfname,
                                          String stocksnew, String weightsnew, String dollarnewval,
                                          String dollarnewdays, String dollarnewstartdate) {
    return dollarnewcreatepfname == null || stocksnew == null || weightsnew == null ||
            dollarnewval == null || dollarnewdays == null || dollarnewstartdate == null ||
            dollarnewcreatepfname.length() == 0 || stocksnew.length() == 0 ||
            weightsnew.length() == 0 || dollarnewval.length() == 0 || dollarnewdays.length() == 0 ||
            dollarnewstartdate.length() == 0;
  }

  private boolean checkCommissionField(String commissionCreate, String label) {
    if (commissionCreate == null || commissionCreate.length() == 0) {
      return true;
    }
    if (commissionCreate != null || commissionCreate.length() > 0) {
      if (!(portfolio.checkValidInteger(commissionCreate)) &&
              !(portfolio.checkValidFloat(commissionCreate))) {
        switch (label) {
          case "add":
            guiView.setcreateDialogStatus("Commission fees given is not a valid number");
            guiView.setcommissionfeescreateValue(null);

            break;
          case "modify":
            guiView.setmodifyDialogStatus("Commission fees given is not a valid number");
            guiView.setcommissionfeesmodifyValue(null);
            break;
          case "dollarexist":
            guiView.setdollarexistpanestatus("Commission fees given is not a valid number");
            guiView.setdollarexistcommisionval(null);
            break;
        }
        return false;
      } else if (Float.valueOf(commissionCreate) < 0) {
        switch (label) {
          case "add":
            guiView.setcreateDialogStatus("Commission fees cannot be negative!");
            guiView.setcommissionfeescreateValue(null);
            break;
          case "modify":
            guiView.setmodifyDialogStatus("Commission fees cannot be negative!");
            guiView.setcommissionfeesmodifyValue(null);
            break;
          case "dollarexist":
            guiView.setdollarexistpanestatus("Commission fees cannot be negative!");
            guiView.setdollarexistcommisionval(null);
            break;
        }
        return false;
      }
    }
    return true;
  }

  private void validateTickrModify(String pfNameModify, String tickrModify, String numStocksModify,
                                   String dateModify, Float commission, String label,
                                   String labelStatus) throws FileNotFoundException,
          ParseException {
    if (labelStatus.equals("sell") || labelStatus.equals("purchase")) {
      boolean checkLabel = true;
      if (labelStatus.equals("sell")) {
        if (!portfolio.ifTickrInPf(this.rootDir + pfNameModify + ".json", tickrModify)) {
          guiView.setmodifyDialogStatus("No stocks for this tickr exists to sell.");
          checkLabel = false;
          guiView.settickrmodifyValue(null);
        } else if (!portfolio.checkValidSell(this.rootDir + pfNameModify + ".json",
                Integer.valueOf(numStocksModify), tickrModify, dateModify)) {
          guiView.setmodifyDialogStatus("The number entered for selling stocks is more than " +
                  "stocks purchased");
          checkLabel = false;
          guiView.setnumstocksmodifyValue(null);
        } else {
          JSONObject pf = portfolio.readPortfolio(this.rootDir + pfNameModify + ".json");
          pf = portfolio.modifyJson(commission, Integer.valueOf(numStocksModify) * (-1),
                  dateModify, tickrModify, pf);
          portfolio.savePortfolio(this.rootDir + pfNameModify + ".json", pf);
        }
      } else if (labelStatus.equals("purchase")) {
        if(!portfolio.validateTickrSymbol(tickrModify)){
          guiView.setmodifyDialogStatus("INvalid Tickr Symbol is entered!!");
          checkLabel = false;
          guiView.settickrmodifyValue(null);
        }
        else{
          JSONObject pf = portfolio.readPortfolio(this.rootDir + pfNameModify + ".json");
          portfolio.modifyJson(commission, Integer.valueOf(numStocksModify), dateModify,
                  tickrModify, pf);
          portfolio.savePortfolio(this.rootDir + pfNameModify + ".json", pf);
        }
      }
      if (checkLabel) {
        guiView.setmodifyDialogStatus("The portfolio " + pfNameModify +
                " is successfully modified");
        guiView.setdateofmodifynValue(null);
        guiView.settickrmodifyValue(null);
        guiView.setnumstocksmodifyValue(null);
        if (commission != 0.0f) {
          guiView.setcommissionfeesmodifyValue(null);
        }
      }
    }
  }

  public void setDirectory() {
    if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
      this.rootDirUser = this.rootDir;
      guiView.setpathStore("No path is given hence " +
              this.rootDir + " is set by default.");
      guiView.setCreateLabelStatus(null);
      guiView.setModifyLabelStatus(null);
      guiView.setValueLabelStatus(null);
      guiView.setLabelCostBasisStatus(null);
      guiView.setRetrievePanelStatus(null);
      guiView.setdollarExistingStatus(null);
      guiView.setdollarNewStatus(null);
    } else if (new File(this.rootDirUser).exists()) {
      guiView.setpathStore("Portfolios can be accessed in the " + this.rootDirUser + " location ");
      if (!portfolio.checkLastEndingCharacter(this.rootDirUser)) {
        this.rootDirUser = this.rootDirUser + "/";
      }
      this.rootDir = this.rootDirUser;
      guiView.setCreateLabelStatus(null);
      guiView.setModifyLabelStatus(null);
      guiView.setValueLabelStatus(null);
      guiView.setLabelCostBasisStatus(null);
      guiView.setRetrievePanelStatus(null);
      guiView.setdollarExistingStatus(null);
      guiView.setdollarNewStatus(null);
    } else {
      guiView.setpathStore("Invalid path given so portfolios will be stored in " +
              this.rootDir + " by default.");
      guiView.setCreateLabelStatus(null);
      guiView.setModifyLabelStatus(null);
      guiView.setValueLabelStatus(null);
      guiView.setLabelCostBasisStatus(null);
      guiView.setRetrievePanelStatus(null);
      guiView.setdollarExistingStatus(null);
      guiView.setdollarNewStatus(null);
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

  public void saveOperation(String pfNameCreate) {
    if (pfNameCreate == null || pfNameCreate.length() == 0) {
      guiView.setcreateDialogStatus("Portfolio Name is to be given!!");
    } else {
      if (checkPortfolioField(pfNameCreate, "save")) {
        portfolio.savePortfolio(this.rootDir + pfNameCreate + ".json",
                this.addTickr);
        guiView.setcreateDialogStatus("Successfully created the portfolio " + pfNameCreate);
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

  public void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
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
                if (commissionCreate != null && commissionCreate.length() > 0) {
                  commission = Float.valueOf(commissionCreate);
                }
                JSONObject addEntry = portfolio.makeTransactionRecord(dateCreate,
                        commission, Integer.valueOf(numStocksCreate), tickrCreate);
                JSONArray listEntry = new JSONArray();
                listEntry.add(addEntry);
                this.addTickr.put(tickrCreate, listEntry);
                guiView.setcreateDialogStatus("Entry added sucessfully!. Click Add to add more " +
                        "entries or Save to save the portfolio.");
                guiView.settickrcreateValue(null);
                guiView.setnumstockscreateValue(null);
                guiView.setdateofcreationValue(null);
                guiView.setcommissionfeescreateValue(null);
              }
            }
          }
        }
      }
    }
  }

  public void modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                             String dateModify, String commissionModify, String statuslabel)
          throws FileNotFoundException, ParseException {
    if (checkAllfields(pfNameModify, tickrModify, numStocksModify, dateModify)) {
      guiView.setmodifyDialogStatus("All the fields are not given!!");
    } else {
      Float commission = 0.0f;
      if (checkPortfolioField(pfNameModify, "modify")) {
        if (checkDateField(dateModify, "modify")) {
          if (checkStocksField(numStocksModify, "modify")) {
            if (checkCommissionField(commissionModify, "modify")) {
              if (commissionModify != null && commissionModify.length() > 0) {
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

  public void validateDateVal(String pfNamedate, String dateValue)
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

  public void validateCostBasis(String pfNameBasis, String dateBasis) throws ParseException {
    if (pfNameBasis == null || pfNameBasis.length() == 0 ||
            dateBasis == null || dateBasis.length() == 0) {
      guiView.setCostBasisDialogStatus("All the values are not given!!");
    } else {
      if (checkPortfolioField(pfNameBasis, "costBasis")) {
        if (checkDateField(dateBasis, "costBasis")) {
          float costBasis = portfolio.getCostBasis(this.rootDir + pfNameBasis
                  + ".json", dateBasis);
          guiView.setCostBasisDialogStatus("The cost basis till date " + dateBasis + " is: $" +
                  costBasis);
        }
      }
    }
  }

  private String viewFlexibleComposition(JSONObject portfolioObj) {
    StringBuilder message = new StringBuilder();
    if (portfolioObj.size() == 0) {
      message = new StringBuilder("This portfolio is empty!!");
    } else {
      for (Object tickrsym : portfolioObj.keySet()) {
        StringBuilder objmessage = new StringBuilder();
        objmessage.append("TICKER SYMBOL : ").append(tickrsym).append("\n");
        objmessage.append("NUM OF STOCKS        TYPE            DATE OF PURCHASE/SELL" + "     " +
                "COMMISSION FEES       STOCK PRICE" + "\n");
        JSONArray arrayObj = (JSONArray) portfolioObj.get(tickrsym);
        for (Object o : arrayObj) {
          JSONObject tickrRecord = (JSONObject) o;
          Double noOfStocks = (Double) (tickrRecord.get("no_of_stocks"));
          String type = "PURCHASED";
          if (noOfStocks < 0) {
            noOfStocks = noOfStocks * (-1);
            type = "SOLD";
          }
          objmessage.append(" ").append(noOfStocks).append("\t  ").
                  append(type).append("\t\t").append(tickrRecord.get("date")).
                  append("\t").append("\t$").append(tickrRecord.get("commission_fee")).
                  append("\t$").append(tickrRecord.get("stock_price")).append("\n");
        }
        message.append(objmessage).append("\n");
      }
    }
    return message.toString();
  }

  public void getCompositionpf(String pfNameComposition) {
    if (pfNameComposition == null || pfNameComposition.length() == 0) {
      guiView.setretrieveDialogStatus("All the values are not given!!");
    } else {
      if (checkPortfolioField(pfNameComposition, "composition")) {
        JSONObject portfolioObj = portfolio.readPortfolio(this.rootDir +
                pfNameComposition + ".json");

        guiView.setPortfoliosListComposition(viewFlexibleComposition(portfolioObj));
        guiView.setpfnameretrieve(null);
      }
    }
  }

  private boolean checkBatchTickrField(String stocksexist, String label) {
    if(portfolio.checkforInvalidcharacters(stocksexist)){
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("Wrong Format is entered!!");
        guiView.setstocksexist(null);
      }
      else if(label.equals("dollarnew")){
        guiView.setdollarnewpanestatus("Wrong format is entered!!");
        guiView.setstocksnew(null);
      }
      return false;
    }
    else{
      ArrayList<String> stocks = portfolio.validateTickrEntries(stocksexist);
      if (stocks.size() == 0) {
        if (label.equals("dollarexist")) {
          guiView.setdollarexistpanestatus("Invalid Tickr symbol is entered!!");
          guiView.setstocksexist(null);
        }
        else if(label.equals("dollarnew")){
          guiView.setdollarnewpanestatus("Invalid Tickr symbol is entered!!");
          guiView.setstocksnew(null);
        }
        return false;
      }
      else{
        if(portfolio.checkDuplicates(stocks)){
          if (label.equals("dollarexist")) {
            guiView.setdollarexistpanestatus("Duplicate tickr symbols exist!!");
            guiView.setstocksexist(null);
          }
          else if(label.equals("dollarnew")){
            guiView.setdollarnewpanestatus("Duplicate tickr symbols exist!!");
            guiView.setstocksnew(null);
          }
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkBatchWeightFields(String weightsexist, String label) {
    if (!portfolio.validateWeightFormat(weightsexist)) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("Format of the the weights is not correct");
        guiView.setweightsexist(null);
        return false;
      }
      else if(label.equals("dollarnew")){
        guiView.setdollarnewpanestatus("Format of the the weights is not correct");
        guiView.setweightsnew(null);
        return false;
      }
    } else {
      ArrayList<Float> weights = portfolio.validateWeightEntriesSum(weightsexist);
      if (weights.size() == 0) {
        if (label.equals("dollarexist")) {
          guiView.setdollarexistpanestatus("Sum of the given weights is not equal to 100%");
          guiView.setweightsexist(null);
          return false;
        }
        else if(label.equals("dollarnew")){
          guiView.setdollarnewpanestatus("Sum of the given weights is not equal to 100%");
          guiView.setweightsnew(null);
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkBatchTickrBatchWeightsSize(String stocksexist, String weightsexist,
                                                  String label) {
    if (!checkBatchTickrField(stocksexist, label)) {
      return false;
    }
    ArrayList<Float> checkingtickrweights = portfolio.validateStockWeightEntries(weightsexist,
            portfolio.validateTickrEntries(stocksexist).size());
    if (checkingtickrweights.size() == 0) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("The number of tickr symbols given and the number " +
                "corresponding weights given do not match");
        guiView.setstocksexist(null);
        guiView.setweightsexist(null);
      }
      else if(label.equals("dollarnew")){
        guiView.setdollarnewpanestatus("The number of tickr symbols given and the number " +
                "corresponding weights given do not match");
        guiView.setstocksnew(null);
        guiView.setweightsnew(null);
      }
      return false;
    }
    return true;
  }

  private boolean checkValidMoney(String dollarexistval, String label) {
    if (!portfolio.checkValidInteger(dollarexistval) &&
            !portfolio.checkValidInteger(dollarexistval)) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("Money can be either an integer or a float value!!");
        guiView.setdollarexistval(null);
      }
      else if(label.equals("dollarnew")){
        guiView.setdollarnewpanestatus("Money can be either an integer or a float value!!");
        guiView.setdollarnewval(null);
      }
      return false;
    } else if (Float.parseFloat(dollarexistval) < 0) {
      if (label.equals("dollarexist")) {
        guiView.setdollarnewpanestatus("Negative money is not allowed!!!");
        guiView.setdollarexistval(null);
      }
      else if(label.equals("dollarnew")){
        guiView.setdollarnewpanestatus("Negative money is not allowed!!!");
        guiView.setdollarnewval(null);
      }
      return false;
    }
    return true;
  }

  public void validateExistingDollar(String stratergydollarexistname, String dollarexistpfname,
                                     String stocksexist, String weightsexist, String dollarexistval,
                                     String dollarexistdate, String dollarexistcommision) {
    // add strategy to add strategy.
    if (checkAllFieldsDollarExist(dollarexistpfname, stocksexist, weightsexist, dollarexistval,
            dollarexistdate)) {
      guiView.setdollarexistpanestatus("All the fields are not given!!");
    } else {
      Float commission = 0.0f;
      if (checkPortfolioField(dollarexistpfname, "dollarexist")) {
        if (checkBatchTickrField(stocksexist, "dollarexist")) {
          if (checkBatchWeightFields(weightsexist, "dollarexist")) {
            if (checkBatchTickrBatchWeightsSize(stocksexist, weightsexist, "dollarexist")) {
              if (checkValidMoney(dollarexistval, "dollarexist")) {
                if (checkDateField(dollarexistdate, "dollarexist")) {
                  if (checkCommissionField(dollarexistcommision, "dollarexist")) {
                    if (dollarexistcommision != null && dollarexistcommision.length() > 0) {
                      commission = Float.valueOf(dollarexistcommision);
                    }
                    JSONObject portfolioObj = portfolio.readPortfolio(
                            this.rootDir + dollarexistpfname + ".json");
                    JSONObject finalObj = portfolio.dollarCostExisting(
                            portfolio.validateTickrEntries(stocksexist),
                            portfolio.validateWeightEntriesSum(weightsexist), commission,
                            Float.parseFloat(dollarexistval), dollarexistdate, portfolioObj);
                    portfolio.savePortfolio(this.rootDir + dollarexistpfname + ".json",
                            finalObj);
                    guiView.setdollarexistpanestatus("Strategy successfully applied to the " +
                            "portfolio" + dollarexistpfname);
                    guiView.setdollardateexist(null);
                    guiView.setdollarexistcommisionval(null);
                    guiView.setstocksexist(null);
                    guiView.setweightsexist(null);
                    guiView.setdollarexistval(null);
                    guiView.setstrategynameexist(null);
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void validateNewDollar(String stratergydollarnewname, String dollarnewcreatepfname,
                                String stocksnew, String weightsnew, String dollarnewval,
                                String dollarnewdays, String dollarnewstartdate,
                                String dollarnewenddate, String dollarnewcommission) {
    // do not know what to do with strategy name.
    // add strategy to add strategy.
    if (checkAllFieldsDollarNew(dollarnewcreatepfname, stocksnew, weightsnew, dollarnewval,
            dollarnewdays, dollarnewstartdate)) {
      guiView.setdollarnewpanestatus("All the fields are not given !!");
    }
    else{
      Float commission = 0.0f;
      if(checkPortfolioField(dollarnewcreatepfname,"dollarnew")){
        if (checkBatchTickrField(stocksnew, "dollarnew")){
          if(checkBatchWeightFields(weightsnew,"dollarnew")){
            if (checkBatchTickrBatchWeightsSize(stocksnew, stocksnew, "dollarnew")){
              if(checkValidMoney(dollarnewval,"dollarnew")){
                if(checkDateField(dollarnewstartdate,"dollarnewstartdate")){
                  if(checkDateField(dollarnewenddate,"dollarnewenddate")){
                    if(portfolio.checkValidInteger(dollarnewdays)){
                      // check number of days.
                      // check commision fees.
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void displaysetrootpane(){
    this.rootDirUser = guiView.givenPath();
    setDirectory();
  }
  public void displayDialogPane(String label) {
    if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
      switch (label) {
        case "costBasis":
          guiView.setLabelCostBasisStatus("Please specify the root directory path!!");
          break;
        case "modify":
          guiView.setModifyLabelStatus("Please specify the root directory path!!");
          break;
        case "getDateVal":
          guiView.setValueLabelStatus("Please specify the root directory path!!");
          break;
        case "create":
          guiView.setCreateLabelStatus("Please specify the root directory path!!");
          break;
        case "composition":
          guiView.setRetrievePanelStatus("Please specify the root directory path!!");
          break;
        case "dollarexist":
          guiView.setdollarExistingStatus("Please specify the root directory path!!");
          break;
        case "dollarnew":
          guiView.setdollarNewStatus("Please specify the root directory path!!");
          break;
      }
    } else {
      String message = portfolio.listJSONfiles(this.rootDir);
      if (message == null || message.length() == 0) {
        message = "No portfolio exists in the given path. Create a new portfolio.";
      }
      switch (label) {
        case "create":
          this.addTickr = new JSONObject();
          guiView.setCreateLabelStatus(null);
          guiView.setcreateDialogStatus(null);
          guiView.displayCreatePf();
          break;
        case "costBasis":
          guiView.setLabelCostBasisStatus(null);
          guiView.setPortfoliosListBasis(message);
          guiView.displayCostBasis();
          break;
        case "modify":
          guiView.setModifyLabelStatus(null);
          guiView.setportfoliosListModify(message);
          guiView.displayModifyPf();
          break;
        case "getDateVal":
          guiView.setValueLabelStatus(null);
          guiView.setPortfoliosListVal(message);
          guiView.displayValuepf();
          break;
        case "composition":
          guiView.setRetrievePanelStatus(null);
          guiView.setPortfoliosListRetrieve(message);
          guiView.displayRetrievepf();
          break;
        case "dollarexist":
          guiView.setdollarExistingStatus(null);
          guiView.setportfolioslistdollarexist(message);
          guiView.displayDollarExistingpf();
          break;
        case "dollarnew":
          guiView.setdollarNewStatus(null);
          guiView.setdollarexistpanestatus(null);
          guiView.displayDollarNewpf();
          break;
      }
    }
  }

  public void goStocks() throws ParseException, IOException {
    guiView.makeVisible();
    guiView.addFeatures(this);
  }
}
