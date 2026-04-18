import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.table.DefaultTableModel;

public class loadInactiveMembers {
    public static void LoadInactiveMembers(DefaultTableModel model, int[] totalProfit) {
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

    public static String calculateExpiry(String type, String dateRegistered) {
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
