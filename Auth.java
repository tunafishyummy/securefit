public class Auth {
    private static String currentUser;

    public static void login(String email) {
        currentUser = email;
    }
    public static void logout() {
        currentUser = null;
    }
    public static String getCurrentUser() {
        return currentUser;
    }
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}