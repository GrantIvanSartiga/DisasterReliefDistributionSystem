import java.util.*;
import java.util.regex.*;

public class UserManager {

    private Map<String, User> users = new HashMap<>();
    private User currentUser = null;

    public boolean register(String username, String password) {
        // Check if username exists
        if (users.containsKey(username)) return false;


        if (!username.matches("^[a-zA-Z0-9_]{4,}$")) return false;


        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")) return false;


        users.put(username, new User(username, password));
        return true;
    }


    public boolean login(String username, String password) {
        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            currentUser = users.get(username);
            return true;
        }
        return false;
    }


    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
