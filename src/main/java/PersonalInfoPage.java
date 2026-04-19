import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PersonalInfoPage {
    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        String currentEmail = Auth.getCurrentUser();
        String[] data = MemberDB.getMemberData(currentEmail);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> LoggedInMainMenuPage.show());
        panel.add(logo);

        JLabel title = new JLabel("PERSONAL INFO");
        title.setFont(new Font("Prompt", Font.BOLD, 36));
        panel.add(title);

        // Labels & Fields
        JLabel firstLabel = new JLabel("FIRST NAME");
        JTextField firstField = new JTextField(data[0]);
        JLabel lastLabel = new JLabel("LAST NAME");
        JTextField lastField = new JTextField(data[1]);
        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        JTextField emailField = new JTextField(data[2]);
        JLabel phoneLabel = new JLabel("PHONE NUMBER");
        JTextField phoneField = new JTextField(data[3]);
        JLabel passLabel = new JLabel("NEW PASSWORD (LEAVE BLANK TO KEEP)");
        JPasswordField passField = new JPasswordField();
        JLabel expireLabel = new JLabel("MEMBERSHIP EXPIRY DATE");
        String expiry = MemberDB.getExpiry(Auth.getCurrentUser());
        JLabel expireDate = new JLabel(expiry != null ? "Expires: " + expiry : "");

        panel.add(firstLabel); panel.add(firstField);
        panel.add(lastLabel); panel.add(lastField);
        panel.add(emailLabel); panel.add(emailField);
        panel.add(phoneLabel); panel.add(phoneField);
        panel.add(passLabel); panel.add(passField);
        panel.add(expireLabel); panel.add(expireDate);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(saveButton);

        saveButton.addActionListener(e -> {
            String nFirst = firstField.getText().trim();
            String nLast = lastField.getText().trim();
            String nEmail = emailField.getText().trim();
            String nPhone = phoneField.getText().trim();
            String nPass = new String(passField.getPassword()).trim();

            if (nFirst.isEmpty() || nLast.isEmpty() || nEmail.isEmpty() || nPhone.isEmpty()) {
                JOptionPane.showMessageDialog(Main.window, "All fields except password are required.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean isEmailChanged = !nEmail.equalsIgnoreCase(currentEmail);
            boolean success = MemberDB.updateMember(currentEmail, nFirst, nLast, nEmail, nPhone, nPass);

            if (success) {
                if (isEmailChanged) {
                    // 1. Force directory check
                    File dir = new File("qrcodes");
                    if (!dir.exists()) dir.mkdirs();

                    // 2. Generate and Save
                    BufferedImage newQr = QrCodeGen.generateQR(nEmail);
                    QrCodeGen.saveQRImage(newQr, nEmail);
                    
                    // 3. Update Session
                    Auth.login(nEmail);
                    
                    // 4. GO STRAIGHT TO QR IMAGE (No JOptionPane)
                    QrImage.show(nEmail); 
                } else {
                    // Normal update: show success and go to Menu
                    JOptionPane.showMessageDialog(Main.window, "Profile updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    LoggedInMainMenuPage.show();
                }
            } else {
                JOptionPane.showMessageDialog(Main.window, "Update failed. Email might already be in use.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth(), h = panel.getHeight();
                double colX = 0.35, fieldW = 0.30;
                int startY = (int)(h * 0.15), spacing = 65;

                logo.setBounds(10, 10, 50, 50);
                title.setBounds((int)(w * 0.28), (int)(h * 0.05), (int)(w * 0.5), 45);
                
                firstLabel.setBounds((int)(w * colX), startY, (int)(w * fieldW), 20);
                firstField.setBounds((int)(w * colX), startY + 22, (int)(w * fieldW), 30);
                
                lastLabel.setBounds((int)(w * colX), startY + spacing, (int)(w * fieldW), 20);
                lastField.setBounds((int)(w * colX), startY + spacing + 22, (int)(w * fieldW), 30);
                
                emailLabel.setBounds((int)(w * colX), startY + (spacing * 2), (int)(w * fieldW), 20);
                emailField.setBounds((int)(w * colX), startY + (spacing * 2) + 22, (int)(w * fieldW), 30);
                
                phoneLabel.setBounds((int)(w * colX), startY + (spacing * 3), (int)(w * fieldW), 20);
                phoneField.setBounds((int)(w * colX), startY + (spacing * 3) + 22, (int)(w * fieldW), 30);
                
                passLabel.setBounds((int)(w * colX), startY + (spacing * 4), (int)(w * fieldW), 20);
                passField.setBounds((int)(w * colX), startY + (spacing * 4) + 22, (int)(w * fieldW), 30);

                expireLabel.setBounds((int)(w * colX), startY + (spacing * 5), (int)(w * fieldW), 20);
                expireDate.setBounds((int)(w * colX), startY + (spacing * 5) + 22, (int)(w * fieldW), 30);

                saveButton.setBounds((int)(w * colX), startY + (spacing * 6), 180, 40);
            }
        });

        Main.setPage(panel);
    }
}
