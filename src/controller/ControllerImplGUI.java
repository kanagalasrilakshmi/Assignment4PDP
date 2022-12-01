package controller;

import model.FileOperation;
import model.FileOperationImplementation;
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

import model.PortfolioStratergy;
import view.GUIView;

/**
 * Implements Controller interface for running the stocks program for using graphical based UI.
 * creation of portfolio.
 * Viewing created portfolios.
 * Getting value of a portfolio on a given date.
 * Implementing dollar cost averaging on existing portfolio.
 * Implementing start-to-finish dollar averaging strategy.
 * And Quitting the program.
 */
public class ControllerImplGUI implements ControllerGUI {
  private final GUIView guiView;
  private final PortfolioStratergy portfolio;
  private String rootDir;
  private String rootDirUser;
  private JSONObject addTickr;

  private final FileOperation fileOperation;


  /**
   * Is the Controller constructor that helps to implement the graphical based view.
   *
   * @param portfolio is the portfolio strategy type object
   * @param guiView   is the guiview object that is used for the implementing graphical UI
   */
  public ControllerImplGUI(PortfolioStratergy portfolio, GUIView guiView) {
    this.guiView = guiView;
    this.portfolio = portfolio;
    this.rootDir = System.getProperty("user.home") + "/Desktop/PortfolioBucket/";
    this.fileOperation = new FileOperationImplementation();
  }

  private boolean checkPortfolioField(String pfName, String label) {

    boolean fileCheck = new File(this.rootDir + pfName + ".json").exists();
    String invalidPfMsg = "Please enter a valid Portfolio name!! Valid names can only contain " +
            "characters and can be of length less than 25 characters.";
    if (label.equals("add") || label.equals("save")) {
      if (!checkValidpfName(pfName)) {
        guiView.setcreateDialogStatus(invalidPfMsg);
        guiView.setCreatePfValue(null);
        return false;
      }
    } else if (label.equals("dollarnew")) {
      if (!checkValidpfName(pfName)) {
        guiView.setdollarnewpanestatus(invalidPfMsg);
        guiView.setpfnamedollarnew(null);
        return false;
      }
    }
    if (fileCheck && !(pfName.equals("stratergyLookup"))) {
      String alreadyExists = "Portfolio with this name " + pfName + " already exists! ";
      if (label.equals("add") || label.equals("save")) {
        guiView.setcreateDialogStatus(alreadyExists + "Type a new  name in the enter portfolio " +
                "name field.");
        guiView.setCreatePfValue(null);
        return false;
      } else if (label.equals("dollarnew")) {
        guiView.setdollarnewpanestatus(alreadyExists + "So this new strategy will be added!!");

        return true;
      }
    } else {
      String invalidPfNameMsg = "Portfolio with this name " + pfName + " does not exist!!";
      switch (label) {
        case "modify":
          guiView.setmodifyDialogStatus(invalidPfNameMsg);
          guiView.setModifyPfValue(null);
          return false;
        case "costBasis":
          guiView.setCostBasisDialogStatus(invalidPfNameMsg);
          guiView.setpfNameCostBasis(null);
          return false;
        case "valDate":
          guiView.setvalueDialogStatus(invalidPfNameMsg);
          guiView.setpfnameVal(null);
          return false;
        case "composition":
          guiView.setretrieveDialogStatus(invalidPfNameMsg);
          guiView.setpfnameretrieve(null);
          guiView.setPortfoliosListComposition(null);
          return false;
        case "dollarexist":
          guiView.setdollarexistpanestatus(invalidPfNameMsg);
          guiView.setpfNameExistDollar(null);
          return false;
        case "dollarnew":
          guiView.setdollarnewpanestatus("New Portfolio with name " + pfName + " is created");
          return true;
        default:
          // do nothing.
      }
    }
    return true;
  }

  private boolean checkDateField(String date, String label) {
    if (!(portfolio.checkIfRightFormat(date)) || date.length() < 10) {
      String invalidDateMsg = "Date is not entered in YYYY-DD-MM format! " +
              "Please enter a valid format date.";
      switch (label) {
        case "add":
          guiView.setcreateDialogStatus(invalidDateMsg);
          guiView.setdateofcreationValue(null);
          break;
        case "modify":
          guiView.setmodifyDialogStatus(invalidDateMsg);
          guiView.setdateofmodifynValue(null);
          break;
        case "costBasis":
          guiView.setCostBasisDialogStatus(invalidDateMsg);
          guiView.setDate(null);
          break;
        case "valDate":
          guiView.setvalueDialogStatus(invalidDateMsg);
          guiView.setdateVal(null);
          break;
        case "dollarexist":
          guiView.setdollarexistpanestatus(invalidDateMsg);
          guiView.setdollardateexist(null);
          break;
        case "dollarnewstartdate":
          guiView.setdollarnewpanestatus(invalidDateMsg);
          guiView.setstartdatenew(null);
          break;
        case "dollarnewenddate":
          if (date == null || date.equals("")) {
            return true;
          }
          guiView.setdollarnewpanestatus(invalidDateMsg);
          guiView.setenddatenew(null);
          break;
        default:
          // do nothing.
      }
      return false;
    } else if (portfolio.checkFutureDate(date) || portfolio.checkTodayDateAndTime(date)) {
      String invalidDateMsg = "You can only enter past date or present(if after " +
              "9:30am).! Please enter new date";
      switch (label) {
        case "add":
          guiView.setcreateDialogStatus(invalidDateMsg);
          guiView.setdateofcreationValue(null);
          break;
        case "modify":
          guiView.setmodifyDialogStatus(invalidDateMsg);
          guiView.setdateofmodifynValue(null);
          break;
        case "valDate":
          guiView.setvalueDialogStatus(invalidDateMsg);
          guiView.setdateVal(null);
          break;
        case "dollarnewstartdate":
        case "dollarnewenddate":
        case "dollarexist":
        case "costBasis":
          return true;
        default:
          // nothing
      }
      return false;
    }
    return true;
  }

  private boolean checkStocksField(String numStocks, String label) {
    String invalidStocksNumber = "Number of stocks given is not a valid positive integer";
    if (!portfolio.checkValidInteger(numStocks)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus(invalidStocksNumber);
        guiView.setnumstockscreateValue(null);
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus(invalidStocksNumber);
        guiView.setnumstocksmodifyValue(null);
      }
      return false;
    } else {
      if (Integer.parseInt(numStocks) < 0) {
        if (label.equals("add")) {
          guiView.setcreateDialogStatus(invalidStocksNumber);
          guiView.setnumstockscreateValue(null);
        } else if (label.equals("modify")) {
          guiView.setmodifyDialogStatus(invalidStocksNumber);
          guiView.setnumstocksmodifyValue(null);
        }
        return false;
      }
    }
    return true;
  }

  private boolean checkTickrField(String tickr, String label) throws FileNotFoundException {
    String invalidTickrMsg = "Invalid Tickr Symbol! Enter valid company tickr symbol!";
    if (!portfolio.validateTickrSymbol(tickr)) {
      if (label.equals("add")) {
        guiView.setcreateDialogStatus(invalidTickrMsg);
        guiView.settickrcreateValue(null);
      } else if (label.equals("modify")) {
        guiView.setmodifyDialogStatus(invalidTickrMsg);
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

  private boolean checkAllFieldsDollarExist(String stratergydollarexistname,
                                            String dollarexistpfname,
                                            String stocksexist, String weightsexist,
                                            String dollarexistval, String dollarexistdate) {
    return stratergydollarexistname == null || dollarexistpfname == null || stocksexist == null ||
            weightsexist == null || dollarexistval == null || dollarexistdate == null ||
            stratergydollarexistname.length() == 0 || dollarexistpfname.length() == 0 ||
            stocksexist.length() == 0 || weightsexist.length() == 0 || dollarexistval.length() == 0
            || dollarexistdate.length() == 0;
  }

  private boolean checkAllFieldsDollarNew(String stratergydollarnewname,
                                          String dollarnewcreatepfname, String stocksnew,
                                          String weightsnew, String dollarnewval,
                                          String dollarnewdays, String dollarnewstartdate) {
    return stratergydollarnewname == null || dollarnewcreatepfname == null || stocksnew == null ||
            weightsnew == null || dollarnewval == null || dollarnewdays == null ||
            dollarnewstartdate == null || stratergydollarnewname.length() == 0 ||
            dollarnewcreatepfname.length() == 0 || stocksnew.length() == 0 ||
            weightsnew.length() == 0 || dollarnewval.length() == 0 ||
            dollarnewdays.length() == 0 || dollarnewstartdate.length() == 0;
  }

  private boolean checkCommissionField(String commissionCreate, String label) {
    if (commissionCreate == null || commissionCreate.length() == 0) {
      return true;
    }
    if (commissionCreate != null || commissionCreate.length() > 0) {
      if (!(portfolio.checkValidInteger(commissionCreate)) &&
              !(portfolio.checkValidFloat(commissionCreate))) {
        String invalidCommission = "Commission fees given is not a valid positive number";
        switch (label) {
          case "add":
            guiView.setcreateDialogStatus(invalidCommission);
            guiView.setcommissionfeescreateValue(null);
            break;
          case "modify":
            guiView.setmodifyDialogStatus(invalidCommission);
            guiView.setcommissionfeesmodifyValue(null);
            break;
          case "dollarexist":
            guiView.setdollarexistpanestatus(invalidCommission);
            guiView.setdollarexistcommisionval(null);
            break;
          case "dollarnew":
            guiView.setdollarnewpanestatus(invalidCommission);
            guiView.setdollarcommissionnew(null);
            break;
          default:
            // do nothing.
        }
        return false;
      } else if (Float.parseFloat(commissionCreate) < 0) {
        String invalidCommission = "Commission fees cannot be negative!";
        switch (label) {
          case "add":
            guiView.setcreateDialogStatus(invalidCommission);
            guiView.setcommissionfeescreateValue(null);
            break;
          case "modify":
            guiView.setmodifyDialogStatus(invalidCommission);
            guiView.setcommissionfeesmodifyValue(null);
            break;
          case "dollarexist":
            guiView.setdollarexistpanestatus(invalidCommission);
            guiView.setdollarexistcommisionval(null);
            break;
          case "dollarnew":
            guiView.setdollarnewpanestatus(invalidCommission);
            guiView.setdollarcommissionnew(null);
            break;
          default:
            // do nothing.
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
      //updateRecordsPf(pfNameModify);
      boolean checkLabel = true;
      if (labelStatus.equals("sell")) {
        if (!portfolio.ifTickrInPf(this.rootDir + pfNameModify + ".json", tickrModify)) {
          guiView.setmodifyDialogStatus("No stocks for this tickr symbol exists to sell. Please " +
                  "enter tickr symbol that exist in the given portfolio.");
          checkLabel = false;
          guiView.settickrmodifyValue(null);
        } else if (!portfolio.checkValidSell(this.rootDir + pfNameModify + ".json",
                Integer.parseInt(numStocksModify), tickrModify, dateModify)) {
          guiView.setmodifyDialogStatus("The number entered for selling stocks is more than " +
                  "stocks purchased. Please enter a valid value.");
          checkLabel = false;
          guiView.setnumstocksmodifyValue(null);
        } else {
          JSONObject pf = this.fileOperation.readPortfolio(this.rootDir + pfNameModify
                  + ".json");
          pf = portfolio.modifyJson(commission, Integer.parseInt(numStocksModify) * (-1),
                  dateModify, tickrModify, pf);
          this.fileOperation.savePortfolio(this.rootDir + pfNameModify + ".json", pf);
        }
      } else if (labelStatus.equals("purchase")) {
        if (!portfolio.validateTickrSymbol(tickrModify)) {
          guiView.setmodifyDialogStatus("Invalid Tickr Symbol is entered!!." +
                  " Please enter a valid tickr symbol");
          checkLabel = false;
          guiView.settickrmodifyValue(null);
        } else {
          JSONObject pf = this.fileOperation.readPortfolio(this.rootDir + pfNameModify + ".json");
          portfolio.modifyJson(commission, Integer.parseInt(numStocksModify), dateModify,
                  tickrModify, pf);
          this.fileOperation.savePortfolio(this.rootDir + pfNameModify + ".json", pf);
        }
      }
      if (checkLabel) {
        guiView.setmodifyDialogStatus("The portfolio " + pfNameModify + " is successfully " +
                "modified. Make a new purchase/sell on the given portfolio name or give new " +
                "portfolio name in portfolio name field. Click 'X' on top left if done " +
                "modifying portfolio.");
        guiView.setdateofmodifynValue(null);
        guiView.settickrmodifyValue(null);
        guiView.setnumstocksmodifyValue(null);
        if (commission != 0.0f) {
          guiView.setcommissionfeesmodifyValue(null);
        }
      }
    }
  }

  /**
   * Get the input path given by the user and validate, if exists set it as root directory,
   * else set the default path in initialized in the constructor. Creates strategy lookup json ,
   * to store the strategies applied.
   */
  public void setDirectory() {
    if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
      this.rootDirUser = this.rootDir;
      guiView.setpathStore("No path is given hence " + this.rootDir + " is set by default.");
      guiView.setCreateLabelStatus(null);
      guiView.setModifyLabelStatus(null);
      guiView.setValueLabelStatus(null);
      guiView.setLabelCostBasisStatus(null);
      guiView.setRetrievePanelStatus(null);
      guiView.setdollarExistingStatus(null);
      guiView.setdollarNewStatus(null);
      createStrategyLookup();
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
      createStrategyLookup();
    } else {
      createStrategyLookup();
      guiView.setpathStore("Invalid path given so portfolios will be stored in " + this.rootDir +
              " by default.");
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

  /**
   * Checks the given portfolio name and saves the given entry into the file.
   *
   * @param pfNameCreate name of the portfolio that needs to be saved
   */
  public void saveOperation(String pfNameCreate) {
    if (pfNameCreate == null || pfNameCreate.length() == 0) {
      guiView.setcreateDialogStatus("Portfolio Name is to be given!!");
    } else {
      if (checkPortfolioField(pfNameCreate, "save")) {
        this.fileOperation.savePortfolio(this.rootDir + pfNameCreate + ".json", this.addTickr);
        this.addTickr = new JSONObject();
        guiView.setcreateDialogStatus("Successfully created the portfolio " + pfNameCreate +
                ". Give a new portfolio name in the portfolio name field to add more entries. " +
                "Click on 'X' button on top left if done creating.");
      }
    }
  }

  /**
   * Checks the given portfolio name is valid or not.
   *
   * @param pfName is the portfolio name entered by the user
   * @return true if valid else return false
   */
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

  /**
   * Carries out operation for adding stocks while creating the portfolio.
   *
   * @param pfNameCreate     name of the portfolio to which stocks are to be added
   * @param tickrCreate      tickr symbol that needs to be added
   * @param numStocksCreate  number of stocks to be added to the portfolio
   * @param dateCreate       is the date on which the adding of purchase of stock needs to be done
   * @param commissionCreate is the commission fees for carrying out the transaction
   * @throws FileNotFoundException if file is not found while validating the tickr symbols
   */
  public void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
                           String dateCreate, String commissionCreate)
          throws FileNotFoundException {
    if (checkAllfields(pfNameCreate, tickrCreate, numStocksCreate, dateCreate)) {
      guiView.setcreateDialogStatus("All the fields are not given!! Please specify all the fields");
    } else {
      Float commission = 0.0f;
      if (checkPortfolioField(pfNameCreate, "add") && checkDateField(dateCreate,
              "add") && checkStocksField(numStocksCreate, "add") &&
              checkTickrField(tickrCreate, "add") &&
              checkCommissionField(commissionCreate, "add")) {
        if (commissionCreate != null && commissionCreate.length() > 0) {
          commission = Float.valueOf(commissionCreate);
        }
        JSONObject addEntry = portfolio.makeTransactionRecord(dateCreate,
                commission, Integer.valueOf(numStocksCreate), tickrCreate);
        JSONArray listEntry = new JSONArray();
        listEntry.add(addEntry);
        this.addTickr.put(tickrCreate, listEntry);
        guiView.setcreateDialogStatus("Entry added successfully!.Click 'Add' to add more entries " +
                "or 'Save' to save portfolio.Give new name in the portfolio name field to create " +
                "a new portfolio. Close this pane by clicking on 'X' button on top left if done " +
                "saving.");
        guiView.settickrcreateValue(null);
        guiView.setnumstockscreateValue(null);
        guiView.setdateofcreationValue(null);
        guiView.setcommissionfeescreateValue(null);
      }
    }
  }

  /**
   * Helps to validate the inmput fields and modify the existing portfolio by allowing the user,
   * to purchase and sell stocks.
   *
   * @param pfNameModify     is the name of the portfolio that needs to be modified
   * @param tickrModify      is the name of the tickr symbol that needs to be modified
   * @param numStocksModify  is the number of stocks that needs to be purchase and sold
   * @param dateModify       is the date on which the purchase or sell needs to occur
   * @param commissionModify is commission fees while carrying out purchase or sell
   * @param statuslabel      helps to specify that modification needs to occur, checks inputs fields
   * @throws FileNotFoundException if portfolio is not found while making modification
   * @throws ParseException        if an exception occurs while parsing the existing portfolio
   */
  public void modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                             String dateModify,
                             String commissionModify, String statuslabel)
          throws FileNotFoundException, ParseException {
    if (checkAllfields(pfNameModify, tickrModify, numStocksModify, dateModify)) {
      guiView.setmodifyDialogStatus("All the fields are not given!!. " +
              "Please specify all the fields.");
    } else {
      Float commission = 0.0f;
      if (checkPortfolioField(pfNameModify, "modify") &&
              checkDateField(dateModify, "modify")) {
        if (checkStocksField(numStocksModify, "modify") &&
                checkCommissionField(commissionModify,
                        "modify")) {
          if (commissionModify != null && commissionModify.length() > 0) {
            commission = Float.valueOf(commissionModify);
          }
          validateTickrModify(pfNameModify, tickrModify, numStocksModify, dateModify,
                  commission, "modify", statuslabel);
        }
      }
    }
  }

  /**
   * Helps to validate the input fields and get the value of the portfolio on that particular date.
   *
   * @param pfNamedate is the name of the portfolio for which value needs to be computed
   * @param dateValue  is the date value on which value needs to be computed
   * @throws FileNotFoundException when pf name to be searched is not found
   * @throws ParseException        an error occurs when parsing fails while reading the input file
   */
  public void validateDateVal(String pfNamedate, String dateValue)
          throws FileNotFoundException, ParseException {
    if (pfNamedate == null || pfNamedate.length() == 0 || dateValue == null ||
            dateValue.length() == 0) {
      guiView.setvalueDialogStatus("All the values are not given!! Please specify all the fields.");
    } else {
      if (checkPortfolioField(pfNamedate, "valDate") && checkDateField(dateValue,
              "valDate")) {
        //updateRecordsPf(pfNamedate);
        float totVal = portfolio.portfolioValueDate(this.rootDir, pfNamedate, dateValue);
        guiView.setvalueDialogStatus("Portfolio Value on " + dateValue + " is : $" + totVal);
      }
    }
  }

  /**
   * Helps to validate the input fields and get the cost basis of the portfolio on particular date.
   *
   * @param pfNameBasis is the name of the portfolio for which cost basis needs to be computed
   * @param dateBasis   is the date value on which cost basis needs to be computed
   * @throws ParseException an error occurs when parsing fails while reading the input file
   */
  public void validateCostBasis(String pfNameBasis, String dateBasis) throws ParseException {
    if (pfNameBasis == null || pfNameBasis.length() == 0 || dateBasis == null ||
            dateBasis.length() == 0) {
      guiView.setCostBasisDialogStatus("All the values are not given!! Please specify all " +
              "the fields.");
    } else {
      if (checkPortfolioField(pfNameBasis, "costBasis") &&
              checkDateField(dateBasis, "costBasis")) {
        float costBasis = portfolio.getCostBasis(this.rootDir + pfNameBasis + ".json",
                dateBasis);
        //updateRecordsPf(pfNameBasis);
        guiView.setCostBasisDialogStatus("The cost basis till date " + dateBasis + " is: $" +
                costBasis);
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
          objmessage.append(" ").append(noOfStocks).append("\t  ").append(type).append("\t\t").
                  append(tickrRecord.get("date")).append("\t").append("\t$").
                  append(tickrRecord.get("commission_fee")).append("\t$").
                  append(tickrRecord.get("stock_price")).append("\n");
        }
        message.append(objmessage).append("\n");
      }
    }
    return message.toString();
  }

  /**
   * Get the composition of the given portfolio name.
   *
   * @param pfNameComposition is the name of the portfolio for which composition needs to be done
   */
  public void getCompositionpf(String pfNameComposition) {
    if (pfNameComposition == null || pfNameComposition.length() == 0) {
      guiView.setretrieveDialogStatus("All the values are not given!! Please give all the values.");
    } else {
      if (checkPortfolioField(pfNameComposition, "composition")) {
        JSONObject portfolioObj = this.fileOperation.readPortfolio(this.rootDir +
                pfNameComposition + ".json");
        guiView.setPortfoliosListComposition(viewFlexibleComposition(portfolioObj));
        guiView.setpfnameretrieve(null);
      }
    }
  }

  private boolean checkBatchTickrField(String stocksexist, String label) {
    if (portfolio.checkforInvalidcharacters(stocksexist)) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("Wrong Format is entered!! Please enter valid format.");
        guiView.setstocksexist(null);
      } else if (label.equals("dollarnew")) {
        guiView.setdollarnewpanestatus("Wrong format is entered!! Please enter valid format.");
        guiView.setstocksnew(null);
      }
      return false;
    } else {
      ArrayList<String> stocks = portfolio.validateTickrEntries(stocksexist);
      if (stocks.size() == 0) {
        if (label.equals("dollarexist")) {
          guiView.setdollarexistpanestatus("Invalid Tickr symbol is entered! Enter valid " +
                  "tickr symbols.");
          guiView.setstocksexist(null);
        } else if (label.equals("dollarnew")) {
          guiView.setdollarnewpanestatus("Invalid Tickr symbol is entered! Enter valid " +
                  "tickr symbols.");
          guiView.setstocksnew(null);
        }
        return false;
      } else {
        if (portfolio.checkDuplicates(stocks)) {
          if (label.equals("dollarexist")) {
            guiView.setdollarexistpanestatus("Duplicate tickr symbols exist in the given entry!!");
            guiView.setstocksexist(null);
          } else if (label.equals("dollarnew")) {
            guiView.setdollarnewpanestatus("Duplicate tickr symbols exist in the given entry!!");
            guiView.setstocksnew(null);
          }
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkBatchWeightFields(String weightsexist, String label) {
    String invalidWeightMsg = "Format of the the weights is not correct!! Please enter " +
            "valid format.";
    if (!portfolio.validateWeightFormat(weightsexist)) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus(invalidWeightMsg);
        guiView.setweightsexist(null);
        return false;
      } else if (label.equals("dollarnew")) {
        guiView.setdollarnewpanestatus(invalidWeightMsg);
        guiView.setweightsnew(null);
        return false;
      }
    } else {
      ArrayList<Float> weights = portfolio.validateWeightEntriesSum(weightsexist);
      String invalidWeightsMsg = "Sum of the given weights is not equal to 100%, Enter valid " +
              "weights again.";
      if (weights.size() == 0) {
        if (label.equals("dollarexist")) {
          guiView.setdollarexistpanestatus(invalidWeightsMsg);
          guiView.setweightsexist(null);
          return false;
        } else if (label.equals("dollarnew")) {
          guiView.setdollarnewpanestatus(invalidWeightsMsg);
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
                "corresponding weights given do not match. Please enter valid entries.");
        guiView.setstocksexist(null);
        guiView.setweightsexist(null);
      } else if (label.equals("dollarnew")) {
        guiView.setdollarnewpanestatus("The number of tickr symbols given and the number " +
                "corresponding weights given do not match. Please enter valid entries.");
        guiView.setstocksnew(null);
        guiView.setweightsnew(null);
      }
      return false;
    }
    return true;
  }

  private boolean checkValidMoney(String dollarexistval, String label) {
    if (!portfolio.checkValidInteger(dollarexistval) &&
            !portfolio.checkValidFloat(dollarexistval)) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("Money can be either an integer or a float value only!!");
        guiView.setdollarexistval(null);
      } else if (label.equals("dollarnew")) {
        guiView.setdollarnewpanestatus("Money can be either an integer or a float value!!");
        guiView.setdollarnewval(null);
      }
      return false;
    } else if (Float.parseFloat(dollarexistval) < 0) {
      if (label.equals("dollarexist")) {
        guiView.setdollarexistpanestatus("Negative money is not allowed!!!");
        guiView.setdollarexistval(null);
      } else if (label.equals("dollarnew")) {
        guiView.setdollarnewpanestatus("Negative money is not allowed!!!");
        guiView.setdollarnewval(null);
      }
      return false;
    }
    return true;
  }

  private void createStrategyLookup() {
    String lookuppath = this.rootDir + "stratergyLookup.json";
    if (!new File(lookuppath).exists()) {
      JSONObject data = new JSONObject();
      this.fileOperation.savePortfolio(lookuppath, data);
    }
  }

  private void saveExistingpf(String dollarexistpfname, String stocksexist, String weightsexist,
                              Float commission, String dollarexistval, String dollarexistdate,
                              String endate, String days, String label) {
    boolean filecheck = new File(this.rootDir + dollarexistpfname + ".json").exists();
    JSONObject portfolioObj = new JSONObject();
    JSONObject finalObj = portfolioObj;
    if (filecheck) {
      portfolioObj = this.fileOperation.readPortfolio(
              this.rootDir + dollarexistpfname + ".json");
      finalObj = portfolioObj;
    }
    if (label.equals("dollarexist")) {
      finalObj = portfolio.dollarCostExisting(portfolio.validateTickrEntries(stocksexist),
              portfolio.validateWeightEntriesSum(weightsexist), commission,
              Float.parseFloat(dollarexistval), dollarexistdate, portfolioObj);
    } else if (label.equals("dollarnew")) {
      if (new File(this.rootDir + dollarexistpfname + ".json").exists()) {
        finalObj = portfolio.startToFinishDollarCostPresent(
                portfolio.validateTickrEntries(stocksexist),
                portfolio.validateWeightEntriesSum(weightsexist), commission,
                Integer.parseInt(days),
                dollarexistdate, endate, Float.parseFloat(dollarexistval), portfolioObj);
      } else {
        finalObj = portfolio.startToFinishDollarCost(portfolio.validateTickrEntries(stocksexist),
                portfolio.validateWeightEntriesSum(weightsexist), commission,
                Integer.parseInt(days),
                dollarexistdate, endate, Float.parseFloat(dollarexistval));
      }
    }
    this.fileOperation.savePortfolio(this.rootDir + dollarexistpfname + ".json", finalObj);
  }

  private void saveStrategyrecord(String stratergydollarexistname, String dollarexistpfname,
                                  String stocksexist, String weightsexist, Float commission,
                                  String dollarexistval, String dollarexistdate,
                                  String dollarenddate, int freq) {
    JSONObject strategyObj = this.fileOperation.readPortfolio(
            this.rootDir + "stratergyLookup.json");
    JSONObject finalstrategyObj = portfolio.saveStrategyRecord(
            portfolio.validateTickrEntries(stocksexist),
            portfolio.validateWeightEntriesSum(weightsexist), commission, freq, dollarexistdate,
            dollarenddate, Float.parseFloat(dollarexistval), stratergydollarexistname, strategyObj,
            dollarexistpfname);
    this.fileOperation.savePortfolio(this.rootDir + "stratergyLookup.json", finalstrategyObj);
  }

  private void updateRecordsPf(String pfname) {
    // under discussion, needs more work.
    // extract stratergy lookup object.
    JSONObject strategyObj = this.fileOperation.readPortfolio(
            this.rootDir + "stratergyLookup.json");
    // check if this pf name exists in the lookup.
    if (strategyObj.containsKey(pfname)) {
      //extract records from every key present.
      for (Object key : strategyObj.keySet()) {
        JSONObject stkeyObj = (JSONObject) strategyObj.get(key);
        JSONObject portfolioObj = this.fileOperation.readPortfolio(
                this.rootDir + pfname + ".json");
        JSONObject finalObj = portfolioObj;
        // have today's date loop through all the date fields in the given range.
        if (stkeyObj.get("end_date") == null) {
          // check if the given date is future date or not.
          finalObj = portfolio.dollarCostExisting((ArrayList<String>) stkeyObj.get("stock_list"),
                  (ArrayList<Float>) stkeyObj.get("weight_list"),
                  (float) stkeyObj.get("commission_fee"), (float) stkeyObj.get("investment"),
                  (String) strategyObj.get("start_date"), portfolioObj);
        } else {
          // check if start date is future date. If so then do not add any entry.
          if (!portfolio.checkFutureDate((String) strategyObj.get("start_date")) &&
                  !portfolio.checkTodayDateAndTime((String) strategyObj.get("start_date"))) {
            // check if end date is future date, if so add entry till the present date only.
            if (portfolio.checkFutureDate((String) strategyObj.get("end_date")) ||
                    portfolio.checkTodayDateAndTime((String) strategyObj.get("end_date"))) {
              finalObj = portfolio.startToFinishDollarCostPresent(
                      (ArrayList<String>) stkeyObj.get("stock_list"),
                      (ArrayList<Float>) stkeyObj.get("weight_list"),
                      (float) stkeyObj.get("commission_fee"),
                      (int) strategyObj.get("frequency"), (String) strategyObj.get("start_date"),
                      "", (float) stkeyObj.get("investment"), portfolioObj);
            }
          }
        }
        this.fileOperation.savePortfolio(this.rootDir + pfname + ".json", finalObj);
      }
    }
  }

  private boolean checkDatesandDays(String dollarnewdays, String dollarnewstartdate,
                                    String dollarnewenddate, String label)
          throws ParseException {
    if (label.equals("dollarnew")) {
      if (dollarnewenddate == null || dollarnewenddate.equals("")) {
        return true;
      } else if (!portfolio.checkValidDates(dollarnewstartdate, dollarnewenddate)) {
        guiView.setdollarnewpanestatus("Value given for start date is ahead of the given end " +
                "date, Please enter valid dates");
        guiView.setstartdatenew(null);
        guiView.setenddatenew(null);
        return false;
      } else if (!portfolio.checkValidInteger(dollarnewdays)) {
        guiView.setdollarnewpanestatus("Please enter only a valid positive integer!!");
        guiView.setdollardays(null);
        return false;
      } else if ((portfolio.checkDifference(dollarnewstartdate, dollarnewenddate) <
              Integer.parseInt(dollarnewdays))) {
        guiView.setdollarnewpanestatus("The number of days between start and end date is less " +
                "than the given frequency!! Please enter valid start date, end date and frequency");
        guiView.setstartdatenew(null);
        guiView.setenddatenew(null);
        guiView.setenddatenew(null);
        return false;
      }
    }
    return true;
  }

  /**
   * This function helps to implement dollar averaging strategy on existing portfolio.
   *
   * @param stratergydollarexistname is the name of the strategy given by the user
   * @param dollarexistpfname        portfolio name over which dollar-averaging needs to be done
   * @param stocksexist              is the list of stocks given by the user, seperated by ','
   * @param weightsexist             is the list of weightsgiven by the user, seperated by ','
   * @param dollarexistval           is the money to be invested in the portfolio
   * @param dollarexistdate          is date oon which the investment needs to occur
   * @param dollarexistcommision     is the commission fees for the transaction to occur
   */
  public void validateExistingDollar(String stratergydollarexistname, String dollarexistpfname,
                                     String stocksexist, String weightsexist, String dollarexistval,
                                     String dollarexistdate, String dollarexistcommision) {
    if (checkAllFieldsDollarExist(stratergydollarexistname, dollarexistpfname, stocksexist,
            weightsexist, dollarexistval, dollarexistdate)) {
      guiView.setdollarexistpanestatus("All the fields are not given!!");
    } else {
      float commission = 0.0f;
      if (checkPortfolioField(dollarexistpfname, "dollarexist") &&
              checkBatchTickrField(stocksexist, "dollarexist") &&
              checkBatchWeightFields(weightsexist, "dollarexist") &&
              checkBatchTickrBatchWeightsSize(stocksexist, weightsexist, "dollarexist") &&
              checkValidMoney(dollarexistval, "dollarexist") && checkDateField(dollarexistdate,
              "dollarexist") &&
              checkCommissionField(dollarexistcommision, "dollarexist")) {
        if (dollarexistcommision != null && dollarexistcommision.length() > 0) {
          commission = Float.parseFloat(dollarexistcommision);
        }
        if (commission > Float.parseFloat(dollarexistval)) {
          guiView.setdollarexistpanestatus("Commission fees cannot be greater than money" +
                  " to be invested in the portfolio");
        } else {
          if (!portfolio.checkFutureDate(dollarexistdate) &&
                  !portfolio.checkTodayDateAndTime(
                          dollarexistdate)) {
            saveExistingpf(dollarexistpfname, stocksexist, weightsexist, commission, dollarexistval,
                    dollarexistdate, null, "0", "dollarexist");
          }
          saveStrategyrecord(stratergydollarexistname, dollarexistpfname, stocksexist,
                  weightsexist, commission, dollarexistval, dollarexistdate,
                  null, 0);
          guiView.setdollarexistpanestatus("Strategy successfully applied to the " +
                  "portfolio " + dollarexistpfname + " Can apply a new strategy on another " +
                  "existing portfolio. If done click on 'X' on top left");
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

  /**
   * This function helps to implement start-to-finish dollar averaging strategy.
   *
   * @param stratergydollarnewname is the name of the strategy given by the user
   * @param dollarnewcreatepfname  name of the portfolio to be created
   * @param stocksnew              is the list of stocks string given by the user, seperated by  ','
   * @param weightsnew             is the list of weights string given by the user, seperated by ','
   * @param dollarnewval           is the money to be invested in the portfolio
   * @param dollarnewdays          is the number of days for investment to recur
   * @param dollarnewstartdate     is start date of the investment to occur
   * @param dollarnewenddate       is the end date of the investment to occur
   * @param dollarnewcommission    is the commission fees for the transaction to occur
   * @throws ParseException when there is an error parsing the portfolio and strategy look up json
   */
  public void validateNewDollar(String stratergydollarnewname, String dollarnewcreatepfname,
                                String stocksnew, String weightsnew, String dollarnewval,
                                String dollarnewdays, String dollarnewstartdate,
                                String dollarnewenddate, String dollarnewcommission)
          throws ParseException {
    if (checkAllFieldsDollarNew(stratergydollarnewname, dollarnewcreatepfname, stocksnew,
            weightsnew, dollarnewval, dollarnewdays, dollarnewstartdate)) {
      guiView.setdollarnewpanestatus("All the fields are not given !!");
    } else {
      float commission = 0.0f;
      if (checkPortfolioField(dollarnewcreatepfname, "dollarnew") &&
              checkBatchTickrField(stocksnew,
                      "dollarnew") && checkBatchWeightFields(weightsnew, "dollarnew") &&
              checkBatchTickrBatchWeightsSize(stocksnew, weightsnew, "dollarnew") &&
              checkValidMoney(dollarnewval, "dollarnew") && checkDateField(dollarnewstartdate,
              "dollarnewstartdate") &&
              checkDateField(dollarnewenddate, "dollarnewenddate") &&
              checkDatesandDays(dollarnewdays, dollarnewstartdate,
                      dollarnewenddate, "dollarnew")) {
        if (checkCommissionField(dollarnewcommission, "dollarnew")) {
          if (dollarnewcommission != null && dollarnewcommission.length() > 0) {
            commission = Float.parseFloat(dollarnewcommission);
          }
          if (commission > Float.valueOf(dollarnewval)) {
            guiView.setdollarnewpanestatus("Commission fees cannot be greater than " +
                    "money to be invested in the portfolio.");
          } else if (Integer.valueOf(dollarnewdays) <= 0) {
            guiView.setdollarnewpanestatus("Frequency cannot be less than or equal to " +
                    "zero.");
          } else {
            if (!portfolio.checkFutureDate(dollarnewstartdate) &&
                    !portfolio.checkTodayDateAndTime(dollarnewstartdate)) {
              String enddate = dollarnewenddate;
              if (portfolio.checkFutureDate(enddate) || portfolio.checkTodayDateAndTime(enddate)) {
                enddate = portfolio.getTodayDate();
              }
              saveExistingpf(dollarnewcreatepfname, stocksnew, weightsnew, commission, dollarnewval,
                      dollarnewstartdate, enddate, dollarnewdays, "dollarnew");
            } else {
              if (!new File(this.rootDir + dollarnewcreatepfname
                      + ".json").exists()) {
                // save an empty portfolio.
                this.fileOperation.savePortfolio(this.rootDir + dollarnewcreatepfname
                        + ".json", new JSONObject());
              }
            }
            saveStrategyrecord(stratergydollarnewname, dollarnewcreatepfname, stocksnew,
                    weightsnew, commission, dollarnewval, dollarnewstartdate, dollarnewenddate,
                    Integer.parseInt(dollarnewdays));
            guiView.setdollarnewpanestatus("Strategy successfully applied to the portfolio " +
                    dollarnewcreatepfname + " Can apply new strategy on the same portfolio or " +
                    "create a new portfolio. If done click on 'X' on top left");
            guiView.setenddatenew(null);
            guiView.setstartdatenew(null);
            guiView.setdollardays(null);
            guiView.setdollarcommissionnew(null);
            guiView.setstocksnew(null);
            guiView.setweightsnew(null);
            guiView.setdollarnewval(null);
            guiView.setstrategynamenewexist(null);
          }

        }
      }
    }
  }

  /**
   * Displays the pane that lets the user set the root path for storing the portfolios that,
   * are to be created, modified or perform dollar averaging strategy.
   */
  public void displaysetrootpane() {
    this.rootDirUser = guiView.givenPath();
    setDirectory();
  }

  /**
   * Displays the dialog pane for the creation of portfolio, modification of the portfolio ,
   * cost basis till a date,getting value of the portfolio on a specific date,
   * retrieving the composition of the portfolio,dollar cost averaging on the existing portfolio,
   * doing dollar cost averaging from start-to-finish and,
   * Quit the program. Every functionality is identified by label given to it.
   *
   * @param label can be the label associated with the functionality that needs to be performed
   */
  public void displayDialogPane(String label) {
    String invalidPathMsg = "Please click 'Create User Path' button to specify " +
            "the root directory path!!";
    if (this.rootDirUser == null || this.rootDirUser.length() == 0) {
      switch (label) {
        case "costBasis":
          guiView.setLabelCostBasisStatus(invalidPathMsg);
          break;
        case "modify":
          guiView.setModifyLabelStatus(invalidPathMsg);
          break;
        case "getDateVal":
          guiView.setValueLabelStatus(invalidPathMsg);
          break;
        case "create":
          guiView.setCreateLabelStatus(invalidPathMsg);
          break;
        case "composition":
          guiView.setRetrievePanelStatus(invalidPathMsg);
          break;
        case "dollarexist":
          guiView.setdollarExistingStatus(invalidPathMsg);
          break;
        case "dollarnew":
          guiView.setdollarNewStatus(invalidPathMsg);
          break;
        default:
          // do nothing.
      }
    } else {
      String message = portfolio.listJSONfiles(this.rootDir);
      if (message == null || message.length() == 0) {
        message = "No portfolio exists in the given path. Create a new portfolio. " +
                "Click on 'X' to close the dialog box";
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
          guiView.setCostBasisDialogStatus(null);
          guiView.displayCostBasis();
          break;
        case "modify":
          guiView.setModifyLabelStatus(null);
          guiView.setportfoliosListModify(message);
          guiView.setmodifyDialogStatus(null);
          guiView.displayModifyPf();
          break;
        case "getDateVal":
          guiView.setValueLabelStatus(null);
          guiView.setPortfoliosListVal(message);
          guiView.setvalueDialogStatus(null);
          guiView.displayValuepf();
          break;
        case "composition":
          guiView.setRetrievePanelStatus(null);
          guiView.setPortfoliosListComposition(null);
          guiView.setPortfoliosListRetrieve(message);
          guiView.setretrieveDialogStatus(null);
          guiView.displayRetrievepf();
          break;
        case "dollarexist":
          guiView.setdollarExistingStatus(null);
          guiView.setportfolioslistdollarexist(null);
          guiView.setportfolioslistdollarexist(message);
          guiView.setdollarexistpanestatus(null);
          guiView.displayDollarExistingpf();
          break;
        case "dollarnew":
          guiView.setdollarNewStatus(null);
          guiView.setdollarexistpanestatus(null);
          guiView.setdollarnewpanestatus(null);
          guiView.displayDollarNewpf();
          break;
        default:
          // do nothing.
      }
    }
  }

  /**
   * Runs the go stocks program by setting GUI view visible and passing class to,
   * add features method in gui for letting buttons with action listeners to run functionalities.
   *
   * @throws ParseException if error occurs while parsing
   * @throws IOException    if error while parsing input
   */
  public void goStocks() throws ParseException, IOException {
    guiView.makeVisible();
    guiView.addFeatures(this);
  }
}
