import javax.swing.*;
import java.awt.*;
import java.util.*;

public class DisasterReliefSystem extends JFrame {

    Map<Integer, Household> households = new HashMap<>();

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


        gbc.gridx = 0;
        gbc.gridy = 0;
        add(addHousehold, gbc);

        gbc.gridx = 1;
        add(addRequest, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(viewHousehold, gbc);


        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        add(processRelief, gbc);

        gbc.gridx = 1;
        add(search, gbc);


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
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });


        viewHousehold.addActionListener(e -> showHouseholds());


        addRequest.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = households.get(id);

                if (h == null) {
                    JOptionPane.showMessageDialog(null, "Household not found");
                    return;
                }

                String reqName = JOptionPane.showInputDialog("Enter Request Name:");
                int urgency = Integer.parseInt(JOptionPane.showInputDialog("Urgency (1-10):"));

                h.requests.add(new ReliefRequest(reqName, urgency));
                JOptionPane.showMessageDialog(null, "Request successfully added");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });


        processRelief.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = households.get(id);

                if (h == null) {
                    JOptionPane.showMessageDialog(null, "Household not found");
                    return;
                }

                if (h.requests.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No requests available");
                    return;
                }

                h.requests.sort((a,b) -> b.urgency - a.urgency);
                ReliefRequest r = h.requests.remove(0);
                h.processed.add(r);

                JOptionPane.showMessageDialog(null,
                        "Processing as per urgency level...\nProcessed: " + r.requestName +
                                " (Urgency: " + r.urgency + ")");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input");
            }
        });


        search.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Household ID:"));
                Household h = households.get(id);

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


    private void showHouseholds() {
        JFrame frame = new JFrame("Household List");
        frame.setSize(500,400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Household h : households.values()) {
            JPanel row = new JPanel(new BorderLayout());

            JLabel label = new JLabel("ID: " + h.id + " | Name: " + h.name);
            JButton viewBtn = new JButton("View Requests");

            viewBtn.addActionListener(e -> showRequests(h));

            row.add(label, BorderLayout.CENTER);
            row.add(viewBtn, BorderLayout.EAST);

            panel.add(row);
        }

        frame.add(new JScrollPane(panel));
        frame.setVisible(true);
    }


    private void showRequests(Household h) {
        JFrame frame = new JFrame("Requests for " + h.name);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        area.append("--- Requests ---\n");
        h.requests.sort((a,b) -> b.urgency - a.urgency);
        for (ReliefRequest r : h.requests) {
            area.append(r.requestName + " (Urgency: " + r.urgency + ")\n");
        }

        area.append("\n--- Processed ---\n");
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