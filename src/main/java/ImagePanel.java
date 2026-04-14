import java.awt.Color; //typical awt
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter; //awt.event is about input and stuff
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon; //swing
import javax.swing.JPanel;

//this is ImagePanel, our reusable "image button" class.
//this is an extension of JPanel
//it allows for an image to be painted on and a click action to be set

public class ImagePanel extends JPanel { //being an extension, we can put imagepanel anywhere jpanels go
    private final Image image; //stores the loaded image for the lifetime of the component
    private Runnable clickAction = null; //stores the code when an image is clicked
    public ImagePanel(String filename) { //constructor. Receives the image file
        setBackground(Color.WHITE); //

        image = new ImageIcon(filename).getImage(); //this loads the image into memory
        addMouseListener(new MouseAdapter() { //a convenience class like component adapter
                                              //it has empty methods, so use override on the ones you need
            @Override //overrides with our own code
            public void mouseClicked(MouseEvent e) { //when click, run code
                if (clickAction != null) {
                    clickAction.run();
                } 
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {  //when mouse hover, change to the hand cursor
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {   //when mouse no hover, change to the default cursor
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    public void setOnClick(Runnable action) { //how the click action is set. Used by other classes
        this.clickAction = action;
    }
    @Override
    protected void paintComponent(Graphics g) { //the image stuff
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this); //paints the image
                                                                     //crucially, it stretches the image to the
                                                                     //panel size, so you can change with
                                                                     //setBounds and it will stretch the image to fit.
                                                                     //efficient!
        }
    }
}