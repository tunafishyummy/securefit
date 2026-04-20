import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension; //awt allows for stuff like colors, dimensions, layouts

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities; //swingy swing

//there are no third-party libraries in our main class

//hello, this is Main.java. We all know Main.java
//for this project, it is the app's entry point, not building all of the ui itself, but creating the first window
//under scrutiny, you can tell this class is solely for window setup

public class Main {
    public static JFrame window; //this is the main window that appears literally everywhere else
    public static JPanel contentPanel;
    public static void main(String[] args) { //JVM START!!!
        
        SwingUtilities.invokeLater(() -> { //I heard this does really good stuff for stability
                                           //invokelater says 'run this on the EDT'
                                           //EDT is the Event Dispatch Thread, a thread
                                           //it helps with Swing UI stability
        window = new JFrame("SecureFit"); //this is the aforementioned first window, made with swing, named Home
                                     //swing is the java gui toolkit youve heard of
        ImageIcon icon = new ImageIcon("images/Icon.png"); //securefit iconies
        Main.window.setIconImage(icon.getImage());
        
        //Let's address the window's scope
        window.setSize(1280,720); //this is the INITIAL, DEFAULT size
        window.setMinimumSize(new Dimension(800, 600)); //this is the MINIMUM size
        window.setResizable(true); //this allows for resizing the window, but not smaller than the minimum size
        window.setLocationRelativeTo(null); //this makes the window appear in the middle
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this stops the program when you exit the window with the X
        
        window.setUndecorated(true); //true so we can customize
        window.setLayout(new BorderLayout()); //as opposed to null, BorderLayout allows elements to fill the space when the window is stretched
        window.add(new CustomTitleBar(), BorderLayout.NORTH); //custom title bar
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.BLACK);
        window.add(contentPanel, BorderLayout.CENTER);
        window.getContentPane().setBackground(Color.BLACK);
       
        HomePage.show(); //it is passed to HomePage.java, where we see our first window built
       
        window.setVisible(true); //shows the window 10 on screen

        });
        
    }

    public static void setPage(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
