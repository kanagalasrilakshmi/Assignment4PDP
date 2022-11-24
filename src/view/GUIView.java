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
  void setCreateLabelStatus(String message);
  public String getCreatePfValue();
  String getdateofcreationValue();
  String getnumstockscreateValue();
  String gettickrcreateValue();
  String getcommissionfeescreateValue();
  void setcreateDialogStatus(String message);

}
