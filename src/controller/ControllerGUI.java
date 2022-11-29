package controller;

import java.io.FileNotFoundException;
import java.text.ParseException;

public interface ControllerGUI extends Controller {
  /**
   * Set the root directory based on the input given by the user.
   */
  void setDirectory();

  /**
   *
   * @param label can be
   */
  void displayDialogPane(String label);

  /**
   *
   * @param pfNameCreate
   * @param tickrCreate
   * @param numStocksCreate
   * @param dateCreate
   * @param commissionCreate
   * @throws FileNotFoundException
   */
  void addOperation(String pfNameCreate, String tickrCreate, String numStocksCreate,
                    String dateCreate, String commissionCreate) throws FileNotFoundException;

  /**
   *
   * @param pfNameCreate
   */
  void saveOperation(String pfNameCreate);

  /**
   *
   * @param pfNameModify
   * @param tickrModify
   * @param numStocksModify
   * @param dateModify
   * @param commissionModify
   * @param statuslabel
   * @throws FileNotFoundException
   * @throws ParseException
   */
  void modifyValidate(String pfNameModify, String tickrModify, String numStocksModify,
                      String dateModify, String commissionModify, String statuslabel)
          throws FileNotFoundException, ParseException;

  /**
   *
   * @param pfNamedate
   * @param dateValue
   * @throws FileNotFoundException
   * @throws ParseException
   */
  void validateDateVal(String pfNamedate, String dateValue)
          throws FileNotFoundException, ParseException;

  /**
   *
   * @param pfNameBasis
   * @param dateBasis
   * @throws ParseException
   */
  void validateCostBasis(String pfNameBasis, String dateBasis) throws ParseException;

  /**
   *
   * @param pfNameComposition
   */
  void getCompositionpf(String pfNameComposition);

  /**
   *
   * @param stratergydollarexistname
   * @param dollarexistpfname
   * @param stocksexist
   * @param weightsexist
   * @param dollarexistval
   * @param dollarexistdate
   * @param dollarexistcommision
   */
  void validateExistingDollar(String stratergydollarexistname, String dollarexistpfname,
                              String stocksexist, String weightsexist, String dollarexistval,
                              String dollarexistdate, String dollarexistcommision);

  /**
   *
   * @param stratergydollarnewname
   * @param dollarnewcreatepfname
   * @param stocksnew
   * @param weightsnew
   * @param dollarnewval
   * @param dollarnewdays
   * @param dollarnewstartdate
   * @param dollarnewenddate
   * @param dollarnewcommission
   * @throws ParseException
   */
  void validateNewDollar(String stratergydollarnewname, String dollarnewcreatepfname,
                         String stocksnew, String weightsnew, String dollarnewval,
                         String dollarnewdays, String dollarnewstartdate, String dollarnewenddate,
                         String dollarnewcommission) throws ParseException;

  /**
   *
   */
  void displaysetrootpane();
}
