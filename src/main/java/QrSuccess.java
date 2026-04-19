import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class QrSuccess {
    public static void show(BufferedImage qrCode, String userName) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        // QR Code Display
        Image scaledQR = qrCode.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
        JLabel qrLabel = new JLabel(new ImageIcon(scaledQR));
        qrLabel.setBounds(465, 100, 350, 350);
        qrLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel.add(qrLabel);

        // Instruction Label
        JLabel instructionLabel = new JLabel("PLEASE TAKE A PICTURE", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Prompt", Font.PLAIN, 24));
        instructionLabel.setBounds(440, 480, 400, 40);
        instructionLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panel.add(instructionLabel);

        // Continue Button
        JButton continueBtn = new JButton("Continue");
        continueBtn.setFont(new Font("Prompt", Font.PLAIN, 28));
        continueBtn.setForeground(Color.BLACK);
        continueBtn.setContentAreaFilled(false);
        continueBtn.setBorderPainted(false);
        continueBtn.setFocusPainted(false);
        continueBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        continueBtn.setBounds(1050, 620, 200, 40);
        continueBtn.addActionListener(e -> HomePage.show());
        panel.add(continueBtn);


        Main.setPage(panel);
    }
}
