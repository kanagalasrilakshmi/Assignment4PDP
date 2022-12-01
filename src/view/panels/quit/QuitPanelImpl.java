package view.panels.quit;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;

/**
 * Class that implements the Quit panel interface to create a quit panel and return it.
 */
public class QuitPanelImpl implements QuitPanel {
  private JPanel quitPanel;

  /**
   * Create quit panel.
   */
  public void getQuitPanel() {
    quitPanel = new JPanel();
    quitPanel.setBorder(BorderFactory.createTitledBorder("Quit the stocks program"));
    JButton quitButton = new JButton("Quit the Program");
    quitButton.addActionListener((ActionEvent e) -> System.exit(0));
    quitPanel.add(quitButton);
  }

  /**
   * Quit panel object.
   *
   * @return Jpanel object of quit panel
   */
  public JPanel quitPanelfinal() {
    return this.quitPanel;
  }
}
