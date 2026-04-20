import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminMenuPage {
    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        //same shared header as the other pages
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        //small logo stays as the home shortcut
        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        topBar.add(image1);

        //admin logout stays in the header instead of the grid
        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        logoutBtn.addActionListener(e -> HomePage.show());
        topBar.add(logoutBtn);

        JLabel menuLabel = new JLabel("Admin Menu", JLabel.CENTER);
        menuLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 34));
        menuLabel.setForeground(Color.WHITE);
        topBar.add(menuLabel);

        ImagePanel image5 = new ImagePanel("images/RevokedMembership.png");
        image5.setOnClick(() -> RevokedMembershipPage.show());
        panel.add(image5);

        ImagePanel image6 = new ImagePanel("images/RevokedMembershipList.png");
        image6.setOnClick(() -> RevokedMembershipListPage.show());
        panel.add(image6);

        ImagePanel image7 = new ImagePanel("images/ActiveMembersList.png");
        image7.setOnClick(() -> ActiveMembersListPage.show());
        panel.add(image7);

        ImagePanel image8 = new ImagePanel("images/InactiveMembersList.png");
        image8.setOnClick(() -> InactiveMembersListPage.show());
        panel.add(image8);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                int contentY = 80;
                int contentHeight = Math.max(0, h - contentY);
                int halfWidth = w / 2;
                int halfHeight = contentHeight / 2;

                //the header keeps its fixed height while the menu grid takes the rest
                topBar.setBounds(0, 0, w, 80);
                image1.setBounds(10, 0, 200, 79);
                menuLabel.setBounds(0, 16, w, 40);
                logoutBtn.setBounds(w - 120, 20, 100, 30);

                image5.setBounds(0, contentY, halfWidth, halfHeight);
                image6.setBounds(0, contentY + halfHeight, halfWidth, contentHeight - halfHeight);
                image7.setBounds(halfWidth, contentY, w - halfWidth, halfHeight);
                image8.setBounds(halfWidth, contentY + halfHeight, w - halfWidth, contentHeight - halfHeight);
            }
        });

        Main.setPage(panel);
    }
}
