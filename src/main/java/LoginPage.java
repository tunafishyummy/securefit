import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class LoginPage {
    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel titleLabel = new JLabel("Member Login", SwingConstants.CENTER);
        titleLabel.setFont(FontLoader.bebasNeue(Font.PLAIN, 34));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        ImagePanel image2 = new ImagePanel("images/ContinuewithQRCode.png");
        image2.setOnClick(() -> {
        new Thread(() -> {
        System.out.println("Scanner started from Login...");
        QrScanner.startScanning();
    }).start();
});
        panel.add(image2);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        emailLabel.setFont(FontLoader.bebasNeue(Font.PLAIN, 24));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(FontLoader.bebasNeue(Font.PLAIN, 24));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(passwordField);

        JButton signInButton = new JButton("SIGN IN");
        signInButton.setBackground(Color.BLACK);
        signInButton.setForeground(Color.WHITE);
        signInButton.setFont(FontLoader.bebasNeue(Font.PLAIN, 24));
        signInButton.setFocusPainted(false);
        signInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(signInButton);
        signInButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter e-mail and password.", "Missing info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                JOptionPane.showMessageDialog(null, "Enter a valid e-mail address.", "Invalid e-mail", JOptionPane.WARNING_MESSAGE);
                return;
            }
             if (MemberDB.checkCredentials(email, password)) {
                Auth.login(email);
                LoggedInMainMenuPage.show();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid e-mail or password.", "Sign-In failed", JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                int formLeft = (int) (w * 0.58);
                int formWidth = (int) (w * 0.34);
                int formRight = formLeft + formWidth;
                int buttonWidth = Math.max(180, (int) (formWidth * 0.55));

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);

                titleLabel.setBounds(formLeft, (int) (h * 0.22), formWidth, 50);
                image2.setBounds((int)(w * 0.01), (int)(h * 0.12), (int)(w * 0.53), (int)(h * 0.86));

                emailLabel.setBounds(formLeft, (int) (h * 0.42), formWidth, 30);
                emailField.setBounds(formLeft, (int) (h * 0.47), formWidth, 40);

                passwordLabel.setBounds(formLeft, (int) (h * 0.56), formWidth, 30);
                passwordField.setBounds(formLeft, (int) (h * 0.61), formWidth, 40);

                signInButton.setBounds(formRight - buttonWidth, (int) (h * 0.72), buttonWidth, 45);
            }
        });
    
        Main.setPage(panel);
    }
}

