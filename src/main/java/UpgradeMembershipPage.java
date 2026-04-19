import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class UpgradeMembershipPage {
    private static String selectedType;
    private static boolean selectedTrainer;

    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        String email = Auth.getCurrentUser();
        String[] current = MemberDB.getMembershipStatus(email);
        selectedType = current[0];
        selectedTrainer = Boolean.parseBoolean(current[1]);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        panel.add(logo);

        JLabel typeLabel = new JLabel("TYPE OF MEMBERSHIP");
        typeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(typeLabel);

        String[] types = {"ONE TIME SESSION", "WEEKLY", "MONTHLY", "YEARLY"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setSelectedItem(selectedType);
        panel.add(typeBox);

        JCheckBox trainerCheck = new JCheckBox("WITH TRAINER");
        trainerCheck.setSelected(selectedTrainer);
        trainerCheck.setBackground(Color.WHITE);
        trainerCheck.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(trainerCheck);

        JLabel priceLabel = new JLabel("₱" + MemberDB.calculateCost(selectedType, selectedTrainer));
        priceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        priceLabel.setForeground(new Color(34, 139, 34)); // Dark Green
        panel.add(priceLabel);

        JButton upgradeBtn = new JButton("Upgrade");
        upgradeBtn.setBackground(Color.BLACK);
        upgradeBtn.setForeground(Color.WHITE);
        upgradeBtn.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(upgradeBtn);

        // Listeners for live price updates
        typeBox.addActionListener(e -> {
            selectedType = (String) typeBox.getSelectedItem();
            priceLabel.setText("₱" + MemberDB.calculateCost(selectedType, selectedTrainer));
        });

        trainerCheck.addActionListener(e -> {
            selectedTrainer = trainerCheck.isSelected();
            priceLabel.setText("₱" + MemberDB.calculateCost(selectedType, selectedTrainer));
        });

        upgradeBtn.addActionListener(e -> {
            MemberDB.upgradeMembership(email, selectedType, selectedTrainer);
            JOptionPane.showMessageDialog(Main.window, "Membership Successfully Upgraded!");
            LoggedInMainMenuPage.show();
        });
        
        // Back Button
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setForeground(Color.BLACK);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> LoggedInMainMenuPage.show());
        panel.add(backBtn);
        
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                int startX = (int)(w * 0.35);

                logo.setBounds(10, 10, 50, 50);
                
                typeLabel.setBounds(startX, (int)(h * 0.30), 200, 20);
                typeBox.setBounds(startX, (int)(h * 0.33), 250, 35);
                
                trainerCheck.setBounds(startX + 260, (int)(h * 0.33), 150, 35);
                
                priceLabel.setBounds(startX, (int)(h * 0.40), 200, 30);
                
                upgradeBtn.setBounds(startX, (int)(h * 0.46), 250, 45);

                backBtn.setBounds(20, (int)(h * 0.92), 80, 30);
            }
        });

        Main.setPage(panel);
    }
}
