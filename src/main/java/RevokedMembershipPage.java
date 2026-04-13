import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RevokedMembershipPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // --- Black Top Bar ---
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        // Logo inside black bar
        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        // --- Title ---
        JLabel title = new JLabel("Revoke Membership(for refunds)");
        title.setFont(new Font("Arial", Font.PLAIN, 16));
        title.setForeground(Color.WHITE);
        topBar.add(title);

        // --- Enter Name ---
        JLabel nameLabel = new JLabel("Enter Name");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setForeground(Color.GRAY);
        nameField.setText("ENTER MEMBER NAME");
        nameField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.add(nameField);

        // --- Reason for Revoking ---
        JLabel reasonLabel = new JLabel("Reason for revoking");
        reasonLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(reasonLabel);

        JTextField reasonField = new JTextField();
        reasonField.setFont(new Font("Arial", Font.PLAIN, 14));
        reasonField.setForeground(Color.GRAY);
        reasonField.setText("ENTER REASON");
        reasonField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        panel.add(reasonField);

        // --- Revoke Button ---
        JButton revokeBtn = new JButton("Revoke");
        revokeBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        revokeBtn.setBackground(Color.WHITE);
        revokeBtn.setFocusPainted(false);
        revokeBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        revokeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        revokeBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String reason = reasonField.getText().trim();
            // TODO: handle revoke logic (e.g. mark member as revoked in DB)
            JOptionPane.showMessageDialog(panel,
                "Membership revoked for: " + name + "\nReason: " + reason,
                "Revoked", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(revokeBtn);

        // --- Back Button ---
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 28));
        backBtn.setForeground(Color.BLACK);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> AdminMenuPage.show());
        panel.add(backBtn);

        // --- Responsive Layout ---
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                // Top bar
                topBar.setBounds(0, 0, w, 60);
                logo.setBounds(10, 5, 50, 50);
                title.setBounds(70, 15, 400, 30);

                // Form center
                int formX = (int) (w * 0.20);
                int formW = (int) (w * 0.60);

                nameLabel.setBounds(formX, (int) (h * 0.25), 150, 20);
                nameField.setBounds(formX, (int) (h * 0.28), formW, 50);

                reasonLabel.setBounds(formX, (int) (h * 0.44), 150, 20);
                reasonField.setBounds(formX, (int) (h * 0.47), formW, 50);

                revokeBtn.setBounds(formX + (formW / 2) - 75, (int) (h * 0.65), 150, 45);

                backBtn.setBounds(30, (int) (h * 0.88), 120, 40);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}