package dataaccess;

import model.AuthData;
import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {
    private final HashMap<String, AuthData> auths = new HashMap<>();

    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        auths.put(auth.authToken(), auth);
        return authToken;
    }

    public AuthData getAuthByUser(String username) throws DataAccessException {
        int index = 0;
        for (String key : auths.keySet()) {
            AuthData auth = auths.get(key);
            if (auth.username().equals(username)) {
                return auth;
            } else if (index == auths.size()) {
                throw new DataAccessException("Error: unauthorized");
            }

            index++;
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
