package dataaccess;

import model.UserData;
import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    private final HashMap<String, UserData> users = new HashMap<>();

    public UserData createUser(UserData user) {
        users.put(user.username(), user);
        return user;
    }

    public UserData getUser(String username) {
        return users.get(username);
    }

    public void deleteUser(String username) {
        users.remove(username);
    }

    public void clearUserData() {
        users.clear();
    }
}
