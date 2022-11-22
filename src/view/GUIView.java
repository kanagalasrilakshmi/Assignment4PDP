package view;

import java.awt.event.ActionListener;

import javax.swing.*;

public interface GUIView {
  void setCommandButtonListener(ActionListener actionEvent);
  void makeVisible();
  void setpathStore(String setMessage);

}
