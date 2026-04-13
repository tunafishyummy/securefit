import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class SignupMenuPage {
    private static String firstName = "";
    private static String lastName = "";
    private static String email = "";
    private static String password = "";
    private static String phoneNumber = "";
    private static String membershipType = "MONTHLY";
    private static boolean withTrainer = false;

    private static int getPrice(String type, boolean trainer) {
        switch (type.toUpperCase()) {
            case "ONE TIME SESSION": return trainer ? 300 : 150;
            case "WEEKLY":          return trainer ? 1250 : 750;
            case "MONTHLY":         return trainer ? 3000 : 1500;
            case "YEARLY":          return trainer ? 15000 : 7500;
            default:                return 0;
        }
    }

    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        JLabel title1 = new JLabel("GYM REGISTRATION");
        title1.setFont(new Font("Prompt", Font.BOLD, 36)); // reduced from 64
        panel.add(title1);

        JLabel firstNameLabel = new JLabel("FIRST NAME");
        panel.add(firstNameLabel);
        JTextField firstNameField = new JTextField(firstName);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("LAST NAME");
        panel.add(lastNameLabel);
        JTextField lastNameField = new JTextField(lastName);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        panel.add(emailLabel);
        JTextField emailField = new JTextField(email);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(password);
        panel.add(passwordField);

        JLabel phoneLabel = new JLabel("PHONE NUMBER");
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField(phoneNumber);
        panel.add(phoneField);

        JLabel membershipLabel = new JLabel("TYPE OF MEMBERSHIP");
        panel.add(membershipLabel);
        JComboBox<String> membershipBox = new JComboBox<>(new String[]{"MONTHLY", "YEARLY", "WEEKLY", "ONE TIME SESSION"});
        membershipBox.setSelectedItem(membershipType);
        panel.add(membershipBox);

        JCheckBox trainerCheckBox = new JCheckBox("WITH TRAINER");
        trainerCheckBox.setSelected(withTrainer);
        trainerCheckBox.setBackground(Color.WHITE);
        panel.add(trainerCheckBox);

        // --- Price Indicator ---
        JLabel priceLabel = new JLabel("\u20B1" + getPrice(membershipType, withTrainer));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 15));
        priceLabel.setForeground(new Color(0x228B22));
        panel.add(priceLabel);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(registerButton);

        // --- Back Button ---
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setForeground(Color.BLACK);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> MainMenuPage.show());
        panel.add(backBtn);

        membershipBox.addActionListener(e -> {
            membershipType = (String) membershipBox.getSelectedItem();
            priceLabel.setText("\u20B1" + getPrice(membershipType, withTrainer));
        });

        trainerCheckBox.addActionListener(e -> {
            withTrainer = trainerCheckBox.isSelected();
            priceLabel.setText("\u20B1" + getPrice(membershipType, withTrainer));
        });

        registerButton.addActionListener(e -> {
            firstName = firstNameField.getText().trim();
            lastName = lastNameField.getText().trim();
            email = emailField.getText().trim();
            password = new String(passwordField.getPassword()).trim();
            phoneNumber = phoneField.getText().trim();
            
            // Exception Handling
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
                password.isEmpty() || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(Main.window, "Please fill in all fields.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!phoneNumber.matches("\\d+")) {
                JOptionPane.showMessageDialog(Main.window, "Phone number must contain numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (MemberDB.emailExists(email)) {
                JOptionPane.showMessageDialog(Main.window, "Email is already registered.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                MemberDB.save(firstName, lastName, email, password, phoneNumber, membershipType, withTrainer);
                BufferedImage qrImage = QrCodeGen.generateQR(email);
                String safeFileName = firstName + "_" + lastName;
                QrCodeGen.saveQRImage(qrImage, safeFileName);
                QrSuccess.show(qrImage, firstName);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(Main.window, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                double col = 0.35;   // left edge of form fields
                double fw  = 0.30;   // field width

                image1.setBounds(10, 10, 50, 50);

                // Title centered, smaller font
                title1.setBounds((int)(w * 0.28), (int)(h * 0.06), (int)(w * 0.44), 45);

                firstNameLabel.setBounds((int)(w * col), (int)(h * 0.160), (int)(w * fw), 18);
                firstNameField.setBounds((int)(w * col), (int)(h * 0.185), (int)(w * fw), 28);

                lastNameLabel.setBounds((int)(w * col), (int)(h * 0.240), (int)(w * fw), 18);
                lastNameField.setBounds((int)(w * col), (int)(h * 0.265), (int)(w * fw), 28);

                emailLabel.setBounds((int)(w * col), (int)(h * 0.320), (int)(w * fw), 18);
                emailField.setBounds((int)(w * col), (int)(h * 0.345), (int)(w * fw), 28);

                passwordLabel.setBounds((int)(w * col), (int)(h * 0.400), (int)(w * fw), 18);
                passwordField.setBounds((int)(w * col), (int)(h * 0.425), (int)(w * fw), 28);

                phoneLabel.setBounds((int)(w * col), (int)(h * 0.480), (int)(w * fw), 18);
                phoneField.setBounds((int)(w * col), (int)(h * 0.505), (int)(w * fw), 28);

                membershipLabel.setBounds((int)(w * col), (int)(h * 0.555), (int)(w * fw), 18);
                membershipBox.setBounds((int)(w * col), (int)(h * 0.578), (int)(w * 0.14), 28);
                trainerCheckBox.setBounds((int)(w * col + w * 0.15), (int)(h * 0.578), (int)(w * 0.14), 28);

                priceLabel.setBounds((int)(w * col), (int)(h * 0.625), (int)(w * 0.20), 22);

                registerButton.setBounds((int)(w * col), (int)(h * 0.665), (int)(w * 0.14), 36);

                backBtn.setBounds(20, (int)(h * 0.920), 80, 30);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}