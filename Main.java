import javax.swing.*;
import java.awt.*;
public class Main {
    public static JFrame window;
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> { //I heard this does really good shit for stability
        window = new JFrame("Home");
        window.setSize(800, 600);
        window.setUndecorated(false); //set to true for borderless
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.setLayout(null); //for manual placement
        window.getContentPane().setBackground(Color.WHITE);
       
        HomePage.show();
       
        window.setVisible(true);

        });
        
    }
}