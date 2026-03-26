import javax.swing.*;
import java.awt.*;

public class MainMenuPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);

        ImagePanel image5 = new ImagePanel("securefit/images/Image5.png", 1, 1, 200, 200);
        image5.setOnClick(() -> HomePage.show());
        panel.add(image5); 

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}