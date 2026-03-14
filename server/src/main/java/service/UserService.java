package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDAO userData;
    private final AuthDAO authData;

    public UserService(UserDAO userData, AuthDAO authData) {
        this.userData = userData;
        this.authData = authData;
    }

    public UserResult register(UserRequest registerRequest) throws DataAccessException {
        if (registerRequest.email() == null || registerRequest.password() == null) {
            throw new DataAccessException("Error: bad request");
        } else if (userData.getUser(registerRequest.username()) == null) {
            UserData user = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
            userData.createUser(user);
            String authToken = authData.createAuth(user.username());
            return new UserResult(user.username(), authToken);
        } else {
            throw new DataAccessException("Error: already taken");
        }
    }

    public UserResult login(UserRequest loginRequest) throws DataAccessException {
        if (loginRequest.username() == null || loginRequest.password() == null) {
            throw new DataAccessException("Error: bad request");
        }

        UserData user = userData.getUser(loginRequest.username());
        if (user != null && BCrypt.checkpw(loginRequest.password(), user.password())) {
            authData.getAuthByUser(user.username());
            String authToken = authData.createAuth(user.username());
            return new UserResult(user.username(), authToken);
        } else {
            throw new DataAccessException("Error: unauthorized");
        }

    }

    public void logout(String authToken) throws DataAccessException {
        if (authData.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }

        authData.deleteAuth(authData.getAuth(authToken));
    }

    public void clear() throws DataAccessException {
        userData.clearUserData();
        authData.clearAuthData();
    }
}
