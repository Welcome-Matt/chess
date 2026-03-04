package dataaccess;

import model.AuthData;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    private final HashMap<String, AuthData> auths = new HashMap<>();

    public void createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        auths.put(auth.authToken(), auth);
    }

    public AuthData getAuthByUser(String username) throws DataAccessException {
        for (String key : auths.keySet()) {
            AuthData auth = auths.get(key);
            if (auth.username().equals(username)) {
                return auth;
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        }

        return null;
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return auths.get(authToken);
    }

    public void deleteAuth(AuthData auth) throws DataAccessException {
        auths.remove(auth.authToken());
    }

    public void clearAuthData() {
        auths.clear();
    }
}
