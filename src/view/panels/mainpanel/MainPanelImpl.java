package view.panels.mainpanel;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Modify panel that implements functionalities for getting main panel.
 */
public class MainPanelImpl implements MainPanel {
  private JPanel mainJPanel;

  /**
   * Main panel that contains create panel, modify panel, cost basis panel, retrieve panel.
   * dollar cost averaging on existing portfolio, start-to-finish dollar cost averaging ,quit panel
   */
  public void mainPanel() {
    mainJPanel = new JPanel();
    mainJPanel.setLayout(new BoxLayout(mainJPanel, BoxLayout.Y_AXIS));
  }

  /**
   * Returns main panel.
   *
   * @return Jpanel object of type main panel
   */
  public JPanel getMainJPanel() {
    return this.mainJPanel;
  }
}
