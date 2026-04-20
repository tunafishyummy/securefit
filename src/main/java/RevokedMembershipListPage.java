import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;

public class RevokedMembershipListPage {

    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        //same shared header so this list feels like the rest of the admin pages
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel title = new JLabel("Revoked Members", SwingConstants.CENTER);
        title.setFont(new Font("Bebas Neue", Font.PLAIN, 34));
        title.setForeground(Color.WHITE);
        topBar.add(title);

        //the table is the page, so it gets built right away
        String[] columns = {
            "Name", "Mobile No.", "Email Address",
            "Type of Membership", "W/Trainer",
            "Status of Membership", "Reason"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        loadRevokedMembers(model);

        JTable table = new JTable(model);
        table.setBackground(new Color(180, 180, 180));
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(55);
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        //the header needs strong contrast because this list gets dense
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.CENTER);

        //this renderer handles striping and keeps text centered
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

        //back takes us to the admin menu
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 28));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> AdminMenuPage.show());
        panel.add(backBtn);

        //resizing is mostly about leaving the table as much space as possible
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);
                title.setBounds(0, 16, w, 40);

                scrollPane.setBounds(20, 95, w - 40, h - 190);

                int tableBottom = 95 + (h - 190);
                backBtn.setBounds(30, tableBottom + 20, 120, 45);
            }
        });

        Main.setPage(panel);
    }

    private static void loadRevokedMembers(DefaultTableModel model) {
        String sql = "SELECT first, last, phone, email, type, trainer, reason FROM members WHERE status = 'REVOKED'";
        try {
            Connection conn = MemberDB.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String name       = rs.getString("first") + " " + rs.getString("last");
                    String phone      = rs.getString("phone");
                    String email      = rs.getString("email");
                    String type       = rs.getString("type");
                    String trainerStr = rs.getBoolean("trainer") ? "YES" : "NO";
                    String reason     = rs.getString("reason");
                    model.addRow(new Object[]{
                        name, phone, email, type, trainerStr,
                        "REVOKED", reason
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
