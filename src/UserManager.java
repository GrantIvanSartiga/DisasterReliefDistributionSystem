import java.util.*;

public class UserManager {

    private Map<String, User> users = new HashMap<>();
    private User currentUser = null;

    // Register user
    public boolean register(String username, String password){
        if(users.containsKey(username)) return false;
        users.put(username, new User(username,password));
        return true;
    }

    // Login user
    public boolean login(String username, String password){
        if(users.containsKey(username) && users.get(username).getPassword().equals(password)){
            currentUser = users.get(username);
            return true;
        }
        return false;
    }

    // Logout
    public void logout(){ currentUser = null; }

    public User getCurrentUser(){ return currentUser; }
}
