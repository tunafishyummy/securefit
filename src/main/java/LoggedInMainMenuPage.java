import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LoggedInMainMenuPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image2 = new ImagePanel("images/MainLogo.png");
        panel.add(image2);

        ImagePanel image3 = new ImagePanel("images/Home.png");
        image3.setOnClick(() -> HomePage.show());
        panel.add(image3);

        JButton logOutButton = new JButton("Log out");
        logOutButton.setBackground(Color.BLACK);
        logOutButton.setForeground(Color.WHITE);
        panel.add(logOutButton);
        logOutButton.addActionListener(e -> {
            Auth.logout();
            HomePage.show();
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                image1.setBounds(10, 10, 50, 50);
                image2.setBounds((int) (w * 0.563), (int) (h * 0.093), (int) (w * 0.404), (int) (h * 0.674));
                image3.setBounds((int) (w * 0.023), (int) (h * 0.283), (int) (w * 0.104), (int) (h * 0.070));
                logOutButton.setBounds((int) (w * 0.922), 10, 120, 40);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
