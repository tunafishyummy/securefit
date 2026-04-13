import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

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

        // 1. Create the Panel to show the video feed
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        // 2. Switch the UI: Clear MainMenu and show the Camera
        Main.window.getContentPane().removeAll();
        JPanel container = new JPanel(new BorderLayout());
        container.add(panel, BorderLayout.CENTER);
        
        Main.window.add(container);
        Main.window.revalidate();
        Main.window.repaint();

        // 3. Start the detection thread
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