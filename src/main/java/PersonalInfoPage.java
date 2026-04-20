import java.awt.Color;
import java.awt.Cursor;
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
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class PersonalInfoPage {
    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        String currentEmail = Auth.getCurrentUser();
        String[] data = MemberDB.getMemberData(currentEmail);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel title = new JLabel("Personal Info", SwingConstants.CENTER);
        title.setFont(new Font("Bebas Neue", Font.PLAIN, 34));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel firstLabel = new JLabel("FIRST NAME");
        firstLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        firstLabel.setForeground(Color.WHITE);
        firstLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField firstField = new JTextField(data[0]);
        firstField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel lastLabel = new JLabel("LAST NAME");
        lastLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        lastLabel.setForeground(Color.WHITE);
        lastLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField lastField = new JTextField(data[1]);
        lastField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel emailLabel = new JLabel("EMAIL ADDRESS");
        emailLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField emailField = new JTextField(data[2]);
        emailField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel phoneLabel = new JLabel("PHONE NUMBER");
        phoneLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        phoneLabel.setForeground(Color.WHITE);
        phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JTextField phoneField = new JTextField(data[3]);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel passLabel = new JLabel("NEW PASSWORD (LEAVE BLANK TO KEEP)");
        passLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        passLabel.setForeground(Color.WHITE);
        passLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel expireLabel = new JLabel("MEMBERSHIP EXPIRY DATE");
        expireLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        expireLabel.setForeground(Color.WHITE);
        expireLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        String expiry = MemberDB.getExpiry(Auth.getCurrentUser());
        JLabel expireDate = new JLabel(expiry != null ? "Expires: " + expiry : "");
        expireDate.setFont(new Font("Arial", Font.PLAIN, 20));
        expireDate.setForeground(Color.WHITE);

        panel.add(firstLabel);
        panel.add(firstField);
        panel.add(lastLabel);
        panel.add(lastField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(expireLabel);
        panel.add(expireDate);

        JButton saveButton = new JButton("SAVE CHANGES");
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        saveButton.setFocusPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        backButton.setForeground(Color.WHITE);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> LoggedInMainMenuPage.show());
        panel.add(backButton);

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
                    File dir = new File("qrcodes");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    BufferedImage newQr = QrCodeGen.generateQR(nEmail);
                    QrCodeGen.saveQRImage(newQr, nEmail);
                    Auth.login(nEmail);
                    QrImage.show(nEmail);
                } else {
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
                int w = panel.getWidth();
                int h = panel.getHeight();
                int formLeft = (int) (w * 0.33);
                int formWidth = (int) (w * 0.34);
                int formRight = formLeft + formWidth;
                int rowHeight = 38;
                int labelHeight = 28;
                int gapY = (int) (h * 0.10);
                int startY = (int) (h * 0.19);

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);

                title.setBounds(formLeft, (int) (h * 0.10), formWidth, 50);

                firstLabel.setBounds(formLeft, startY, formWidth, labelHeight);
                firstField.setBounds(formLeft, startY + 28, formWidth, rowHeight);

                lastLabel.setBounds(formLeft, startY + gapY, formWidth, labelHeight);
                lastField.setBounds(formLeft, startY + gapY + 28, formWidth, rowHeight);

                emailLabel.setBounds(formLeft, startY + (gapY * 2), formWidth, labelHeight);
                emailField.setBounds(formLeft, startY + (gapY * 2) + 28, formWidth, rowHeight);

                phoneLabel.setBounds(formLeft, startY + (gapY * 3), formWidth, labelHeight);
                phoneField.setBounds(formLeft, startY + (gapY * 3) + 28, formWidth, rowHeight);

                passLabel.setBounds(formLeft, startY + (gapY * 4), formWidth, labelHeight);
                passField.setBounds(formLeft, startY + (gapY * 4) + 28, formWidth, rowHeight);

                expireLabel.setBounds(formLeft, startY + (gapY * 5), formWidth, labelHeight);
                expireDate.setBounds(formLeft, startY + (gapY * 5) + 28, formWidth, rowHeight);

                saveButton.setBounds(formRight - Math.max(180, (int) (formWidth * 0.42)), startY + (gapY * 6) + 20, Math.max(180, (int) (formWidth * 0.42)), 45);
                backButton.setBounds((int) (w * 0.05), (int) (h * 0.88), 120, 40);
            }
        });

        Main.setPage(panel);
    }
}
