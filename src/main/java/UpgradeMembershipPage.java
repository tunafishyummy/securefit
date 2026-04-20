import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UpgradeMembershipPage {
    private static String selectedType;
    private static boolean selectedTrainer;

    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.BLACK);

        String email = Auth.getCurrentUser();
        String[] current = MemberDB.getMembershipStatus(email);
        selectedType = current[0];
        selectedTrainer = Boolean.parseBoolean(current[1]);

        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel titleLabel = new JLabel("Upgrade Membership", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 34));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);

        JLabel typeLabel = new JLabel("TYPE OF MEMBERSHIP");
        typeLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        typeLabel.setForeground(Color.WHITE);
        typeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(typeLabel);

        String[] types = {"ONE TIME SESSION", "WEEKLY", "MONTHLY", "YEARLY"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setSelectedItem(selectedType);
        typeBox.setFont(new Font("Bebas Neue", Font.PLAIN, 18));
        panel.add(typeBox);

        JCheckBox trainerCheck = new JCheckBox("WITH TRAINER");
        trainerCheck.setSelected(selectedTrainer);
        trainerCheck.setBackground(Color.BLACK);
        trainerCheck.setForeground(Color.WHITE);
        trainerCheck.setFont(new Font("Bebas Neue", Font.PLAIN, 20));
        panel.add(trainerCheck);

        JLabel priceLabel = new JLabel("\u20B1" + MemberDB.calculateCost(selectedType, selectedTrainer));
        priceLabel.setFont(new Font("Bebas Neue", Font.PLAIN, 28));
        priceLabel.setForeground(new Color(34, 139, 34));
        priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(priceLabel);

        JButton upgradeBtn = new JButton("UPGRADE");
        upgradeBtn.setBackground(Color.BLACK);
        upgradeBtn.setForeground(Color.WHITE);
        upgradeBtn.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        upgradeBtn.setFocusPainted(false);
        upgradeBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.add(upgradeBtn);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Bebas Neue", Font.PLAIN, 24));
        backButton.setForeground(Color.WHITE);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> LoggedInMainMenuPage.show());
        panel.add(backButton);

        typeBox.addActionListener(e -> {
            selectedType = (String) typeBox.getSelectedItem();
            priceLabel.setText("\u20B1" + MemberDB.calculateCost(selectedType, selectedTrainer));
        });

        trainerCheck.addActionListener(e -> {
            selectedTrainer = trainerCheck.isSelected();
            priceLabel.setText("\u20B1" + MemberDB.calculateCost(selectedType, selectedTrainer));
        });

        upgradeBtn.addActionListener(e -> {
            MemberDB.upgradeMembership(email, selectedType, selectedTrainer);
            JOptionPane.showMessageDialog(Main.window, "Membership Successfully Upgraded!");
            LoggedInMainMenuPage.show();
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();
                int formLeft = (int) (w * 0.33);
                int formWidth = (int) (w * 0.34);
                int formRight = formLeft + formWidth;
                int rowHeight = 38;
                int labelHeight = 28;
                int gapY = (int) (h * 0.10);
                int startY = (int) (h * 0.26);
                int membershipWidth = (int) (formWidth * 0.52);

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);

                titleLabel.setBounds(formLeft, (int) (h * 0.10), formWidth, 50);

                typeLabel.setBounds(formLeft, startY, formWidth, labelHeight);
                typeBox.setBounds(formLeft, startY + 28, membershipWidth, rowHeight);

                trainerCheck.setBounds(formLeft + membershipWidth + 15, startY + 26, formWidth - membershipWidth - 15, rowHeight);

                priceLabel.setBounds(formLeft, startY + gapY, formWidth, 30);

                upgradeBtn.setBounds(formRight - Math.max(180, (int) (formWidth * 0.42)), startY + gapY + 40, Math.max(180, (int) (formWidth * 0.42)), 45);
                backButton.setBounds((int) (w * 0.05), (int) (h * 0.88), 120, 40);
            }
        });

        Main.setPage(panel);
    }
}
