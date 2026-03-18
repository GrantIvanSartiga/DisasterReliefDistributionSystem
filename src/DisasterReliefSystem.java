import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class DisasterReliefSystem extends JFrame {

    HouseholdBST bst = new HouseholdBST();
    Queue<ReliefRequest> normalQueue = new LinkedList<>();
    PriorityQueue<ReliefRequest> priorityQueue = new PriorityQueue<>((a, b) -> b.urgency - a.urgency);

    JTextArea outputArea;

    public DisasterReliefSystem() {
        setTitle("Disaster Relief System");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));

        JButton addHouseholdBtn = new JButton("Add Household");
        JButton viewBtn = new JButton("View Households");
        JButton addRequestBtn = new JButton("Add Request");
        JButton processBtn = new JButton("Process Relief");
        JButton searchBtn = new JButton("Search Household");

        panel.add(addHouseholdBtn);
        panel.add(viewBtn);
        panel.add(addRequestBtn);
        panel.add(processBtn);
        panel.add(searchBtn);

        add(panel, BorderLayout.WEST);

        outputArea = new JTextArea();
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // BUTTON ACTIONS

        addHouseholdBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID:"));
                String name = JOptionPane.showInputDialog("Enter Name:");
                bst.root = bst.insert(bst.root, id, name);
                outputArea.append("Household added.\n");
            } catch (Exception ex) {
                outputArea.append("Invalid input.\n");
            }
        });

        viewBtn.addActionListener(e -> {
            outputArea.setText("--- Household List ---\n");
            bst.inorder(bst.root, outputArea);
        });

        addRequestBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Household ID:"));
                int urgency = Integer.parseInt(JOptionPane.showInputDialog("Urgency (1-10):"));

                ReliefRequest req = new ReliefRequest(id, urgency);
                if (urgency >= 7) {
                    priorityQueue.add(req);
                    outputArea.append("Added to PRIORITY queue.\n");
                } else {
                    normalQueue.add(req);
                    outputArea.append("Added to NORMAL queue.\n");
                }
            } catch (Exception ex) {
                outputArea.append("Invalid input.\n");
            }
        });

        processBtn.addActionListener(e -> {
            ReliefRequest r;
            if (!priorityQueue.isEmpty()) {
                r = priorityQueue.poll();
                outputArea.append("Processing PRIORITY request...\n");
            } else if (!normalQueue.isEmpty()) {
                r = normalQueue.poll();
                outputArea.append("Processing NORMAL request...\n");
            } else {
                outputArea.append("No requests available.\n");
                return;
            }

            Household h = bst.search(bst.root, r.householdId);
            if (h != null) {
                outputArea.append("Relief sent to: " + h.name + " (Urgency: " + r.urgency + ")\n");
            } else {
                outputArea.append("Household not found.\n");
            }
        });

        searchBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID to search:"));
                Household h = bst.search(bst.root, id);
                if (h != null) {
                    outputArea.append("Found: " + h.name + "\n");
                } else {
                    outputArea.append("Not found.\n");
                }
            } catch (Exception ex) {
                outputArea.append("Invalid input.\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisasterReliefSystem().setVisible(true));
    }
}
