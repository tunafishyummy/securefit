import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class LoginPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image2 = new ImagePanel("images/ContinuewithQRCode.png");
        panel.add(image2);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        panel.add(emailLabel);
        JTextField emailField = new JTextField();
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton signInButton = new JButton("Sign in");
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

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                image1.setBounds(10, 10, 50, 50);
                image2.setBounds((int) (w * 0.385), (int) (h * 0.159), (int) (w * 0.226), (int) (h * 0.065));

                emailLabel.setBounds((int) (w * 0.422), (int) (h * 0.361), (int) (w * 0.10), 20);
                emailField.setBounds((int) (w * 0.422), (int) (h * 0.380), (int) (w * 0.156), 30);

                passwordLabel.setBounds((int) (w * 0.422), (int) (h * 0.426), (int) (w * 0.08), 20);
                passwordField.setBounds((int) (w * 0.422), (int) (h * 0.444), (int) (w * 0.156), 30);

                signInButton.setBounds((int) (w * 0.458), (int) (h * 0.630), 150, 40);
            }
        });
    
        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
