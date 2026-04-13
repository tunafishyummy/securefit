import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;

public class MemberDB {
    private static final String DB_URL = "jdbc:sqlite:securefit.db";
    private static final Connection connection;

    static {
        try { 
            connection = DriverManager.getConnection(DB_URL);
            initDB();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try { connection.close(); } catch (SQLException ignored) {}
            }));
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
        
    }

    private static void initDB() {
    String sql =
      "CREATE TABLE IF NOT EXISTS members(" +
      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
      "first TEXT NOT NULL," +
      "last  TEXT NOT NULL," +
      "email TEXT UNIQUE NOT NULL," +
      "pass  TEXT NOT NULL," +
      "phone TEXT NOT NULL," +
      "type  TEXT NOT NULL," +
      "trainer BOOLEAN NOT NULL," +
      "status TEXT NOT NULL DEFAULT 'ACTIVE'," +
      "date_registered TEXT NOT NULL DEFAULT (datetime('now','localtime')))";
    try (Statement s = connection.createStatement()) {
        s.execute(sql);
        try {
            s.execute("ALTER TABLE members ADD COLUMN date_registered TEXT NOT NULL DEFAULT (datetime('now','localtime'))");
        } catch (SQLException ignored) {}
        try {
            s.execute("ALTER TABLE members ADD COLUMN status TEXT NOT NULL DEFAULT 'ACTIVE'");
        } catch (SQLException ignored) {}
        try {
            s.execute("ALTER TABLE members ADD COLUMN reason TEXT DEFAULT ''");
        } catch (SQLException ignored) {}
    } catch (SQLException ex) {
        System.err.println("Database initialization error: " + ex.getMessage());
    }
}

    public static boolean emailExists(String email) {
        String sql = "SELECT 1 FROM members WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            System.err.println("Error checking email: " + ex.getMessage());
            return false; 
        }
    }

    public static void save(String first, String last, String email, String password, String phone, String type, boolean trainer) {
    String sql = "INSERT INTO members(first,last,email,pass,phone,type,trainer,status,date_registered) VALUES (?,?,?,?,?,?,?,'ACTIVE',datetime('now','localtime'))";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, first);
        ps.setString(2, last);
        ps.setString(3, email);
        ps.setString(4, BCrypt.hashpw(password, BCrypt.gensalt()));
        ps.setString(5, phone);
        ps.setString(6, type);
        ps.setBoolean(7, trainer);
        ps.executeUpdate();
    } catch (SQLException ex) {
        System.err.println("Error saving member: " + ex.getMessage());
    }
}
public static String[] getMemberData(String email) {
    String[] data = new String[4]; 
    String sql = "SELECT first, last, email, phone FROM members WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data[0] = rs.getString("first");
                data[1] = rs.getString("last");
                data[2] = rs.getString("email");
                data[3] = rs.getString("phone");
            }
        }
    } catch (SQLException ex) {
        System.err.println("Error fetching member data: " + ex.getMessage());
    }
    return data;
}

public static void updateMember(String oldEmail, String first, String last, String newEmail, String phone) {
    String sql = "UPDATE members SET first = ?, last = ?, email = ?, phone = ? WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, first);
        ps.setString(2, last);
        ps.setString(3, newEmail);
        ps.setString(4, phone);
        ps.setString(5, oldEmail);
        ps.executeUpdate();
    } catch (SQLException ex) {
        System.err.println("Error updating member: " + ex.getMessage());
    }
}
    public static boolean checkCredentials(String email, String password) {
        String sql = "SELECT pass FROM members WHERE email=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return BCrypt.checkpw(password, rs.getString("pass"));
            }
        } catch (SQLException ex) {
            System.err.println("Error checking credentials: " + ex.getMessage());
        }
        return false;
    }

    public static int calculateCost(String type, boolean trainer) {
        switch (type.toUpperCase()) {
            case "ONE TIME SESSION": return trainer ? 300  : 150;
            case "WEEKLY":          return trainer ? 1250 : 750;
            case "MONTHLY":         return trainer ? 3000 : 1500;
            case "YEARLY":          return trainer ? 15000 : 7500;
            default:                return 0;
        }
    }

    //Status updates for membership management
    public static void setStatus(String email, String status) {
    String sql = "UPDATE members SET status = ? WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setString(2, email);
        ps.executeUpdate();
    } catch (SQLException ex) {
        System.err.println("Error updating status: " + ex.getMessage());
    }
}
    public static Connection getConnection() { return connection; }

    private MemberDB() {}
}

