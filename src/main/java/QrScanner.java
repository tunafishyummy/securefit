import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
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
        if (webcam == null) return;

        webcam.open();
        
        while (true) {
            BufferedImage image = webcam.getImage();
            if (image == null) continue;

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Result result = new MultiFormatReader().decode(bitmap);
                String scannedEmail = result.getText();

                // DATABASE CONNECTION POINT
                if (MemberDB.emailExists(scannedEmail)) {
                    System.out.println("Access Granted: " + scannedEmail);
                    // Open your LoggedInMainMenuPage or Success Screen here
                    break; 
                } else {
                    System.out.println("Access Denied: Member not found.");
                }

            } catch (NotFoundException e) {
                // No QR code found in this frame, keep looking
            }
        }
        webcam.close();
    }
}