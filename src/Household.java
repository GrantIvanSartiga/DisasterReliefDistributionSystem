//import java.util.*;
//
//public class Household {
//    int id;
//    String name;
//    Household left, right;
//
//    public Household(int id, String name) {
//        this.id = id;
//        this.name = name;
//        left = right = null;
//    }
//}


import java.util.*;

public class Household {
    int id;
    String name;

    public List<ReliefRequest> requests = new ArrayList<>();
    public List<ReliefRequest> processed = new ArrayList<>();

    // for BST compatibility
    Household left, right;

    public Household(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
