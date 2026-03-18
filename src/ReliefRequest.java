//import java.util.*;
//
//public class ReliefRequest {
//
//    int householdId;
//    int urgency; // higher = more urgent
//
//    public ReliefRequest(int householdId, int urgency) {
//        this.householdId = householdId;
//        this.urgency = urgency;
//    }
//}


public class ReliefRequest {
    String requestName;
    int urgency;

    public ReliefRequest(String requestName, int urgency) {
        this.requestName = requestName;
        this.urgency = urgency;
    }
}