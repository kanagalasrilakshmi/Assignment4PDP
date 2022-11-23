package view;

import java.awt.event.ActionListener;

import javax.swing.*;

public interface GUIView {
  void setCommandButtonListener(ActionListener actionEvent);
  void makeVisible();
  void setpathStore(String setMessage);
  void setCostBasisResult(String message);
  void displayCostBasis();
  String pfNameCostBasis();
  String getDate();
  void setLabelCostBasis(String message);

}
