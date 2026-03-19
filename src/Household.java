import java.util.*;

public class Household {
    int id;
    String name;
    Household left, right;

    List<ReliefRequest> requests = new ArrayList<>();
    List<ReliefRequest> processed = new ArrayList<>();

    public Household(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
