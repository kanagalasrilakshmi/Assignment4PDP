package view;

import controller.ControllerGUI;

public interface GUIView {

  void makeVisible();

  void setpathStore(String setMessage);

  void displayCostBasis();

  void setLabelCostBasisStatus(String message);

  void displayCreatePf();

  void displayModifyPf();

  void displayValuepf();

  void setCreateLabelStatus(String message);

  void setcreateDialogStatus(String message);

  void setModifyLabelStatus(String message);

  void setmodifyDialogStatus(String message);

  void setValueLabelStatus(String message);

  void setvalueDialogStatus(String message);

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

  void setPortfoliosListVal(String message);

  void setPortfoliosListBasis(String message);

  void addFeatures(ControllerGUI features);

}
