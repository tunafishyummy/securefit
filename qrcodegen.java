import java.awt.image.BufferedImage;
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
}