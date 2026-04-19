import javax.swing.*; //swing again. We use jpanel a lot
import java.awt.*; //awt. layout and colors etc
import java.awt.event.ComponentEvent; //This detects when the window is resized and is used by ComponentAdapter
import java.awt.event.ComponentAdapter; //This is the "listener"

//no third-party libraries

//this is Homepage.java, the landing screen upon running the program

public class HomePage {
    public static void show() {
        JPanel panel = new JPanel(null); //a new panel with a null layout
                                         //a null layout means components must be placed manually

        final int[] clickCount = {0};
        final long[] lastClickTime = {0}; //for opening the admin menu with 5 clicks on the small logo 
        panel.setBackground(Color.BLACK); //yeah

        //image stuff
        ImagePanel image1 = new ImagePanel("images/ScanQRCode.png"); //imagepanel!
        image1.setOnClick(() -> {
        new Thread(() -> {
        System.out.println("Scanner started...");
        QrScanner.startScanning();
        }).start();
        });
        panel.add(image1);
        
        ImagePanel image2 = new ImagePanel("images/Login.png"); //imagepanel!
        image2.setOnClick(() -> { 
            if (!Auth.isLoggedIn()) { 
                LoginPage.show(); //if not logged in, show login page
                return;
            }
            LoggedInMainMenuPage.show(); //if logged in, show logged in menu
                                         //dependent on logic in Auth.java
        });                       //this has click logic
        panel.add(image2);
        
        ImagePanel image3 = new ImagePanel("images/Register.png"); //this has click logic
        image3.setOnClick(() -> SignupMenuPage.show());
        panel.add(image3);
        
        ImagePanel image5 = new ImagePanel("images/SmallLogo.png"); //visual
        image5.setOnClick(() -> {
         long now = System.currentTimeMillis();

        //reset if too slow between clicks
        if (now - lastClickTime[0] > 2000) {
        clickCount[0] = 0;
          }

        clickCount[0]++;
        lastClickTime[0] = now;

        if (clickCount[0] >= 5) {
        clickCount[0] = 0; // reset after triggering
        AdminLoginPage.show();
    }
});
        panel.add(image5);

        //resizer behavior
        panel.addComponentListener(new ComponentAdapter() { //all the logic for resizing. This all allows for a stretchy window AND elements, as opposed to stiff. Note buddy man component adapter
            @Override //ComponentAdapter holds empty stuff. Override says we are overriding that empty stuff with our own
            public void componentResized(ComponentEvent e) { //you can probably just copy most of this
                int w = panel.getWidth(); //and this
                int h = panel.getHeight(); //and this
                
                // Stacked buttons on the right (approx 57% across the screen)
                image1.setBounds((int)(w * 0.01), (int)(h * 0.12), (int)(w * 0.53), (int)(h * 0.86)); //.5 = 50% across the screen etc etc
                image2.setBounds((int)(w * 0.54), (int)(h * 0.12), (int)(w * 0.45), (int)(h * 0.43)); // same for y value
                image3.setBounds((int)(w * 0.54), (int)(h * 0.55), (int)(w * 0.45), (int)(h * 0.43)); // requires some trial and error
                
                // Small logo top left (fixed ITS FIXED NOTE ITS FIXED LIKE UNMOVING)
                image5.setBounds(10, 0, 200, 79); 
            }
        });

        Main.setPage(panel);

    }

} 
