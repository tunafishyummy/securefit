import javax.swing.*;
import java.awt.*;
public class Main {
    public static JFrame window;
    public static void main(String[] args) {

        window = new JFrame("Home");
        //window.setSize(800, 600);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setUndecorated(false); //set to true for borderless
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null); //for manual placement
        window.getContentPane().setBackground(Color.WHITE);

        window.setVisible(true);
        HomePage.show();
    }
}