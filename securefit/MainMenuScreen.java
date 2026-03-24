import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends JFrame {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenuScreen();
        });
    }
    
    public MainMenuScreen() {
        setUndecorated(true); // true = no window borders
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main menu
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        //Black Top Bar
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(Color.BLACK);
        topBar.setPreferredSize(new Dimension(0, 78));
        mainPanel.add(topBar, BorderLayout.NORTH);
        
        // Centering
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setBackground(Color.WHITE);
        
        // Big circle things
        JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw circle
                int size = 800;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;
                
                g2d.setColor(Color.WHITE);
                g2d.fillOval(x, y, size, size);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(15));
                g2d.drawOval(x, y, size, size);
            }
        };
        logoPanel.setBackground(Color.WHITE);
        
        // RIGHT
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(Color.WHITE);
        
        // Center buttons vertically
        buttonsPanel.add(Box.createVerticalGlue());
        
        JButton mainMenuBtn = createBlackBorderButton("Main Menu");
        JButton adminMenuBtn = createBlackBorderButton("Admin Menu");
        JButton exitBtn = createBlackBorderButton("Exit");
        
        mainMenuBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "WIP");
        });
        
        adminMenuBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "WIP");
        });
        
        exitBtn.addActionListener(e -> System.exit(0));
        
        buttonsPanel.add(mainMenuBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(adminMenuBtn);
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonsPanel.add(exitBtn);
        
        buttonsPanel.add(Box.createVerticalGlue());
        
        // Add to center panel
        centerPanel.add(logoPanel);
        centerPanel.add(buttonsPanel);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    // Button Settings
    private JButton createBlackBorderButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Prompt", Font.PLAIN, 64));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(500, 90));
        button.setPreferredSize(new Dimension(500, 90));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 7));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }
}