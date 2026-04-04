import javax.swing.*;
import java.awt.*;

public class TextPanel extends JButton {
    public TextPanel(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setBackground(Color.WHITE);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void onClick(Runnable action) {
        addActionListener(e -> action.run());
    }
}