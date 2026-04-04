import javax.swing.*;
import java.awt.*;

public class LoggedInMainMenuPage {
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

        JButton logOutButton = new JButton("Log out");
        logOutButton.setBounds(1770, 1, 150, 40);
        logOutButton.setBackground(Color.BLACK);
        logOutButton.setForeground(Color.WHITE);
        panel.add(logOutButton);
        logOutButton.addActionListener(e -> {
            Auth.logout();
            HomePage.show();
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}