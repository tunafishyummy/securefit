import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoggedInMainMenuPage {
    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        ImagePanel logoutButton = new ImagePanel("images/Logout.png");
        logoutButton.setOnClick(() -> {
            Auth.logout();
            HomePage.show();
        });

        JLabel menuLabel = new JLabel("Main Menu", JLabel.CENTER);
        menuLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 34));
        menuLabel.setForeground(Color.WHITE);
        topBar.add(menuLabel);

        ImagePanel personalInfoButton = new ImagePanel("images/PersonalInfo.png");
        personalInfoButton.setOnClick(() -> PersonalInfoPage.show());
        panel.add(personalInfoButton);

        ImagePanel qrCodeButton = new ImagePanel("images/QRCodeImage.png");
        qrCodeButton.setOnClick(() -> {
            String email = Auth.getCurrentUser();
            if (email != null) {
                QrImage.show(email);
            }
        });
        panel.add(qrCodeButton);

        ImagePanel upgradeButton = new ImagePanel("images/UpgradeMembership.png");
        upgradeButton.setOnClick(() -> UpgradeMembershipPage.show());
        panel.add(upgradeButton);

        panel.add(logoutButton);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                int contentY = 80;
                int contentHeight = Math.max(0, h - contentY);
                int halfWidth = w / 2;
                int halfHeight = contentHeight / 2;

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);
                menuLabel.setBounds(0, 16, w, 40);

                personalInfoButton.setBounds(0, contentY, halfWidth, halfHeight);
                qrCodeButton.setBounds(0, contentY + halfHeight, halfWidth, contentHeight - halfHeight);
                upgradeButton.setBounds(halfWidth, contentY, w - halfWidth, halfHeight);
                logoutButton.setBounds(halfWidth, contentY + halfHeight, w - halfWidth, contentHeight - halfHeight);
            }
        });

        Main.setPage(panel);
    }
}
