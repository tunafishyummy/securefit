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
        
        ImagePanel image2 = new ImagePanel("images/AdminMenu.png", 0, 0, 0, 0);
        image2.setOnClick(() -> AdminMenuPage.show());
        panel.add(image2);
        
        ImagePanel image3 = new ImagePanel("images/Exit.png", 0, 0, 0, 0);
        image3.setOnClick(() -> System.exit(0));
        panel.add(image3);
        
        ImagePanel image4 = new ImagePanel("images/MainLogo.png", 0, 0, 0, 0);
        panel.add(image4);
        
        ImagePanel image5 = new ImagePanel("images/SmallLogo.png", 0, 0, 0, 0);
        panel.add(image5);

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();

    }

} 