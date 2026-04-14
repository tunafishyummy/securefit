import javax.swing.*;
import java.awt.*; //power couple

//this is TextPanel, our reusable "text button" class.
//very lightweight

public class TextPanel extends JButton { //get juked. It is not jpanel, it's a jbutton extension
    public TextPanel(String text, int x, int y, int width, int height) { 
        super(text); //sets the text
        setBounds(x, y, width, height); //positions the button. Nice because we're null layouted 
        setBackground(Color.WHITE); //
        setFocusPainted(false); //removes the focus thing swing does
        setBorder(BorderFactory.createEmptyBorder()); //removes the border. It looks flat now
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //hand cursor
    }

    public void onClick(Runnable action) { //does the click action. Used by other classes
        addActionListener(e -> action.run());
    }
}