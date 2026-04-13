
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PersonalInfoPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        String currentEmail = Auth.getCurrentUser();
        String[] data = MemberDB.getMemberData(currentEmail);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        JLabel title1 = new JLabel("EDIT PERSONAL INFO");
        title1.setFont(new Font("Prompt", Font.BOLD, 36));
        panel.add(title1);

        JLabel firstNameLabel = new JLabel("FIRST NAME");
        panel.add(firstNameLabel);
        JTextField firstNameField = new JTextField(data[0]);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("LAST NAME");
        panel.add(lastNameLabel);
        JTextField lastNameField = new JTextField(data[1]);
        panel.add(lastNameField);

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        panel.add(emailLabel);
        JTextField emailField = new JTextField(data[2]);
        panel.add(emailField);

        JLabel phoneLabel = new JLabel("PHONE NUMBER");
        panel.add(phoneLabel);
        JTextField phoneField = new JTextField(data[3]);
        panel.add(phoneField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(saveButton);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setForeground(Color.BLACK);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> LoggedInMainMenuPage.show());
        panel.add(backBtn);

        saveButton.addActionListener(e -> {
            String nFirst = firstNameField.getText().trim();
            String nLast = lastNameField.getText().trim();
            String nEmail = emailField.getText().trim();
            String nPhone = phoneField.getText().trim();

            if (nFirst.isEmpty() || nEmail.isEmpty()) {
                JOptionPane.showMessageDialog(Main.window, "First Name and Email are required.");
                return;
            }

            if (!nEmail.equalsIgnoreCase(currentEmail)) {
                if (MemberDB.emailExists(nEmail)) {
                    JOptionPane.showMessageDialog(Main.window, "Email already exists.");
                    return;
                }
                
                MemberDB.updateMember(currentEmail, nFirst, nLast, nEmail, nPhone);
                
                // Regenerate QR because email changed
                BufferedImage newQr = QrCodeGen.generateQR(nEmail);
                QrCodeGen.saveQRImage(newQr, nEmail);
                
                Auth.login(nEmail); 
                JOptionPane.showMessageDialog(Main.window, "Profile and QR Code Updated!");
            } else {
                MemberDB.updateMember(currentEmail, nFirst, nLast, nEmail, nPhone);
                JOptionPane.showMessageDialog(Main.window, "Profile Updated!");
            }
            LoggedInMainMenuPage.show();
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                double col = 0.35;
                double fw  = 0.30;

                image1.setBounds(10, 10, 50, 50);
                title1.setBounds((int)(w * 0.28), (int)(h * 0.06), (int)(w * 0.44), 45);
                
                firstNameLabel.setBounds((int)(w * col), (int)(h * 0.160), (int)(w * fw), 18);
                firstNameField.setBounds((int)(w * col), (int)(h * 0.185), (int)(w * fw), 28);
                
                lastNameLabel.setBounds((int)(w * col), (int)(h * 0.240), (int)(w * fw), 18);
                lastNameField.setBounds((int)(w * col), (int)(h * 0.265), (int)(w * fw), 28);
                
                emailLabel.setBounds((int)(w * col), (int)(h * 0.320), (int)(w * fw), 18);
                emailField.setBounds((int)(w * col), (int)(h * 0.345), (int)(w * fw), 28);
                
                phoneLabel.setBounds((int)(w * col), (int)(h * 0.400), (int)(w * fw), 18);
                phoneField.setBounds((int)(w * col), (int)(h * 0.425), (int)(w * fw), 28);

                saveButton.setBounds((int)(w * col), (int)(h * 0.500), (int)(w * 0.14), 36);
                backBtn.setBounds(20, (int)(h * 0.920), 80, 30);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}