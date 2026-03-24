import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

// ==================== MEMBER CLASS ====================
class Member {
    private String memberId;
    private String name;
    private String email;
    private String password;
    private String membershipPlan;
    private boolean isActive;
    private String qrCode;
    
    public Member(String memberId, String name, String email, String password, String membershipPlan) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.membershipPlan = membershipPlan;
        this.isActive = true;
        this.qrCode = "GYM-" + memberId + "-" + System.currentTimeMillis();
    }
    
    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMembershipPlan() { return membershipPlan; }
    public boolean isActive() { return isActive; }
    public String getQrCode() { return qrCode; }
    
    // Setters
    public void setActive(boolean active) { isActive = active; }
    public void setMembershipPlan(String plan) { membershipPlan = plan; }
    
    public boolean checkPassword(String input) { return password.equals(input); }
}

// ==================== MAIN GUI APPLICATION ====================
public class GymManagementGUI extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ArrayList<Member> members;
    private Member currentUser;
    private static int nextMemberId = 1000;
    
    // Panel references for navigation
    private JPanel homePanel, mainMenuPanel, signupPanel, loginPanel;
    private JPanel loggedInMenuPanel, personalInfoPanel, qrCodePanel;
    private JPanel adminLoginPanel, adminPanel, activeMembersPanel;
    private JPanel changePlanPanel;
    
    public GymManagementGUI() {
        members = new ArrayList<>();
        
        // Set up main frame
        setTitle("Gym Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        
        // Create card layout for screen switching
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create all screens
        createHomePanel();
        createMainMenuPanel();
        createSignupPanel();
        createLoginPanel();
        //createLoggedInMenuPanel();
        //createPersonalInfoPanel();
        //createQRCodePanel();
        //createAdminLoginPanel();
        //createAdminPanel();
        //createActiveMembersPanel();
        //createChangePlanPanel();
        
        // Add panels to card layout
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(mainMenuPanel, "MAINMENU");
        mainPanel.add(signupPanel, "SIGNUP");
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(loggedInMenuPanel, "LOGGEDIN");
        mainPanel.add(personalInfoPanel, "PERSONALINFO");
        mainPanel.add(qrCodePanel, "QRCODE");
        mainPanel.add(adminLoginPanel, "ADMINLOGIN");
        mainPanel.add(adminPanel, "ADMINPANEL");
        mainPanel.add(activeMembersPanel, "ACTIVEMEMBERS");
        mainPanel.add(changePlanPanel, "CHANGEPLAN");
        
        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }
    
    // ==================== HELPER METHODS ====================
    
    private String generateMemberId() {
        return "M" + (nextMemberId++);
    }
    
    private int getIntInput() {
        try {
            return Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number:"));
        } catch (Exception e) {
            return -1;
        }
    }
    
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    // ==================== PANEL 1: HOME ====================
    private void createHomePanel() {
        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.setBackground(Color.WHITE);
        
        // Logo/Title
        JLabel title = new JLabel("GYM MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 100, 200));
        title.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 50, 80));
        
        JButton mainMenuBtn = createStyledButton("Main Menu", new Color(0, 150, 200));
        JButton adminBtn = createStyledButton("Admin Menu", new Color(100, 100, 100));
        JButton exitBtn = createStyledButton("Exit", new Color(200, 50, 50));
        
        mainMenuBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAINMENU"));
        adminBtn.addActionListener(e -> cardLayout.show(mainPanel, "ADMINLOGIN"));
        exitBtn.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(mainMenuBtn);
        buttonPanel.add(adminBtn);
        buttonPanel.add(exitBtn);
        
        homePanel.add(title, BorderLayout.NORTH);
        homePanel.add(buttonPanel, BorderLayout.CENTER);
    }
    
    // ==================== PANEL 2: MAIN MENU ====================
    private void createMainMenuPanel() {
        mainMenuPanel = new JPanel(new BorderLayout());
        mainMenuPanel.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("MAIN MENU", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 100, 200));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 15, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 40, 100));
        
        JButton loginBtn = createStyledButton("Login", new Color(0, 150, 200));
        JButton signupBtn = createStyledButton("Sign Up", new Color(0, 180, 100));
        JButton backBtn = createStyledButton("Back", new Color(100, 100, 100));
        
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        signupBtn.addActionListener(e -> cardLayout.show(mainPanel, "SIGNUP"));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        buttonPanel.add(backBtn);
        
        mainMenuPanel.add(title, BorderLayout.NORTH);
        mainMenuPanel.add(buttonPanel, BorderLayout.CENTER);
    }
    
    // ==================== PANEL 3: SIGN UP ====================
    private void createSignupPanel() {
        signupPanel = new JPanel(new BorderLayout());
        signupPanel.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("MEMBER REGISTRATION", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 100, 200));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Form fields
        formPanel.add(new JLabel("Full Name:"));
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField);
        
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Password:"));
        JPasswordField passField = new JPasswordField(20);
        formPanel.add(passField);
        
        formPanel.add(new JLabel("Confirm Password:"));
        JPasswordField confirmField = new JPasswordField(20);
        formPanel.add(confirmField);
        
        formPanel.add(new JLabel("Membership Plan:"));
        String[] plans = {"Basic ($29/month)", "Premium ($49/month)", "Elite ($79/month)"};
        JComboBox<String> planBox = new JComboBox<>(plans);
        formPanel.add(planBox);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        
        JButton registerBtn = createStyledButton("Register", new Color(0, 180, 100));
        JButton backBtn = createStyledButton("Back", new Color(100, 100, 100));
        
        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            String confirm = new String(confirmField.getPassword());
            String plan = ((String) planBox.getSelectedItem()).split(" ")[0];
            
            // Validation
            if(name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }
            
            if(!password.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords don't match!");
                return;
            }
            
            // Check if email already exists
            for(Member m : members) {
                if(m.getEmail().equals(email)) {
                    JOptionPane.showMessageDialog(this, "Email already registered!");
                    return;
                }
            }
            
            // Create new member
            String memberId = generateMemberId();
            Member newMember = new Member(memberId, name, email, password, plan);
            members.add(newMember);
            
            JOptionPane.showMessageDialog(this, 
                "Registration Successful!\nMember ID: " + memberId + 
                "\nQR Code: " + newMember.getQrCode());
            
            cardLayout.show(mainPanel, "MAINMENU");
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAINMENU"));
        
        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);
        
        signupPanel.add(title, BorderLayout.NORTH);
        signupPanel.add(formPanel, BorderLayout.CENTER);
        signupPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // ==================== PANEL 4: LOGIN ====================
    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(Color.WHITE);
        
        JLabel title = new JLabel("MEMBER LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(0, 100, 200));
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));
        
        formPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField);
        
        formPanel.add(new JLabel("Password:"));
        JPasswordField passField = new JPasswordField(20);
        formPanel.add(passField);
        
        formPanel.add(new JLabel(""));
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        
        JButton loginBtn = createStyledButton("Login", new Color(0, 150, 200));
        JButton backBtn = createStyledButton("Back", new Color(100, 100, 100));
        
        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passField.getPassword());
            
            for(Member m : members) {
                if(m.getEmail().equals(email) && m.checkPassword(password)) {
                    if(m.isActive()) {
                        currentUser = m;
                        JOptionPane.showMessageDialog(this, 
                            "Welcome, " + m.getName() + "!");
                        cardLayout.show(mainPanel, "LOGGEDIN");
                        return;
                    } else {
                        JOptionPane.showMessageDialog(this, 
                            "Account is inactive. Contact admin.");
                        return;
                    }
                }
            }
            
            JOptionPane.showMessageDialog(this, "Invalid email or password!");
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "MAINMENU"));
        
        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);
        formPanel.add(buttonPanel);
        
        loginPanel.add(title, BorderLayout.NORTH);
        loginPanel.add(formPanel, BorderLayout.CENTER);
    }
    
    // ==================== HELPER: STYLED BUTTON ====================
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    // ==================== MAIN ====================
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new GymManagementGUI().setVisible(true);
        });
    }
}