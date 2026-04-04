import javax.swing.*;
import java.awt.*;

public class AdminMenuPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png", 1, 1, 50, 50);
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image4 = new ImagePanel("images/MainLogo.png", 1080, 100, 776, 728);
        panel.add(image4);

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}