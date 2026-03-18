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

    // ✅ BINARY TREE NODE (Week 11)
    Household left, right;

    // Requests storage
    List<ReliefRequest> requests = new ArrayList<>();
    List<ReliefRequest> processed = new ArrayList<>();

    public Household(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
