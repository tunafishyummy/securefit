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

//this screen swaps in the webcam view and keeps scanning until it finds a valid qr code

public class QrScanner {
    public static void startScanning() {
    Webcam webcam = Webcam.getDefault();
    if (webcam == null) {
        System.out.println("No webcam detected");
        return;
    }

    //the webcam panel gives us the live feed right away
    WebcamPanel webPanel = new WebcamPanel(webcam);
    webPanel.setFPSDisplayed(true);
    webPanel.setMirrored(true);

    //this header keeps the scanner in the same family as the other pages
    JPanel headerPanel = new JPanel(null);
    headerPanel.setBackground(Color.BLACK);
    headerPanel.setPreferredSize(new Dimension(Main.window.getWidth(), 80));
    
    ImagePanel logo = new ImagePanel("images/SmallLogo.png");
    logo.setBounds(10, 0, 200, 79); 
    logo.setOnClick(() -> HomePage.show());
    headerPanel.add(logo);

    //borderlayout makes this screen easy to split into header, feed, and back button
    JPanel mainContainer = new JPanel(new BorderLayout());
    
    mainContainer.add(headerPanel, BorderLayout.NORTH);

    mainContainer.add(webPanel, BorderLayout.CENTER);

    //back closes the webcam before we leave the page
    JButton backBtn = new JButton("Back");
    backBtn.setFont(new Font("Arial", Font.BOLD, 14));
    backBtn.addActionListener(e -> {
        webcam.close();
        HomePage.show();
    });
    mainContainer.add(backBtn, BorderLayout.SOUTH);
    Main.setPage(mainContainer);

    //this worker loop keeps checking frames until a matching account is found
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
