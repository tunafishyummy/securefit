import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class InactiveMembersListPage {

    public static void show() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(30, 30, 30));

        //same shared header so this page matches the other admin views
        JPanel topBar = new JPanel(null);
        topBar.setBackground(Color.BLACK);
        panel.add(topBar);

        ImagePanel logo = new ImagePanel("images/SmallLogo.png");
        logo.setOnClick(() -> HomePage.show());
        topBar.add(logo);

        JLabel title = new JLabel("Inactive Members List", SwingConstants.CENTER);
        title.setFont(FontLoader.bebasNeue(Font.PLAIN, 34));
        title.setForeground(Color.WHITE);
        topBar.add(title);

        //the table is the main point of the page, so it gets set up first
        String[] columns = {
            "Name", "Mobile No.", "Email Address",
            "Type of Membership", "W/Trainer",
            "Status of Membership", "Total Cost"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        int[] totalProfit = {0};
        loadInactiveMembers.LoadInactiveMembers(model, totalProfit);

        JTable table = new JTable(model);
        table.setBackground(new Color(180, 180, 180));
        table.setForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(75);
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        //high contrast helps a lot once the columns start filling up
        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.CENTER);

        //this renderer handles striping, centering, and the multiline status cell
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {
                //swing table cells play nicely with a little html for line breaks
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

        //this keeps the total visible near the bottom of the table
        JLabel totalProfitLabel = new JLabel("Total Profit: ₱" + totalProfit[0]);
        totalProfitLabel.setFont(new Font("Arial", Font.BOLD, 13));
        totalProfitLabel.setForeground(Color.WHITE);
        totalProfitLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(totalProfitLabel);

        //back returns to the admin menu
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.BOLD, 28));
        backBtn.setForeground(Color.WHITE);
        backBtn.setContentAreaFilled(false);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> AdminMenuPage.show());
        panel.add(backBtn);


        //the layout mostly just protects table space during resize
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = panel.getWidth();
                int h = panel.getHeight();

                topBar.setBounds(0, 0, w, 80);
                logo.setBounds(10, 0, 200, 79);
                title.setBounds(0, 16, w, 40);

                scrollPane.setBounds(20, 95, w - 40, h - 205);

                int tableBottom = 95 + (h - 205);
                totalProfitLabel.setBounds(w - 220, tableBottom + 5, 180, 20);
                backBtn.setBounds(30, tableBottom + 20, 120, 45);
            }
        });

        Main.setPage(panel);
    }
}

