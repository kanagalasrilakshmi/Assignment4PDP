package view;

import java.awt.event.ActionListener;

public interface GUIView {
  void setCommandButtonListener(ActionListener actionEvent);

  void makeVisible();

  void setpathStore(String setMessage);

  void displayCostBasis();

  String pfNameCostBasis();

  String getDate();

  void setLabelCostBasisStatus(String message);

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
  void setCostBasisDialogStatus(String message);
  void setCreatePfValue(String message);
  void settickrmodifyValue(String message);
  void settickrcreateValue(String message);
  void setnumstockscreateValue(String message);
  void setdateofcreationValue(String message);
  void setcommissionfeescreateValue(String message);
  void setModifyPfValue(String message);
  void setnumstocksmodifyValue(String message);
  void setdateofmodifynValue(String message);
  void setcommissionfeesmodifyValue(String message);
  void setpfnameVal(String message);
  void setdateVal(String message);
  void setpfNameCostBasis(String message);
  void setDate(String message);
  void setportfoliosListModify(String message);

}
