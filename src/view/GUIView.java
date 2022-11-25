package view;

import java.awt.event.ActionListener;

public interface GUIView {
  void setCommandButtonListener(ActionListener actionEvent);

  void makeVisible();

  void setpathStore(String setMessage);

  void setCostBasisResult(String message);

  void displayCostBasis();

  String pfNameCostBasis();

  String getDate();

  void setLabelCostBasis(String message);

  void displayCreatePf();

  void displayModifyPf();

  void displayValuepf();
  public String getCreatePfValue();
  String getdateofcreationValue();
  String getnumstockscreateValue();
  String gettickrcreateValue();
  String getcommissionfeescreateValue();
  void setCreateLabelStatus(String message);
  void setcreateDialogStatus(String message);
  void setModifyLabelStatus(String message);
  void setmodifyDialogStatus(String message);
  String getModifyPfValue();

  String getdateofmodifynValue();

  String getnumstocksmodifyValue();

  String gettickrmodifyValue();

  String getcommissionfeesmodifyValue();
  void setValueLabelStatus(String message);
  void setvalueDialogStatus(String message);
  String getpfnameVal();
  String getdateVal();

}
