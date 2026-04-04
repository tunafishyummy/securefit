import java.sql.*;

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
          "trainer BOOLEAN NOT NULL)";
        try (Statement s = connection.createStatement()) {
            s.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            ex.printStackTrace(); 
            return false; 
        }
    }

    public static void save(String first, String last, String email, String password, String phone, String type, boolean trainer) {
        String sql = "INSERT INTO members(first,last,email,pass,phone,type,trainer) VALUES (?,?,?,?,?,?,?)";
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
            ex.printStackTrace(); 
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
            ex.printStackTrace();  
        }
        return false;
    }
    private MemberDB() {}
}