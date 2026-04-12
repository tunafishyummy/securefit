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

    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        JLabel title1 = new JLabel("GYM REGISTRATION");
        title1.setFont(new Font("Prompt", Font.BOLD, 64));
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
        panel.add(trainerCheckBox);

        JButton registerButton = new JButton("Register");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        panel.add(registerButton);
        
        // --- Back Button ---
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 18));
        backBtn.setForeground(Color.BLACK);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> MainMenuPage.show());
        panel.add(backBtn);


        membershipBox.addActionListener(e -> membershipType = (String) membershipBox.getSelectedItem());
        trainerCheckBox.addActionListener(e -> withTrainer = trainerCheckBox.isSelected());

        registerButton.addActionListener(e -> {
            firstName = firstNameField.getText().trim();
            lastName = lastNameField.getText().trim();
            email = emailField.getText().trim();
            password = new String(passwordField.getPassword()).trim();
            phoneNumber = phoneField.getText().trim();

            MemberDB.save(firstName, lastName, email, password, phoneNumber, membershipType, withTrainer);
            BufferedImage qrImage = QrCodeGen.generateQR(email);
            QrSuccess.show(qrImage, firstName);
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                image1.setBounds(10, 10, 50, 50);
                title1.setBounds((int) (w * 0.335), (int) (h * 0.146), (int) (w * 0.356), 56);
                firstNameLabel.setBounds((int) (w * 0.417), (int) (h * 0.231), 100, 20);
                firstNameField.setBounds((int) (w * 0.417), (int) (h * 0.250), (int) (w * 0.156), 30);
                lastNameLabel.setBounds((int) (w * 0.417), (int) (h * 0.296), 100, 20);
                lastNameField.setBounds((int) (w * 0.417), (int) (h * 0.315), (int) (w * 0.156), 30);
                emailLabel.setBounds((int) (w * 0.417), (int) (h * 0.361), 120, 20);
                emailField.setBounds((int) (w * 0.417), (int) (h * 0.380), (int) (w * 0.156), 30);
                passwordLabel.setBounds((int) (w * 0.417), (int) (h * 0.426), 100, 20);
                passwordField.setBounds((int) (w * 0.417), (int) (h * 0.444), (int) (w * 0.156), 30);
                phoneLabel.setBounds((int) (w * 0.417), (int) (h * 0.491), 120, 20);
                phoneField.setBounds((int) (w * 0.417), (int) (h * 0.509), (int) (w * 0.156), 30);
                membershipLabel.setBounds((int) (w * 0.417), (int) (h * 0.556), 150, 20);
                membershipBox.setBounds((int) (w * 0.417), (int) (h * 0.574), (int) (w * 0.078), 30);
                trainerCheckBox.setBounds((int) (w * 0.505), (int) (h * 0.574), (int) (w * 0.100), 30);
                registerButton.setBounds((int) (w * 0.417), (int) (h * 0.630), 150, 40);
                backBtn.setBounds(20, (int) (h * 0.90), 100, 35);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
