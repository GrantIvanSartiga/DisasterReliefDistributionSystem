import java.util.*;

public class DisasterReliefSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        HouseholdBST bst = new HouseholdBST();

        // Queue (FIFO)
        Queue<ReliefRequest> normalQueue = new LinkedList<>();

        // Priority Queue (Heap)
        PriorityQueue<ReliefRequest> priorityQueue =
                new PriorityQueue<>((a, b) -> b.urgency - a.urgency);

        int choice;

        do {
            System.out.println("\n===== DISASTER RELIEF SYSTEM =====");
            System.out.println("1. Add Household");
            System.out.println("2. View Households");
            System.out.println("3. Add Relief Request");
            System.out.println("4. Process Relief (Priority First)");
            System.out.println("5. Search Household");
            System.out.println("6. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();

            switch (choice) {

                // ================= ADD HOUSEHOLD =================
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    bst.root = bst.insert(bst.root, id, name);
                    System.out.println("Household added.");
                    break;

                // ================= VIEW HOUSEHOLDS =================
                case 2:
                    System.out.println("\n--- Household List ---");
                    bst.inorder(bst.root);
                    break;

                // ================= ADD REQUEST =================
                case 3:
                    System.out.print("Enter Household ID: ");
                    int hid = sc.nextInt();

                    System.out.print("Urgency (1-10): ");
                    int urgency = sc.nextInt();

                    ReliefRequest req = new ReliefRequest(hid, urgency);

                    if (urgency >= 7) {
                        priorityQueue.add(req);
                        System.out.println("Added to PRIORITY queue.");
                    } else {
                        normalQueue.add(req);
                        System.out.println("Added to NORMAL queue.");
                    }
                    break;

                // ================= PROCESS RELIEF =================
                case 4:
                    if (!priorityQueue.isEmpty()) {
                        ReliefRequest r = priorityQueue.poll();
                        System.out.println("Processing PRIORITY request...");
                        processRequest(r, bst);
                    } else if (!normalQueue.isEmpty()) {
                        ReliefRequest r = normalQueue.poll();
                        System.out.println("Processing NORMAL request...");
                        processRequest(r, bst);
                    } else {
                        System.out.println("No requests available.");
                    }
                    break;

                // ================= SEARCH =================
                case 5:
                    System.out.print("Enter ID to search: ");
                    int sid = sc.nextInt();

                    Household found = bst.search(bst.root, sid);

                    if (found != null) {
                        System.out.println("Found: " + found.name);
                    } else {
                        System.out.println("Not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 6);

        sc.close();
    }
    public static void processRequest(ReliefRequest r, HouseholdBST bst) {
        Household h = bst.search(bst.root, r.householdId);

        if (h != null) {
            System.out.println("Relief sent to: " + h.name +
                    " (Urgency: " + r.urgency + ")");
        } else {
            System.out.println("Household not found.");
        }
    }
}