import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LoggedInMainMenuPage {
    public static void show() {
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

        ImagePanel image4 = new ImagePanel("images/PersonalInfo.png");
        image4.setOnClick(() -> PersonalInfoPage.show());
        panel.add(image4);

        ImagePanel image5 = new ImagePanel("images/QRCodeImage.png");
        image5.setOnClick(() -> {
        String email = Auth.getCurrentUser();
        if (email != null) {
        QrImage.show(email);
        }
        });
        panel.add(image5);

        ImagePanel image6 = new ImagePanel("images/UpgradeMembership.png");
        image6.setOnClick(() -> UpgradeMembershipPage.show());
        panel.add(image6);

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

                image1.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.03), (int)(h * 0.05)); 
                image2.setBounds((int)(w * 0.56), (int)(h * 0.09), (int)(w * 0.40), (int)(h * 0.67));
                image3.setBounds((int)(w * 0.0229), (int)(h * 0.28), (int)(w * 0.10), (int)(h * 0.07));
                image4.setBounds((int)(w * 0.0229), (int)(h * 0.38), (int)(w * 0.15), (int)(h * 0.07));
                image5.setBounds((int)(w * 0.0229), (int)(h * 0.48), (int)(w * 0.17), (int)(h * 0.07));
                image6.setBounds((int)(w * 0.0229), (int)(h * 0.59), (int)(w * 0.19), (int)(h * 0.07));
                logOutButton.setBounds((int) (w * 0.922), 10, 120, 40);
            }
        });

        Main.setPage(panel);
    }
}
