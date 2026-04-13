import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class ActiveMembersListPage {
    public static void show() {
        Main.window.getContentPane().removeAll();

        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        // --- Title ---
        JLabel title = new JLabel("Active Members List");
        title.setFont(new Font("Arial", Font.PLAIN, 16));
        title.setForeground(Color.WHITE);
        panel.add(title);

        // --- Table Setup ---
        String[] columns = {
            "Name", "Mobile No.", "Email Address",
            "Type of Membership", "W/Trainer",
            "Status of Membership", "Recent Entry", "Total Cost"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        // Load members from DB
        loadMembers(model);

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
        header.setBackground(new Color(100, 100, 100));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Center-align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Alternating row colors
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

        // --- Total Profit ---
        JLabel totalProfitLabel = new JLabel("Total Profit:");
        totalProfitLabel.setFont(new Font("Arial", Font.PLAIN, 13));
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

        // --- Inactive Members List (bottom label) ---
        JLabel inactiveLabel = new JLabel("Inactive Members List");
        inactiveLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        inactiveLabel.setForeground(Color.WHITE);
        panel.add(inactiveLabel);

        // --- Responsive Layout ---
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                title.setBounds(40, 15, 300, 25);
                scrollPane.setBounds(40, 50, w - 80, h - 175);

                int tableBottom = 50 + (h - 175);
                totalProfitLabel.setBounds(w - 200, tableBottom + 5, 160, 20);
                backBtn.setBounds(30, tableBottom + 30, 120, 40);
                inactiveLabel.setBounds(40, h - 25, 250, 20);
            }
        });

        Main.window.add(panel);
        Main.window.revalidate();
        Main.window.repaint();
    }

    private static void loadMembers(DefaultTableModel model) {
        String sql = "SELECT first, last, phone, email, type, trainer FROM members";
        try {
            java.lang.reflect.Field f = MemberDB.class.getDeclaredField("connection");
            f.setAccessible(true);
            Connection conn = (Connection) f.get(null);

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String name     = rs.getString("first") + " " + rs.getString("last");
                    String phone    = rs.getString("phone");
                    String email    = rs.getString("email");
                    String type     = rs.getString("type");
                    String trainer  = rs.getBoolean("trainer") ? "YES" : "NO";
                    String status   = "ACTIVE";
                    String entry    = "-";   // placeholder
                    String cost     = "-";   // placeholder
                    model.addRow(new Object[]{name, phone, email, type, trainer, status, entry, cost});
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}