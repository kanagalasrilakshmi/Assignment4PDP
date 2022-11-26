package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

public interface ControllerGUI extends Controller{
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
}
