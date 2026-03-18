import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DisasterReliefSystem extends JFrame {

    Map<Integer, Household> households = new HashMap<>();
    JTextArea output;

    public DisasterReliefSystem() {
        setTitle("Disaster Relief System Prototype");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5,1,10,10));

        JButton addHousehold = new JButton("Add Household");
        JButton viewHousehold = new JButton("View Households");
        JButton addRequest = new JButton("Add Request");
        JButton processRelief = new JButton("Process Relief");
        JButton search = new JButton("Search Household");

        panel.add(addHousehold);
        panel.add(viewHousehold);
        panel.add(addRequest);
        panel.add(processRelief);
        panel.add(search);

        add(panel, BorderLayout.WEST);

        output = new JTextArea();
        add(new JScrollPane(output), BorderLayout.CENTER);

        // ADD HOUSEHOLD
        addHousehold.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                String name = JOptionPane.showInputDialog("Enter Name:");

                if (households.containsKey(id)) {
                    JOptionPane.showMessageDialog(null, "ID already exists!");
                    return;
                }

                households.put(id, new Household(id, name));

                int option = JOptionPane.showOptionDialog(null,
                        "Household inputted successfully",
                        "Success",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"View List", "Okay"},
                        "Okay");

                if (option == 0) showHouseholds();

            } catch (Exception ex) {
                output.append("Invalid input\n");
            }
        });

        // VIEW
        viewHousehold.addActionListener(e -> showHouseholds());

        // ADD REQUEST
        addRequest.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = households.get(id);

                if (h == null) {
                    output.append("Household not found\n");
                    return;
                }

                String reqName = JOptionPane.showInputDialog("Enter Request Name:");
                int urgency = Integer.parseInt(JOptionPane.showInputDialog("Urgency (1-10):"));

                h.requests.add(new ReliefRequest(reqName, urgency));

                JOptionPane.showMessageDialog(null, "Request successfully added");

            } catch (Exception ex) {
                output.append("Invalid input\n");
            }
        });

        // PROCESS
        processRelief.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = households.get(id);

                if (h == null) {
                    output.append("Household not found\n");
                    return;
                }

                if (h.requests.isEmpty()) {
                    output.append("No requests available\n");
                    return;
                }

                h.requests.sort((a,b)-> b.urgency - a.urgency);

                ReliefRequest r = h.requests.remove(0);
                h.processed.add(r);

                output.append("Processing as per urgency level...\n");
                output.append("Processed: " + r.requestName + " (Urgency: " + r.urgency + ")\n");

            } catch (Exception ex) {
                output.append("Invalid input\n");
            }
        });

        // SEARCH
        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = households.get(id);

                if (h == null) {
                    output.append("Not found\n");
                    return;
                }

                output.setText("--- Household Info ---\n");
                output.append("ID: " + h.id + " Name: " + h.name + "\n\n");

                output.append("Requests:\n");
                h.requests.sort((a,b)-> b.urgency - a.urgency);
                for (ReliefRequest r : h.requests) {
                    output.append(r.requestName + " (Urgency: " + r.urgency + ")\n");
                }

                output.append("\nProcessed:\n");
                for (ReliefRequest r : h.processed) {
                    output.append(r.requestName + " (Urgency: " + r.urgency + ")\n");
                }

            } catch (Exception ex) {
                output.append("Invalid input\n");
            }
        });
    }

    private void showHouseholds() {
        output.setText("--- Household List ---\n");
        for (Household h : households.values()) {
            output.append("ID: " + h.id + " | Name: " + h.name + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DisasterReliefSystem().setVisible(true));
    }
}