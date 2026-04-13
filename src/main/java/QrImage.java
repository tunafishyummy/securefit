import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class QrImage {
    public static void show(String email) {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("MY QR CODE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);

        try {
            File file = new File("qrcodes/" + email + ".png");

            if (file.exists()) {
                BufferedImage img = ImageIO.read(file);
                // Resize for display
                Image scaled = img.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                JLabel qrLabel = new JLabel(new ImageIcon(scaled));
                panel.add(qrLabel, BorderLayout.CENTER);
            } else {
                JLabel error = new JLabel("QR Code file not found.", SwingConstants.CENTER);
                panel.add(error, BorderLayout.CENTER);
            }
        } catch (java.io.IOException | java.awt.image.ImagingOpException e) {
            System.err.println("Error loading QR code: " + e.getMessage());
        }

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> LoggedInMainMenuPage.show());
        panel.add(backBtn, BorderLayout.SOUTH);

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}