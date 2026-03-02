package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

public class UserService {

    private final UserDAO userData;

    public UserService(UserDAO userData) {
        this.userData = userData;
    }
    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        if (userData.getUser(registerRequest.username()) == null) {
            UserData user = new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email());
            userData.createUser(user);
            return new RegisterResult(user.username(), user.password());
        } else {
            throw new DataAccessException("Error: username already taken");
        }
    }

    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        UserData user = userData.getUser(loginRequest.username());
        return new LoginResult(user.username(), user.password());
    }
    //public void logout(LogoutRequest logoutRequest) {}
}
