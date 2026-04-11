import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdminMenuPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // --- Black Top Bar ---
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        // Logo inside the black bar
        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        topBar.add(image1);

        // Logout text/button (Top Right)
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutBtn.addActionListener(e -> HomePage.show());
        topBar.add(logoutBtn);

        // --- Main Content ---
        JLabel menuLabel = new JLabel("Main Menu");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(menuLabel);

        // The Buttons
        ImagePanel image3 = new ImagePanel("images/Home.png");
        image3.setOnClick(() -> HomePage.show());
        panel.add(image3);

        ImagePanel image5 = new ImagePanel("images/RevokedMembership.png");
        image5.setOnClick(() -> RevokedMembershipPage.show());
        panel.add(image5);

        ImagePanel image6 = new ImagePanel("images/Revoked Membership List.png");
        image6.setOnClick(() -> RevokedMembershipListPage.show());
        panel.add(image6);

        ImagePanel image7 = new ImagePanel("images/Active Members List.png");
        image7.setOnClick(() -> ActiveMembersListPage.show());
        panel.add(image7);

        ImagePanel image8 = new ImagePanel("images/Inactive Members List.png");
        image8.setOnClick(() -> InactiveMembersListPage.show());
        panel.add(image8);

        // Big Side Logo
        ImagePanel image4 = new ImagePanel("images/MainLogo.png");
        panel.add(image4);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                // Top Bar Positioning
                topBar.setBounds(0, 0, w, 60);
                image1.setBounds(10, 5, 50, 50);
                logoutBtn.setBounds(w - 110, 15, 100, 30);

                // Menu Header Positioning
                menuLabel.setBounds(50, 80, 200, 30);

                // Vertical Button Stack Positioning
                int startX = 30;
                int startY = 120;
                int btnW = 260; // Adjust based on your image widths
                int btnH = 65;
                int spacing = 12; // Gap between buttons

                image3.setBounds(startX, startY, btnW, btnH);
                image5.setBounds(startX, startY + (btnH + spacing), btnW, btnH);
                image6.setBounds(startX, startY + (btnH + spacing) * 2, btnW, btnH);
                image7.setBounds(startX, startY + (btnH + spacing) * 3, btnW, btnH);
                image8.setBounds(startX, startY + (btnH + spacing) * 4, btnW, btnH);

                // Right Side Large Logo
                image4.setBounds((int) (w * 0.55), (int) (h * 0.15), (int) (w * 0.42), (int) (w * 0.42));
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}