import javax.swing.*;
import java.awt.*;

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
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png", 1, 1, 50, 50);
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        JLabel title1 = new JLabel("GYM REGISTRATION");
        title1.setFont(new Font("Prompt", Font.BOLD, 64));
        title1.setBounds(642, 158, 684, 56);
        panel.add(title1);

        JLabel firstNameLabel = new JLabel("FIRST NAME");
        firstNameLabel.setBounds(800, 250, 100, 20);
        panel.add(firstNameLabel);
        JTextField firstNameField = new JTextField();
        firstNameField.setBounds(800, 270, 300, 30);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("LAST NAME");
        lastNameLabel.setBounds(800, 320, 100, 20);
        panel.add(lastNameLabel);
        JTextField lastNameField = new JTextField();
        lastNameField.setBounds(800, 340, 300, 30);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        emailLabel.setBounds(800, 390, 120, 20);
        panel.add(emailLabel);
        JTextField emailField = new JTextField();
        emailField.setBounds(800, 410, 300, 30);
        panel.add(emailField);

        JLabel passwordLabel = new JLabel("PASSWORD");
        passwordLabel.setBounds(800, 460, 100, 20);
        panel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(800, 480, 300, 30);
        panel.add(passwordField);

        JLabel phoneLabel = new JLabel("PHONE NUMBER");
        phoneLabel.setBounds(800, 530, 120, 20);
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(800, 550, 300, 30);
        panel.add(phoneField);

        JLabel membershipLabel = new JLabel("TYPE OF MEMBERSHIP");
        membershipLabel.setBounds(800, 600, 150, 20);
        panel.add(membershipLabel);
        JComboBox<String> membershipBox = new JComboBox<>(new String[]{"MONTHLY", "YEARLY", "WEEKLY", "ONE TIME SESSION"});
        membershipBox.setBounds(800, 620, 150, 30);
        panel.add(membershipBox);

        JCheckBox trainerCheckBox = new JCheckBox("WITH TRAINER");
        trainerCheckBox.setBounds(970, 620, 120, 30);
        panel.add(trainerCheckBox);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(800, 680, 150, 40);
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        panel.add(registerButton);
        registerButton.addActionListener(e ->  {
            firstName = firstNameField.getText().trim();
            lastName = lastNameField.getText().trim();
            email = emailField.getText().trim();
            password = new String(passwordField.getPassword()).trim();
            phoneNumber = phoneField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()     || password.isEmpty() || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in every field.", "Missing info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {

                JOptionPane.showMessageDialog(null, "Enter a valid e-mail address.", "Invalid e-mail", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (MemberDB.emailExists(email)) {
                JOptionPane.showMessageDialog(null, "E-mail already registered.", "Duplicate", JOptionPane.WARNING_MESSAGE);
                return;
            }
            MemberDB.save(firstName, lastName, email, password, phoneNumber, membershipType, withTrainer);
            JOptionPane.showMessageDialog(null, "Account created");
        });
        
        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}