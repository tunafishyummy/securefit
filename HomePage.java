import javax.swing.*;
import java.awt.*;    
import java.awt.event.ComponentEvent; //This detects when the window is resized and is used by ComponentAdapter
import java.awt.event.ComponentAdapter; //This is the "listener"


public class HomePage {
    public static void show() {
        Main.window.getContentPane().removeAll();

        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        //image stuff
        ImagePanel image1 = new ImagePanel("images/MainMenu.png", 0, 0, 0, 0);
        image1.setOnClick(() -> { 
            if (!Auth.isLoggedIn()) {
                MainMenuPage.show();
                return;
            }
            LoggedInMainMenuPage.show();
        });
        panel.add(image1);
        
        ImagePanel image2 = new ImagePanel("images/AdminMenu.png", 0, 0, 0, 0); //these are dummy values that will be overriden later
        image2.setOnClick(() -> AdminMenuPage.show());
        panel.add(image2);
        
        ImagePanel image3 = new ImagePanel("images/Exit.png", 0, 0, 0, 0);
        image3.setOnClick(() -> System.exit(0));
        panel.add(image3);
        
        ImagePanel image4 = new ImagePanel("images/MainLogo.png", 0, 0, 0, 0);
        panel.add(image4);
        
        ImagePanel image5 = new ImagePanel("images/SmallLogo.png", 0, 0, 0, 0);
        panel.add(image5);

        panel.addComponentListener(new ComponentAdapter() { //all the logic for resizing. Note buddy man component adapter
            @Override //ComponentAdapter holds empty stuff. Override says we are overriding that empty stuff with our own
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                
                // setBounds( X, Y, Width, Height )
                
                // Stacked buttons on the right (approx 57% across the screen)
                image1.setBounds((int)(w * 0.57), (int)(h * 0.21), (int)(w * 0.33), (int)(h * 0.09)); //.5 = 50% across the screen etc etc
                image2.setBounds((int)(w * 0.57), (int)(h * 0.32), (int)(w * 0.33), (int)(h * 0.09)); // same for y value
                image3.setBounds((int)(w * 0.57), (int)(h * 0.43), (int)(w * 0.33), (int)(h * 0.09)); // requires some trial and error
                
                // Big logo on the left
                image4.setBounds(0, (int)(h * 0.09), (int)(w * 0.40), (int)(h * 0.67));
                
                // Small logo top left (fixed ITS FIXED NOTE ITS FIXED LIKE UNMOVING)
                image5.setBounds(10, 10, 50, 50); 
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();

    }

} 