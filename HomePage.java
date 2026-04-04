import javax.swing.*;
import java.awt.*;    
    
public class HomePage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);
        
        //image stuff
        ImagePanel image1 = new ImagePanel("images/MainMenu.png", 1096, 226, 631, 100);
        image1.setOnClick(() -> { 
            if (!Auth.isLoggedIn()) {
                MainMenuPage.show();
                return;
            }
            LoggedInMainMenuPage.show();
        });
        panel.add(image1);
        
        ImagePanel image2 = new ImagePanel("images/AdminMenu.png", 1096, 343, 631, 100);
        image2.setOnClick(() -> AdminMenuPage.show());
        panel.add(image2);
        
        ImagePanel image3 = new ImagePanel("images/Exit.png", 1096, 460, 631, 100);
        image3.setOnClick(() -> System.exit(0));
        panel.add(image3);
        
        ImagePanel image4 = new ImagePanel("images/MainLogo.png", 0, 100, 776, 728);
        panel.add(image4);
        
        ImagePanel image5 = new ImagePanel("images/SmallLogo.png", 1, 1, 50, 50);
        panel.add(image5);
        
        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}