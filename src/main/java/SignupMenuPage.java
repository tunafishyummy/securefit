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
import javax.swing.SwingConstants;
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
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel title1 = new JLabel("Gym Registration", SwingConstants.CENTER);
        title1.setFont(new Font("Bebas Neue", Font.PLAIN, 34));
        title1.setForeground(Color.WHITE);
        panel.add(title1);

        JLabel firstNameLabel = new JLabel("FIRST NAME");
        firstNameLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        firstNameLabel.setForeground(Color.WHITE);
        firstNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(firstNameLabel);
        JTextField firstNameField = new JTextField(firstName);
        firstNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("LAST NAME");
        lastNameLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        lastNameLabel.setForeground(Color.WHITE);
        lastNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(lastNameLabel);
        JTextField lastNameField = new JTextField(lastName);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        emailLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(emailLabel);
        JTextField emailField = new JTextField(email);
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(password);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(passwordField);

        JLabel phoneLabel = new JLabel("PHONE NUMBER");
        phoneLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField(phoneNumber);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(phoneField);

        JLabel membershipLabel = new JLabel("TYPE OF MEMBERSHIP");
        membershipLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        membershipLabel.setForeground(Color.WHITE);
        membershipLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(membershipLabel);
        JComboBox<String> membershipBox = new JComboBox<>(new String[]{"MONTHLY", "YEARLY", "WEEKLY", "ONE TIME SESSION"});
        membershipBox.setSelectedItem(membershipType);
        membershipBox.setFont(new Font("Bebas Neue", Font.PLAIN, 18));
        panel.add(membershipBox);

        JCheckBox trainerCheckBox = new JCheckBox("WITH TRAINER");
        trainerCheckBox.setSelected(withTrainer);
        trainerCheckBox.setBackground(Color.BLACK);
        trainerCheckBox.setForeground(Color.WHITE);
        trainerCheckBox.setFont(new Font("Bebas Neue", Font.PLAIN, 20));
        panel.add(trainerCheckBox);

        JLabel priceLabel = new JLabel("\u20B1" + getPrice(membershipType, withTrainer));
        priceLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 28));
        priceLabel.setForeground(new Color(0x228B22));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(priceLabel);

        JButton registerButton = new JButton("REGISTER");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(registerButton);

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
            
            if (phoneNumber.length() != 11) {
                JOptionPane.showMessageDialog(Main.window, "Phone number must be exactly 11 digits.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
}
            if (!email.endsWith("@gmail.com")) {
                JOptionPane.showMessageDialog(Main.window, "Email must be a valid Gmail address (@gmail.com).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
}
            if (MemberDB.emailExists(email)) {
                JOptionPane.showMessageDialog(Main.window, "Email is already registered.", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                MemberDB.save(firstName, lastName, email, password, phoneNumber, membershipType, withTrainer);
                BufferedImage qrImage = QrCodeGen.generateQR(email);
                QrCodeGen.saveQRImage(qrImage, email);
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
                int formLeft = (int) (w * 0.33);
                int formWidth = (int) (w * 0.34);
                int formRight = formLeft + formWidth;
                int rowHeight = 38;
                int labelHeight = 28;
                int gapY = (int) (h * 0.10);
                int startY = (int) (h * 0.19);
                int membershipWidth = (int) (formWidth * 0.52);

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);

                title1.setBounds(formLeft, (int) (h * 0.10), formWidth, 50);

                firstNameLabel.setBounds(formLeft, startY, formWidth, labelHeight);
                firstNameField.setBounds(formLeft, startY + 28, formWidth, rowHeight);

                lastNameLabel.setBounds(formLeft, startY + gapY, formWidth, labelHeight);
                lastNameField.setBounds(formLeft, startY + gapY + 28, formWidth, rowHeight);

                emailLabel.setBounds(formLeft, startY + (gapY * 2), formWidth, labelHeight);
                emailField.setBounds(formLeft, startY + (gapY * 2) + 28, formWidth, rowHeight);

                passwordLabel.setBounds(formLeft, startY + (gapY * 3), formWidth, labelHeight);
                passwordField.setBounds(formLeft, startY + (gapY * 3) + 28, formWidth, rowHeight);

                phoneLabel.setBounds(formLeft, startY + (gapY * 4), formWidth, labelHeight);
                phoneField.setBounds(formLeft, startY + (gapY * 4) + 28, formWidth, rowHeight);

                membershipLabel.setBounds(formLeft, startY + (gapY * 5), formWidth, labelHeight);
                membershipBox.setBounds(formLeft, startY + (gapY * 5) + 28, membershipWidth, rowHeight);
                trainerCheckBox.setBounds(formLeft + membershipWidth + 15, startY + (gapY * 5) + 26, formWidth - membershipWidth - 15, rowHeight);

                priceLabel.setBounds(formLeft, startY + (gapY * 6) + 10, formWidth, 30);

                registerButton.setBounds(formRight - Math.max(180, (int) (formWidth * 0.42)), startY + (gapY * 6) + 50, Math.max(180, (int) (formWidth * 0.42)), 45);
            }
        });

        Main.setPage(panel);
    }
}
