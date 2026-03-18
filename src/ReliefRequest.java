import java.util.*;

public class ReliefRequest {

    int householdId;
    int urgency; // higher = more urgent

    public ReliefRequest(int householdId, int urgency) {
        this.householdId = householdId;
        this.urgency = urgency;
    }
}
