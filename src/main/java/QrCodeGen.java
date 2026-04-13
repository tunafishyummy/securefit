import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.nayuki.qrcodegen.QrCode;

public class QrCodeGen {
    public static BufferedImage generateQR(String text) {
        QrCode qr = QrCode.encodeText(text, QrCode.Ecc.MEDIUM);
        BufferedImage img = new BufferedImage(qr.size, qr.size, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < qr.size; y++) {
            for (int x = 0; x < qr.size; x++) {
                img.setRGB(x, y, qr.getModule(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return img;
    }
    
    public static void saveQRImage(BufferedImage img, String fileName) {
        try {
            File folder = new File("qrcodes");
            File outputFile = new File(folder, fileName + ".png");
            ImageIO.write(img, "png", outputFile);
            System.out.println("QR Code saved: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving QR image: " + e.getMessage());
        }
    }
}