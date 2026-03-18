import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DisasterReliefSystem extends JFrame {
    // UI Colors
    private final Color pastelPink = new Color(255,182,193);
    private final Color pastelYellow = new Color(214, 205, 124);
    private final Color pastelPurple = new Color(152, 123, 237);
    private final Color pastelWhite = new Color(152, 123, 237);

    // BST
    HouseholdBST bst = new HouseholdBST();
    Household root = null;

    // User management
    UserManager userManager = new UserManager();

    // Panels
    JPanel loginPanel, mainPanel;

    public DisasterReliefSystem(){
        setTitle("Disaster Relief System");
        setSize(600,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showLogin();
    }

    // ----- Login & Register -----
    private void showLogin(){
        getContentPane().removeAll();
        loginPanel = new JPanel();
        loginPanel.setBackground(pastelPink);
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Disaster Relief Login/Register");
        title.setFont(new Font("Arial",Font.BOLD,20));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2;
        loginPanel.add(title,gbc);

        gbc.gridwidth=1;
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(15);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        gbc.gridx=0; gbc.gridy=1; loginPanel.add(userLabel,gbc);
        gbc.gridx=1; loginPanel.add(userField,gbc);
        gbc.gridx=0; gbc.gridy=2; loginPanel.add(passLabel,gbc);
        gbc.gridx=1; loginPanel.add(passField,gbc);

        JButton loginBtn = new JButton("Login"); loginBtn.setBackground(pastelYellow);
        JButton registerBtn = new JButton("Register"); registerBtn.setBackground(pastelPurple);

        gbc.gridx=0; gbc.gridy=3; loginPanel.add(loginBtn,gbc);
        gbc.gridx=1; loginPanel.add(registerBtn,gbc);

        loginBtn.addActionListener(e->{
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            if(userManager.login(username,password)){
                showMainUI();
            } else {
                JOptionPane.showMessageDialog(null,"Invalid username/password");
            }
        });

        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if(userManager.register(username,password)){
                JOptionPane.showMessageDialog(null,"Registered successfully!");
            } else {
                JOptionPane.showMessageDialog(null,
                        "Registration failed!\nUsername: ≥4 chars, letters/numbers/_ only\n" +
                                "Password: ≥6 chars, at least one letter and one number");
            }
        });

        setContentPane(loginPanel);
        revalidate(); repaint();
    }

    // ----- Main UI -----
    private void showMainUI(){
        getContentPane().removeAll();
        mainPanel = new JPanel();
        mainPanel.setBackground(pastelPink);
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);

        JLabel title = new JLabel("Disaster Relief - User: "+userManager.getCurrentUser().getUsername());
        title.setFont(new Font("Arial",Font.BOLD,18));
        gbc.gridx=0; gbc.gridy=0; gbc.gridwidth=2; mainPanel.add(title,gbc);

        gbc.gridwidth=1;
        JButton addHousehold = new JButton("Add Household"); addHousehold.setBackground(pastelYellow);
        JButton addRequest = new JButton("Add Request"); addRequest.setBackground(pastelPurple);
        JButton viewHousehold = new JButton("View Households"); viewHousehold.setBackground(pastelYellow);
        JButton processRelief = new JButton("Process Relief"); processRelief.setBackground(pastelPurple);
        JButton search = new JButton("Search Household"); search.setBackground(pastelYellow);
        JButton logout = new JButton("Logout"); logout.setBackground(pastelPurple);

        gbc.gridx=0; gbc.gridy=1; mainPanel.add(addHousehold,gbc);
        gbc.gridx=1; mainPanel.add(addRequest,gbc);
        gbc.gridx=0; gbc.gridy=2; mainPanel.add(viewHousehold,gbc);
        gbc.gridx=1; mainPanel.add(processRelief,gbc);
        gbc.gridx=0; gbc.gridy=3; mainPanel.add(search,gbc);
        gbc.gridx=1; mainPanel.add(logout,gbc);

        addHousehold.addActionListener(e->addHousehold());
        addRequest.addActionListener(e->addRequest());
        viewHousehold.addActionListener(e->showHouseholds());
        processRelief.addActionListener(e->processRelief());
        search.addActionListener(e->searchHousehold());
        logout.addActionListener(e->{ userManager.logout(); showLogin(); });

        setContentPane(mainPanel); revalidate(); repaint();
    }

    // ----- Household Operations -----
    private void addHousehold(){
        try{
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
            String name = JOptionPane.showInputDialog("Enter Household Name:");
            if(bst.search(root,id)!=null){ JOptionPane.showMessageDialog(null,"ID exists!"); return; }
            root = bst.insert(root,id,name);
            JOptionPane.showMessageDialog(null,"Household added!");
        } catch(Exception e){ JOptionPane.showMessageDialog(null,"Invalid input"); }
    }

    private void addRequest(){
        try{
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
            Household h = bst.search(root,id);
            if(h==null){ JOptionPane.showMessageDialog(null,"Household not found"); return; }
            String name = JOptionPane.showInputDialog("Enter Request Name:");
            int urgency = Integer.parseInt(JOptionPane.showInputDialog("Urgency 1-10:"));
            h.requests.add(new ReliefRequest(name,urgency));
            JOptionPane.showMessageDialog(null,"Request added!");
        } catch(Exception e){ JOptionPane.showMessageDialog(null,"Invalid input"); }
    }

    private void processRelief(){
        try{
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
            Household h = bst.search(root,id);
            if(h==null){ JOptionPane.showMessageDialog(null,"Household not found"); return; }
            if(h.requests.isEmpty()){ JOptionPane.showMessageDialog(null,"No requests"); return; }
            PriorityQueue<ReliefRequest> pq = new PriorityQueue<>((a,b)->b.urgency-a.urgency);
            pq.addAll(h.requests); ReliefRequest r=pq.poll();
            h.requests.remove(r); h.processed.add(r);
            JOptionPane.showMessageDialog(null,"Processed: "+r.requestName+" (Urgency:"+r.urgency+")");
        } catch(Exception e){ JOptionPane.showMessageDialog(null,"Invalid input"); }
    }

    private void searchHousehold(){
        try{
            int id=Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
            Household h = bst.search(root,id);
            if(h==null){ JOptionPane.showMessageDialog(null,"Household not found"); return; }
            showRequests(h);
        } catch(Exception e){ JOptionPane.showMessageDialog(null,"Invalid input"); }
    }

    private void showHouseholds(){
        JFrame frame = new JFrame("Household List");
        frame.setSize(500,400); frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel(); panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        addHouseholdRows(root,panel);
        frame.add(new JScrollPane(panel)); frame.setVisible(true);
    }

    private void addHouseholdRows(Household node,JPanel panel){
        if(node==null) return;
        addHouseholdRows(node.left,panel);
        JPanel row = new JPanel(new BorderLayout());
        JLabel label = new JLabel("ID:"+node.id+" | Name:"+node.name);
        JButton viewBtn = new JButton("View Requests"); viewBtn.setBackground(pastelPurple);
        viewBtn.addActionListener(e->showRequests(node));
        row.add(label,BorderLayout.CENTER); row.add(viewBtn,BorderLayout.EAST);
        panel.add(row);
        addHouseholdRows(node.right,panel);
    }

    private void showRequests(Household h){
        JFrame frame = new JFrame("Requests for "+h.name);
        frame.setSize(400,400); frame.setLocationRelativeTo(null);
        JTextArea area = new JTextArea(); area.setEditable(false);
        area.append("--- Pending Requests ---\n");
        for(ReliefRequest r:h.requests) area.append(r.requestName+" (Urgency:"+r.urgency+")\n");
        area.append("\n--- Processed Requests ---\n");
        for(ReliefRequest r:h.processed) area.append(r.requestName+" (Urgency:"+r.urgency+")\n");
        frame.add(new JScrollPane(area)); frame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->new DisasterReliefSystem().setVisible(true));
    }
}