import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CustomTitleBar extends JPanel {
    private static final Color BAR_COLOR = Color.BLACK;
    private static final Color MINIMIZE_HOVER = new Color(0, 120, 215);
    private static final Color MAXIMIZE_HOVER = new Color(46, 160, 67);
    private static final Color CLOSE_HOVER = new Color(232, 17, 35);

    private Point clickPoint;
    private final JButton maximize;

    public CustomTitleBar() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(0, 40));

        JLabel title = new JLabel("SecureFit");
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
        buttons.setOpaque(false);

        JButton minimize = new JButton("_");
        maximize = new JButton("[ ]");
        JButton close = new JButton("X");

        styleWindowButton(minimize, MINIMIZE_HOVER);
        styleWindowButton(maximize, MAXIMIZE_HOVER);
        styleWindowButton(close, CLOSE_HOVER);

        minimize.addActionListener(e -> Main.window.setState(Frame.ICONIFIED));
        maximize.addActionListener(e -> toggleMaximize());
        close.addActionListener(e -> Main.window.dispose());

        buttons.add(minimize);
        buttons.add(maximize);
        buttons.add(close);

        add(title, BorderLayout.WEST);
        add(buttons, BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickPoint = e.getPoint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    toggleMaximize();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isMaximized()) {
                    return;
                }
                Point win = Main.window.getLocation();
                Main.window.setLocation(
                    win.x + e.getX() - clickPoint.x,
                    win.y + e.getY() - clickPoint.y
                );
            }
        });
    }

    private void toggleMaximize() {
        if (isMaximized()) {
            Main.window.setExtendedState(JFrame.NORMAL);
            maximize.setText("[ ]");
        } else {
            Main.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            maximize.setText("[]");
        }
    }

    private boolean isMaximized() {
        return (Main.window.getExtendedState() & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;
    }

    private void styleWindowButton(JButton button, Color hoverColor) {
        button.setForeground(Color.WHITE);
        button.setBackground(BAR_COLOR);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(48, 28));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BAR_COLOR);
            }
        });
    }
}

