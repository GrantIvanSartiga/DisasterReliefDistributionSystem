//import java.util.*;
//
//public class HouseholdBST {
//    Household root;
//
//    public Household insert(Household root, int id, String name) {
//        if (root == null) {
//            return new Household(id, name);
//        }
//        if (id < root.id) {
//            root.left = insert(root.left, id, name);
//        } else {
//            root.right = insert(root.right, id, name);
//        }
//        return root;
//    }
//
//    public Household search(Household root, int id) {
//        if (root == null || root.id == id) {
//            return root;
//        }
//        if (id < root.id) {
//            return search(root.left, id);
//        } else {
//            return search(root.right, id);
//        }
//    }
//
//    public void inorder(Household root) {
//        if (root != null) {
//            inorder(root.left);
//            System.out.println("ID: " + root.id + " | Name: " + root.name);
//            inorder(root.right);
//        }
//    }
//}


public class HouseholdBST {
    Household root;

    public Household insert(Household root, int id, String name) {
        if (root == null) return new Household(id, name);
        if (id < root.id) root.left = insert(root.left, id, name);
        else root.right = insert(root.right, id, name);
        return root;
    }

    public Household search(Household root, int id) {
        if (root == null || root.id == id) return root;
        return (id < root.id) ? search(root.left, id) : search(root.right, id);
    }

    public void inorder(Household root, javax.swing.JTextArea area) {
        if (root != null) {
            inorder(root.left, area);
            area.append("ID: " + root.id + " | Name: " + root.name + "\n");
            inorder(root.right, area);
        }
    }
}