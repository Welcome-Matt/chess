package service;

import dataaccess.*;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyTests {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    final UserService userService = new UserService(userDAO, authDAO);
    UserRequest userRequest = new UserRequest("MrMan", "coolPassword", "email@me.com");

    @BeforeEach
    void clear() throws DataAccessException {
        userService.clear();
    }

    @Test
    void register() throws DataAccessException {
        userService.register(userRequest);
        assertEquals(new UserData("MrMan", "coolPassword",
                "email@me.com"), userDAO.getUser("MrMan"));
    }

    @Test
    void badRegister() {
        assertThrows(DataAccessException.class, () ->
                userService.register(new UserRequest("MrMan", null, "email@me.com")));
    }

    @Test
    void login() throws DataAccessException {
        UserResult result = userService.register(userRequest);
        String oldAuth = result.authToken();
        UserRequest request = new UserRequest("MrMan", "coolPassword", "email@me.com");
        result = userService.login(request);
        assertNotEquals(oldAuth, result.authToken());
    }

    @Test
    void badLogin() throws DataAccessException {
        userService.register(userRequest);
        assertThrows(DataAccessException.class, () ->
                userService.login(new UserRequest("ThatGuy", "coolPassword", "email")));
    }

    @Test
    void logout() throws DataAccessException {
        userService.register(userRequest);
    }

    @Test
    void clearDb() throws DataAccessException {
        authDAO.createAuth("ham");
        userDAO.createUser(new UserData("MrMan", "goodPassword", "email@me.com"));
        gameDAO.createGame("coolGame");
        authDAO.clearAuthData();
        userDAO.clearUserData();
        gameDAO.clearGames();
        assertNull(authDAO.getAuthByUser("ham"));
        assertNull(userDAO.getUser("MrMan"));
        assertTrue(gameDAO.listGame().isEmpty());
    }
}
