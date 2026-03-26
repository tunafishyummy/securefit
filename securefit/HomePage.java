import javax.swing.*;
import java.awt.*;    
    
public class HomePage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 0, Main.window.getWidth(), Main.window.getHeight());
        panel.setBackground(Color.WHITE);
        
        //image stuff
        ImagePanel image1 = new ImagePanel("securefit/images/Image1.png", 1300, 100, 200, 200);
        image1.setOnClick(() -> MainMenuPage.show());
        panel.add(image1);
        
        ImagePanel image2 = new ImagePanel("securefit/images/Image2.png", 1300, 350, 200, 200);
        image2.setOnClick(() -> AdminMenuPage.show());
        panel.add(image2);
        
        ImagePanel image3 = new ImagePanel("securefit/images/Image3.png", 1300, 600, 200, 200);
        image3.setOnClick(() -> System.exit(0));
        panel.add(image3);
        
        ImagePanel image4 = new ImagePanel("securefit/images/Image4.png", -200, 250, 600, 600);
        panel.add(image4);
        
        ImagePanel image5 = new ImagePanel("securefit/images/Image5.png", 1, 1, 200, 200);
        panel.add(image5);
        
        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}