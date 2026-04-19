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
import javax.swing.SwingConstants;

public class AdminLoginPage {
    private static final String ADMIN_PASSWORD = "admin123";

    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        //Black top bar
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        //loog
        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        //
        JLabel titleLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.putClientProperty("JTextField.placeholderText", "ENTER ADMIN PASSWORD");
        panel.add(passwordField);

        JButton signInBtn = new JButton("SIGN IN");
        signInBtn.setBackground(Color.BLACK);
        signInBtn.setForeground(Color.WHITE);
        signInBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signInBtn.setFocusPainted(false);
        signInBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(signInBtn);

        signInBtn.addActionListener(e -> {
            String entered = new String(passwordField.getPassword()).trim();
            if (entered.equals(ADMIN_PASSWORD)) {
                AdminMenuPage.show();
            } else {
                JOptionPane.showMessageDialog(Main.window, "Incorrect password.", "Access Denied", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79); 

                titleLabel.setBounds(0, (int)(h * 0.25), w, 40);
                passwordField.setBounds((int)(w * 0.3), (int)(h * 0.35), (int)(w * 0.4), 45);
                signInBtn.setBounds((int)(w * 0.4), (int)(h * 0.45), (int)(w * 0.2), 45);
                
            }
        });

        Main.setPage(panel);
    }
}
