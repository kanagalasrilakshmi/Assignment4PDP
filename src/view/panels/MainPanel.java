package view.panels;

import javax.swing.*;

/**
 * Interface that helps to access main panel.
 */
public interface MainPanel {
  /**
   * Main panel that contains create panel, modify panel, cost basis panel, retrieve panel,
   * dollar cost averaging on existing portfolio, start-to-finish dollar cost averaging ,quit panel
   */
  void mainPanel();

  /**
   * Returns main panel.
   *
   * @return Jpanel object of type main panel
   */
  JPanel getMainJPanel();
}
