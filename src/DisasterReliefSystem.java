import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DisasterReliefSystem extends JFrame {

    // ✅ USING BST INSTEAD OF HASHMAP
    HouseholdBST bst = new HouseholdBST();
    Household root = null;

    public DisasterReliefSystem() {
        setTitle("Disaster Relief System");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton addHousehold = new JButton("Add Household");
        JButton addRequest = new JButton("Add Request");
        JButton viewHousehold = new JButton("View Households");
        JButton processRelief = new JButton("Process Relief");
        JButton search = new JButton("Search Household");

        gbc.gridx = 0; gbc.gridy = 0;
        add(addHousehold, gbc);

        gbc.gridx = 1;
        add(addRequest, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        add(viewHousehold, gbc);

        gbc.gridwidth = 1; gbc.gridy = 2; gbc.gridx = 0;
        add(processRelief, gbc);

        gbc.gridx = 1;
        add(search, gbc);

        // ✅ ADD HOUSEHOLD (BST INSERT)
        addHousehold.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                String name = JOptionPane.showInputDialog("Enter Name:");

                // check if already exists
                if (bst.search(root, id) != null) {
                    JOptionPane.showMessageDialog(null, "ID already exists!");
                    return;
                }

                root = bst.insert(root, id, name);

                JOptionPane.showMessageDialog(null, "Household added successfully!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });

        // ✅ VIEW HOUSEHOLDS (BST INORDER)
        viewHousehold.addActionListener(e -> {
            JFrame frame = new JFrame("Household List");
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            JTextArea area = new JTextArea();
            area.setEditable(false);

            bst.inorder(root, area);

            frame.add(new JScrollPane(area));
            frame.setVisible(true);
        });

        // ✅ ADD REQUEST
        addRequest.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = bst.search(root, id);

                if (h == null) {
                    JOptionPane.showMessageDialog(null, "Household not found");
                    return;
                }

                String reqName = JOptionPane.showInputDialog("Enter Request Name:");
                int urgency = Integer.parseInt(JOptionPane.showInputDialog("Urgency (1-10):"));

                h.requests.add(new ReliefRequest(reqName, urgency));

                JOptionPane.showMessageDialog(null, "Request added!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });

        // ✅ PROCESS RELIEF (PRIORITY QUEUE - Week 13)
        processRelief.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = bst.search(root, id);

                if (h == null) {
                    JOptionPane.showMessageDialog(null, "Household not found");
                    return;
                }

                if (h.requests.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No requests available");
                    return;
                }

                // ✅ PRIORITY QUEUE (Highest urgency first)
                PriorityQueue<ReliefRequest> pq =
                        new PriorityQueue<>((a, b) -> b.urgency - a.urgency);

                pq.addAll(h.requests);

                ReliefRequest r = pq.poll(); // highest priority

                h.requests.remove(r);
                h.processed.add(r);

                JOptionPane.showMessageDialog(null,
                        "Processed: " + r.requestName +
                                " (Urgency: " + r.urgency + ")");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });

        // ✅ SEARCH HOUSEHOLD (BST SEARCH)
        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = bst.search(root, id);

                if (h == null) {
                    JOptionPane.showMessageDialog(null, "Household not found");
                    return;
                }

                showRequests(h);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });
    }

    // ✅ DISPLAY REQUESTS
    private void showRequests(Household h) {
        JFrame frame = new JFrame("Requests for " + h.name);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        area.append("--- Pending Requests ---\n");
        for (ReliefRequest r : h.requests) {
            area.append(r.requestName + " (Urgency: " + r.urgency + ")\n");
        }

        area.append("\n--- Processed Requests ---\n");
        for (ReliefRequest r : h.processed) {
            area.append(r.requestName + " (Urgency: " + r.urgency + ")\n");
        }

        frame.add(new JScrollPane(area));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisasterReliefSystem().setVisible(true));
    }
}