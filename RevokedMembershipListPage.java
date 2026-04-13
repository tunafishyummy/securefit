import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;

public class RevokedMembershipListPage {

    public static void show() {
        Main.window.getContentPane().removeAll();

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        // --- Black Top Bar ---
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        // Logo
        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        // Title in top bar
        JLabel title = new JLabel("Revoked Members");
        title.setFont(new Font("Arial", Font.PLAIN, 16));
        title.setForeground(Color.WHITE);
        topBar.add(title);

        // --- Table Setup ---
        String[] columns = {
            "Name", "Mobile No.", "Email Address",
            "Type of Membership", "W/Trainer",
            "Status of Membership", "Reason"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        // Load revoked members from DB
        loadRevokedMembers(model);

        JTable table = new JTable(model);
        table.setBackground(new Color(180, 180, 180));
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(55);
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.CENTER);

        // Alternating row colors + center align
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (isSelected) {
                    setBackground(new Color(120, 120, 200));
                } else {
                    setBackground(row % 2 == 0
                        ? new Color(190, 190, 190)
                        : new Color(160, 160, 160));
                }
                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        panel.add(scrollPane);

        // --- Back Button ---
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 28));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> AdminMenuPage.show());
        panel.add(backBtn);

        // --- Responsive Layout ---
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 60);
                logo.setBounds(10, 5, 50, 50);
                title.setBounds(70, 15, 300, 30);

                scrollPane.setBounds(20, 65, w - 40, h - 160);

                int tableBottom = 65 + (h - 160);
                backBtn.setBounds(30, tableBottom + 20, 120, 45);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }

    private static void loadRevokedMembers(DefaultTableModel model) {
        // TODO: Once you add a 'revoked' status and 'reason' column to your DB,
        // query with: SELECT * FROM members WHERE status = 'REVOKED'
        // For now, loads all members as placeholder
        String sql = "SELECT first, last, phone, email, type, trainer FROM members";
        try {
            java.lang.reflect.Field f = MemberDB.class.getDeclaredField("connection");
            f.setAccessible(true);
            Connection conn = (Connection) f.get(null);

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String name    = rs.getString("first") + " " + rs.getString("last");
                    String phone   = rs.getString("phone");
                    String email   = rs.getString("email");
                    String type    = rs.getString("type");
                    String trainer = rs.getBoolean("trainer") ? "YES" : "NO";
                    String status  = "REVOKED";
                    String reason  = "-"; // placeholder until reason column is added
                    model.addRow(new Object[]{name, phone, email, type, trainer, status, reason});
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}