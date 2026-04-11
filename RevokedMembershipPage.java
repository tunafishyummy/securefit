import javax.swing.*;
import java.awt.*;

public class RevokedMembershipPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
