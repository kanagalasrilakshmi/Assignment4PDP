import java.net.URL;
/* ww w .  j  a  va2s .c o m*/
import javax.imageio.ImageIO;
import javax.swing.*;

public class Testing {
  JDialog dialog;

  public void displayGUI() {
    JOptionPane optionPane = new JOptionPane(getPanel(),
            JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null,
            new Object[] {}, null);
    dialog = optionPane.createDialog("import");
    dialog.setVisible(true);
  }

  public JPanel getPanel() {
    JPanel panel = new JPanel();
    JTextField field1 = new JTextField(50);
    JTextField field2 = new JTextField(50);
    JLabel lab1 = new JLabel("Enter pf name");
    JLabel lab2 = new JLabel("Enter date");

    JButton button = new JButton("ADD");
    JButton button2 = new JButton("SAVE");
    //button.addActionListener(e -> dialog.dispose());
    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
    panel.add(lab1);
    panel.add(field1);
    panel.add(lab2);
    panel.add(field2);
    panel.add(button);
    panel.add(button2);

    return panel;
  }

  public static void main(String[] args) {
    new Testing().displayGUI();
    //System.out.println(field1.getText);

  }
}
