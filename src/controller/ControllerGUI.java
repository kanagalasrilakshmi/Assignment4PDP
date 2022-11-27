package controller;

import java.io.FileNotFoundException;
import java.text.ParseException;

public interface ControllerGUI extends Controller {
  void setDirectory();

  void displayDialogPane(String label);

  void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
                    String dateCreate, String commissionCreate) throws FileNotFoundException;

  void saveOperation(String pfNameCreate);

  void modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                      String dateModify, String commissionModify, String statuslabel)
          throws FileNotFoundException, ParseException;

  void validateDateVal(String pfNamedate, String dateValue)
          throws FileNotFoundException, ParseException;

  void validateCostBasis(String pfNameBasis, String dateBasis) throws ParseException;

  void getCompositionpf(String pfNameComposition);

  void validateExistingDollar(String stratergydollarexistname, String dollarexistpfname,
                              String stocksexist, String weightsexist, String dollarexistval,
                              String dollarexistdate, String dollarexistcommision);

  void validateNewDollar(String stratergydollarnewname, String dollarnewcreatepfname,
                         String stocksnew, String weightsnew, String dollarnewval,
                         String dollarnewdays, String dollarnewstartdate, String dollarnewenddate,
                         String dollarnewcommission);
  void displaysetrootpane();
}
