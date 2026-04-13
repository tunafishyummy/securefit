import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class UpgradeMembershipPage {
    public static void show() {
        Main.window.getContentPane().removeAll();
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ImagePanel image1 = new ImagePanel("images/SmallLogo.png");
        image1.setOnClick(() -> HomePage.show());
        panel.add(image1);

        ImagePanel image2 = new ImagePanel("images/MainLogo.png");
        panel.add(image2);

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                image1.setBounds((int)(w * 0.005), (int)(h * 0.009), (int)(w * 0.03), (int)(h * 0.05));
                image2.setBounds((int)(w * 0.56), (int)(h * 0.09), (int)(w * 0.40), (int)(h * 0.67));
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }
}
