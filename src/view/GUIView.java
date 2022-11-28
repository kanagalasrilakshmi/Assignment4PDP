package view;

import controller.ControllerGUI;

public interface GUIView {

  void makeVisible();
  String givenPath();

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
  void setRetrievePanelStatus(String message);
  void setPortfoliosListRetrieve(String message);
  void displayRetrievepf();
  void setretrieveDialogStatus(String message);
  void setpfnameretrieve(String message);
  void setPortfoliosListComposition(String message);
  void displayDollarExistingpf();
  void displayDollarNewpf();
  void setdollarExistingStatus(String message);
  void setdollarNewStatus(String message);
  void setdollarexistpanestatus(String message);

  void setpfNameExistDollar(String message);
  void setdollardateexist(String message);
  void setdollarexistcommisionval(String message);
  void setstocksexist(String message);
  void setweightsexist(String message);

  void setdollarexistval(String message);
  void setdollarnewpanestatus(String message);
  void setportfolioslistdollarexist(String message);
  void setpfnamedollarnew(String message);
  void setstocksnew(String message);
  void setweightsnew(String message);
  void setdollarnewval(String message);
  void setstartdatenew(String message);
  void setenddatenew(String message);
  void setdollardays(String message);
  void setdollarcommissionnew(String message);
  void setstrategynameexist(String message);
  void setstrategynamenewexist(String message);

}
