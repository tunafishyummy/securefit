import javax.swing.*;
import java.awt.*;

public class LoginPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png", 1, 1, 50, 50);
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image2 = new ImagePanel("images/ContinuewithQRCode.png", 740, 172, 434, 70);
        panel.add(image2);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        emailLabel.setBounds(810, 390, 120, 20);
        panel.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(810, 410, 300, 30);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setBounds(810, 460, 100, 20);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(810, 480, 300, 30);
        panel.add(passwordField);

        JButton signInButton = new JButton("Sign in");
        signInButton.setBounds(880, 680, 150, 40);
        signInButton.setBackground(Color.BLACK);
        signInButton.setForeground(Color.WHITE);
        panel.add(signInButton);
        signInButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            email = emailField.getText().trim();
            password = new String(passwordField.getPassword()).trim();
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
    
        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}