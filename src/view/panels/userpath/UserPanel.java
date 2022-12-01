package view.panels.userpath;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Interface that adds user panel and returning user panel.
 */
public interface UserPanel {
  /**
   * Creates user panel.
   */
  void getUserPanel();

  /**
   * input button for clicking the user panel.
   *
   * @return Jbutton type that takes in input
   */
  JButton returninputButton();

  /**
   * path for storing the portfolios.
   *
   * @return Jlabel that return the path where portfolios are to be stored
   */
  JLabel returnPathStore();

  /**
   * Return the user panel.
   *
   * @return user panel of type Jpanel
   */
  JPanel returnUserPanel();
}
