import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
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

//hello, this is the qr based log in screen
//in QrScanner.java, we have one method, startScanning.
//he is diligent and does four jobs:
// 1. opens the default webcam and detects if it exists (immediately below)
// 2. replaces the SWwing window with the back button and the webcam feed
// 3. keeps grabbing frames from the webcam (like a webcam feed would)
// 4. it tries to decode a qr code from each and every single frame
// take a look 

public class QrScanner {
    public static void startScanning() {
    Webcam webcam = Webcam.getDefault();
    if (webcam == null) {
        System.out.println("No webcam detected");
        return;
    }

    // webcam
    WebcamPanel webPanel = new WebcamPanel(webcam);
    webPanel.setFPSDisplayed(true);
    webPanel.setMirrored(true);

    // header with the logo
    JPanel headerPanel = new JPanel(null);
    headerPanel.setBackground(Color.BLACK);
    headerPanel.setPreferredSize(new Dimension(Main.window.getWidth(), 80));
    
    ImagePanel logo = new ImagePanel("images/SmallLogo.png");
    logo.setBounds(10, 0, 200, 79); 
    logo.setOnClick(() -> HomePage.show());
    headerPanel.add(logo);

    // main container
    JPanel mainContainer = new JPanel(new BorderLayout());
    
    // top header
    mainContainer.add(headerPanel, BorderLayout.NORTH);

    // webcam
    mainContainer.add(webPanel, BorderLayout.CENTER);

    // back
    JButton backBtn = new JButton("Back");
    backBtn.setFont(new Font("Arial", Font.BOLD, 14));
    backBtn.addActionListener(e -> {
        webcam.close();
        HomePage.show();
    });
    mainContainer.add(backBtn, BorderLayout.SOUTH);
    Main.setPage(mainContainer);

    // scan
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

            }
        }
    }).start();
}
}
