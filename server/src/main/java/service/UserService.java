package service;

import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import model.UserData;

public class UserService {

    private final UserDAO userData;

    public UserService(UserDAO userData) {
        this.userData = userData;
    }
    //public RegisterResult register(RegisterRequest registerRequest) {}
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        UserData user = userData.getUser(loginRequest.username());
        return new LoginResult(user.username(), user.password());
    }
    //public void logout(LogoutRequest logoutRequest) {}
}
