import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class QrImage {
    public static void show(String email) {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel title = new JLabel("My QR Code", SwingConstants.CENTER);
        title.setFont(FontLoader.bebasNeue(Font.PLAIN, 34));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JPanel qrFrame = new JPanel(null);
        qrFrame.setBackground(Color.WHITE);
        qrFrame.setBorder(BorderFactory.createLineBorder(Color.WHITE, 12));
        panel.add(qrFrame);

        JLabel qrLabel = new JLabel("", SwingConstants.CENTER);
        qrFrame.add(qrLabel);

        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        errorLabel.setForeground(Color.WHITE);
        panel.add(errorLabel);

        try {
            File file = new File("qrcodes/" + email + ".png");

            if (file.exists()) {
                BufferedImage img = ImageIO.read(file);
                Image scaled = img.getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                qrLabel.setIcon(new ImageIcon(scaled));
            } else {
                errorLabel.setText("QR Code file not found.");
            }
        } catch (java.io.IOException | java.awt.image.ImagingOpException e) {
            System.err.println("Error loading QR code: " + e.getMessage());
            errorLabel.setText("Error loading QR code.");
        }

        JButton backBtn = new JButton("Back");
        backBtn.setFont(FontLoader.bebasNeue(Font.PLAIN, 24));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> LoggedInMainMenuPage.show());
        panel.add(backBtn);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);
                title.setBounds((int) (w * 0.33), (int) (h * 0.12), (int) (w * 0.34), 50);
                qrFrame.setBounds((w - 390) / 2, (int) (h * 0.22), 390, 390);
                qrLabel.setBounds(20, 20, 350, 350);
                errorLabel.setBounds((int) (w * 0.25), (int) (h * 0.40), (int) (w * 0.50), 30);
                backBtn.setBounds((int) (w * 0.05), (int) (h * 0.88), 120, 40);
            }
        });

        Main.setPage(panel);
    }
}

