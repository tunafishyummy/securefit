import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;

//this is the landing screen, so most of the app branches out from here

public class HomePage {
    public static void show() {
        JPanel panel = new JPanel(null);

        final int[] clickCount = {0};
        final long[] lastClickTime = {0}; //five quick clicks on the small logo open the admin login
        panel.setBackground(Color.BLACK);

        ImagePanel image1 = new ImagePanel("images/ScanQRCode.png");
        image1.setOnClick(() -> {
        new Thread(() -> {
        System.out.println("Scanner started...");
        QrScanner.startScanning();
        }).start();
        });
        panel.add(image1);
        
        ImagePanel image2 = new ImagePanel("images/Login.png");
        image2.setOnClick(() -> { 
            if (!Auth.isLoggedIn()) { 
                LoginPage.show();
                return;
            }
            LoggedInMainMenuPage.show();
        });
        panel.add(image2);
        
        ImagePanel image3 = new ImagePanel("images/Register.png");
        image3.setOnClick(() -> SignupMenuPage.show());
        panel.add(image3);
        
        ImagePanel image5 = new ImagePanel("images/SmallLogo.png");
        image5.setOnClick(() -> {
         long now = System.currentTimeMillis();

        //if the clicks are too spread out, the hidden admin trigger resets
        if (now - lastClickTime[0] > 2000) {
        clickCount[0] = 0;
          }

        clickCount[0]++;
        lastClickTime[0] = now;

        if (clickCount[0] >= 5) {
        clickCount[0] = 0;
        AdminLoginPage.show();
    }
});
        panel.add(image5);

        //the home layout is all percentage-based so it stretches pretty cleanly
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                
                image1.setBounds((int)(w * 0.01), (int)(h * 0.12), (int)(w * 0.53), (int)(h * 0.86));
                image2.setBounds((int)(w * 0.54), (int)(h * 0.12), (int)(w * 0.45), (int)(h * 0.43));
                image3.setBounds((int)(w * 0.54), (int)(h * 0.55), (int)(w * 0.45), (int)(h * 0.43));
                
                //the small logo stays fixed so it always reads like a header mark
                image5.setBounds(10, 0, 200, 79); 
            }
        });

        Main.setPage(panel);

    }

} 
