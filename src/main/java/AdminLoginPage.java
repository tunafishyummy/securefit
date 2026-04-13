import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class AdminLoginPage {
    private static final String ADMIN_PASSWORD = "admin123";

    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // --- Black Top Bar ---
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        topBar.add(image1);

        // --- Form ---
        JLabel titleLabel = new JLabel("Enter Admin Password");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.putClientProperty("JTextField.placeholderText", "ENTER ADMIN PASSWORD");
        panel.add(passwordField);

        JButton signInBtn = new JButton("SIGN IN");
        signInBtn.setBackground(Color.WHITE);
        signInBtn.setForeground(Color.DARK_GRAY);
        signInBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signInBtn.setFocusPainted(false);
        panel.add(signInBtn);

        // --- Back Button ---
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setForeground(Color.BLACK);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> HomePage.show());
        panel.add(backBtn);

        signInBtn.addActionListener(e -> {
            String entered = new String(passwordField.getPassword()).trim();
            try {
                if (entered.isEmpty()) {
                    throw new Exception("Please enter the admin password.");
                }
                if (!entered.equals(ADMIN_PASSWORD)) {
                    throw new Exception("Incorrect password. Access denied.");
                }
                AdminMenuPage.show();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Main.window, ex.getMessage(), "Login Error", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 60);
                image1.setBounds(10, 5, 50, 50);

                titleLabel.setBounds((int)(w * 0.22), (int)(h * 0.25), (int)(w * 0.30), 30);
                passwordField.setBounds((int)(w * 0.22), (int)(h * 0.32), (int)(w * 0.55), 50);
                signInBtn.setBounds((int)(w * 0.38), (int)(h * 0.65), (int)(w * 0.22), 50);
                backBtn.setBounds(20, (int)(h * 0.92), 80, 30);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
