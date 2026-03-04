package dataaccess;

import model.AuthData;

public interface AuthDAO {
    String createAuth(String username) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

    AuthData getAuthByUser(String username) throws DataAccessException;

    void deleteAuth(AuthData auth) throws DataAccessException;

    void clearAuthData() throws DataAccessException;
}
