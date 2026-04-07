import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdminMenuPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image4 = new ImagePanel("images/MainLogo.png");
        panel.add(image4);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                image1.setBounds(10, 10, 50, 50);
                image4.setBounds((int) (w * 0.563), (int) (h * 0.093), (int) (w * 0.404), (int) (h * 0.674));
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
