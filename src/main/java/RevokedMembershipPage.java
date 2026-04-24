import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RevokedMembershipPage {
    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel titleLabel = new JLabel("Revoke Membership", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Enter member name", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(nameField);

        JLabel reasonLabel = new JLabel("Reason for revoking", SwingConstants.CENTER);
        reasonLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        reasonLabel.setForeground(Color.WHITE);
        panel.add(reasonLabel);

        JTextField reasonField = new JTextField();
        reasonField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(reasonField);

        JButton revokeBtn = new JButton("REVOKE");
        revokeBtn.setBackground(Color.BLACK);
        revokeBtn.setForeground(Color.WHITE);
        revokeBtn.setFont(new Font("Arial", Font.BOLD, 14));
        revokeBtn.setFocusPainted(false);
        revokeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(revokeBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(FontLoader.bebasNeue(Font.PLAIN, 24));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> AdminMenuPage.show());
        panel.add(backBtn);

        revokeBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String reason = reasonField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a member name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (reason.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter a reason.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "UPDATE members SET status = 'REVOKED', reason = ? WHERE (first || ' ' || last) = ?";
            try {
                Connection conn = MemberDB.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, reason);
                ps.setString(2, name);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(panel,
                        "Membership revoked for: " + name + "\nReason: " + reason,
                        "Revoked", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    reasonField.setText("");
                } else {
                    JOptionPane.showMessageDialog(panel,
                        "Member not found: " + name,
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                System.err.println("Error revoking member: " + ex.getMessage());
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);

                titleLabel.setBounds(0, (int) (h * 0.22), w, 40);
                nameLabel.setBounds((int) (w * 0.3), (int) (h * 0.33), (int) (w * 0.4), 25);
                nameField.setBounds((int) (w * 0.3), (int) (h * 0.37), (int) (w * 0.4), 45);
                reasonLabel.setBounds((int) (w * 0.3), (int) (h * 0.45), (int) (w * 0.4), 25);
                reasonField.setBounds((int) (w * 0.3), (int) (h * 0.49), (int) (w * 0.4), 45);
                revokeBtn.setBounds((int) (w * 0.4), (int) (h * 0.60), (int) (w * 0.2), 45);
                backBtn.setBounds(30, (int) (h * 0.88), 120, 40);
            }
        });

        Main.setPage(panel);
    }
}

