package view.panels;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Class that implements the Quit panel interface to create a quit panel and return it.
 */
public class QuitPanelImpl implements QuitPanel {
  private JPanel quitPanel;
  public void getQuitPanel() {
    quitPanel = new JPanel();
    quitPanel.setBorder(BorderFactory.createTitledBorder("Quit the stocks program"));
    JButton quitButton = new JButton("Quit the Program");
    quitButton.addActionListener((ActionEvent e) -> System.exit(0));
    quitPanel.add(quitButton);
  }
  public JPanel quitPanelfinal(){
    return this.quitPanel;
  }
}
