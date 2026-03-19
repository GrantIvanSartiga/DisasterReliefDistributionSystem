import javax.swing.JTextArea;

public class HouseholdBST {
    public Household insert(Household root, int id, String name) {
        if (root == null) return new Household(id, name);

        if (id < root.id)
            root.left = insert(root.left, id, name);
        else
            root.right = insert(root.right, id, name);

        return root;
    }

    public Household search(Household root, int id) {
        if (root == null || root.id == id) return root;

        if (id < root.id)
            return search(root.left, id);
        else
            return search(root.right, id);
    }

    public void inorder(Household root, JTextArea area) {
        if (root != null) {
            inorder(root.left, area);
            area.append("ID: " + root.id + " | Name: " + root.name + "\n");
            inorder(root.right, area);
        }
    }
}