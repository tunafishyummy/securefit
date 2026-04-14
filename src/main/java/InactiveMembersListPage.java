import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;

public class InactiveMembersListPage {

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

        // --- Table Setup ---
        String[] columns = {
            "Name", "Mobile No.", "Email Address",
            "Type of Membership", "W/Trainer",
            "Status of Membership", "Total Cost"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int[] totalProfit = {0};
        loadInactiveMembers(model, totalProfit);

        JTable table = new JTable(model);
        table.setBackground(new Color(180, 180, 180));
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(75);
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

        // Alternating row colors + center align + multiline status support
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                // Use HTML for multiline in status column
                if (col == 5 && value != null) {
                    String html = "<html><center>" +
                        value.toString().replace("\n", "<br>") +
                        "</center></html>";
                    super.getTableCellRendererComponent(t, html, isSelected, hasFocus, row, col);
                } else {
                    super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                }
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

        // --- Total Profit ---
        JLabel totalProfitLabel = new JLabel("Total Profit: ₱" + totalProfit[0]);
        totalProfitLabel.setFont(new Font("Arial", Font.BOLD, 13));
        totalProfitLabel.setForeground(Color.WHITE);
        totalProfitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(totalProfitLabel);

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

                scrollPane.setBounds(20, 65, w - 40, h - 175);

                int tableBottom = 65 + (h - 175);
                totalProfitLabel.setBounds(w - 220, tableBottom + 5, 180, 20);
                backBtn.setBounds(30, tableBottom + 20, 120, 45);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }

    private static void loadInactiveMembers(DefaultTableModel model, int[] totalProfit) {
        MemberDB.updateExpiredMembers();
        String sql = "SELECT first, last, phone, email, type, trainer, date_registered FROM members WHERE status = 'INACTIVE'";
        try {
            Connection conn = MemberDB.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String name       = rs.getString("first") + " " + rs.getString("last");
                    String phone      = rs.getString("phone");
                    String email      = rs.getString("email");
                    String type       = rs.getString("type");
                    boolean trainer   = rs.getBoolean("trainer");
                    String trainerStr = trainer ? "YES" : "NO";
                    String dateReg    = rs.getString("date_registered");
                    String expiredOn  = calculateExpiry(type, dateReg);
                    String status     = "INACTIVE\nExpired on: " + expiredOn;
                    int cost          = MemberDB.calculateCost(type, trainer);
                    totalProfit[0]   += cost;
                    model.addRow(new Object[]{
                        name, phone, email, type, trainerStr,
                        status, "₱" + cost
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static String calculateExpiry(String type, String dateRegistered) {
        if (dateRegistered == null) return "-";
        try {
            java.time.LocalDateTime regDate = java.time.LocalDateTime.parse(
                dateRegistered, java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );
            java.time.LocalDateTime expiry;
            switch (type.toUpperCase()) {
                case "ONE TIME SESSION": expiry = regDate.plusDays(1);   break;
                case "WEEKLY":          expiry = regDate.plusWeeks(1);   break;
                case "MONTHLY":         expiry = regDate.plusMonths(1);  break;
                case "YEARLY":          expiry = regDate.plusYears(1);   break;
                default:                return "-";
            }
            return expiry.format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy"));
        } catch (Exception e) {
            return "-";
        }
    }
}