import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

//this is the shared image-backed button for most of the ui

public class ImagePanel extends JPanel {
    //these constants make the hover wash easy to tune in one place
    public static final int HOVER_OVERLAY_RED = 255;
    public static final int HOVER_OVERLAY_GREEN = 255;
    public static final int HOVER_OVERLAY_BLUE = 255;
    public static final int HOVER_OVERLAY_ALPHA = 50;

    private final Image image;
    private final boolean hoverOverlayEnabled;
    private Runnable clickAction = null;
    private boolean hovered = false;
    public ImagePanel(String filename) {
        setBackground(Color.WHITE);

        image = new ImageIcon(filename).getImage();
        hoverOverlayEnabled = !filename.replace("\\", "/").endsWith("SmallLogo.png");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (clickAction != null) {
                    clickAction.run();
                } 
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                repaint();
            }
        });
    }
    public void setOnClick(Runnable action) {
        this.clickAction = action;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            //the image stretches to the panel bounds, so layout code controls the final look
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
        if (hovered && hoverOverlayEnabled) {
            g.setColor(new Color(
                HOVER_OVERLAY_RED,
                HOVER_OVERLAY_GREEN,
                HOVER_OVERLAY_BLUE,
                HOVER_OVERLAY_ALPHA
            ));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
