import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> { //I heard this does really good shit for stability
        window = new JFrame("Home");
        
        //Let's address the window's scope
        window.setSize(1280,720); //this is the INITIAL, DEFAULT size
        window.setMinimumSize(new Dimension(800, 600)); //this is the MINIMUM size
        window.setResizable(true); //this allows for resizing the window, but not smaller than the minimum size
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setUndecorated(false); //set to true for borderless
        window.setLayout(new BorderLayout()); //as opposed to null, BorderLayout allows elements to fill the space when the window is stretched
        window.getContentPane().setBackground(Color.WHITE);
       
        HomePage.show();
       
        window.setVisible(true);

        });
        
    }
}