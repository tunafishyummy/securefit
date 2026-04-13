import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;


public class QrScanner {
    public static void startScanning() {
        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("No webcam detected");
            return;
        }

        
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        
        Main.window.getContentPane().removeAll();
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.addActionListener(e -> {
        webcam.close();
        MainMenuPage.show();
});
container.add(backBtn, BorderLayout.SOUTH);
        
        Main.window.add(container);
        Main.window.revalidate();
        Main.window.repaint();

        
        new Thread(() -> {
            while (true) {
                if (!webcam.isOpen()) continue;
                
                BufferedImage image = webcam.getImage();
                if (image == null) continue;

                try {
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = new MultiFormatReader().decode(bitmap);
                    
                    String scannedEmail = result.getText();
                    
                    // DATABASE LINK
                    if (MemberDB.emailExists(scannedEmail)) {
                        System.out.println("Access Granted: " + scannedEmail);
                        Auth.login(scannedEmail);
                        webcam.close(); 
                        
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            LoggedInMainMenuPage.show();
                        });
                        break;
                    }
                } catch (NotFoundException e) {
                    // QR not found in this frame, loop continues
                }
            }
        }).start();
    }
}