import javax.swing.*;
import java.awt.*;

public class MainMenuPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png", 1, 1, 50, 50);
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image2 = new ImagePanel("images/MainLogo.png", 1080, 100, 776, 728);
        panel.add(image2);

        ImagePanel image3 = new ImagePanel("images/Home.png", 44, 306, 199, 76);
        image3.setOnClick(() -> HomePage.show());
        panel.add(image3);

        ImagePanel image4 = new ImagePanel("images/Register.png", 44, 414, 273, 76);
        image4.setOnClick(() -> SignupMenuPage.show());
        panel.add(image4);

        ImagePanel image5 = new ImagePanel("images/Login.png", 44, 522, 273, 76);
        image5.setOnClick(() -> LoginPage.show());
        panel.add(image5);

        ImagePanel image6 = new ImagePanel("images/ScanQRCode.png", 44, 642, 292, 76);
        panel.add(image6);

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}