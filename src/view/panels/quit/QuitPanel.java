package view.panels.quit;

import javax.swing.JPanel;

/**
 * Interface for adding the Quit panel components and returning final quit panel.
 */
public interface QuitPanel {
  /**
   * Create a quit panel.
   */
  void getQuitPanel();

  /**
   * Return the quit panel.
   *
   * @return JPanel type quit panel object.
   */
  JPanel quitPanelfinal();
}
