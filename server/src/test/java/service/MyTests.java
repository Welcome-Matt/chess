package service;

import dataaccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyTests {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();

    final UserService userService = new UserService(userDAO, authDAO);
    final GameService gameService = new GameService(gameDAO, authDAO);
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
        UserResult result  = userService.register(userRequest);
        userService.logout(result.authToken());
        assertNull(authDAO.getAuthByUser(userRequest.username()));
    }

    @Test
    void badLogout() throws DataAccessException {
        userService.register(userRequest);
        assertThrows(DataAccessException.class, () -> userService.logout("badAuth"));
    }

    @Test
    void listGames() throws DataAccessException {
        gameService.createGame(new GameRequest("newGame", null, 0));
        gameService.createGame(new GameRequest("anotherGame", null, 0));
        assertEquals(2, gameService.listGames().games().size());

    }

    @Test
    void badListGames() throws DataAccessException {
        assertThrows(DataAccessException.class, () ->
                gameService.createGame(new GameRequest(null, null, 0)));
    }

    @Test
    void createGame() throws DataAccessException {
        gameService.createGame(new GameRequest("newGame", null, 0));
        assertEquals(1, gameService.listGames().games().size());
    }

    @Test
    void badCreateGame() throws DataAccessException {
        assertThrows(DataAccessException.class, () ->
                gameService.createGame(new GameRequest(null, null, 0)));
    }

    @Test
    void joinGame() throws DataAccessException {
        UserResult userResult  = userService.register(userRequest);
        GameResult gameResult = gameService.createGame(new GameRequest("newGame", null, 0));
        gameDAO.getGame(gameResult.gameID());
        gameService.joinGame(new GameRequest(null, "WHITE", gameResult.gameID()),
                authDAO.getAuthByUser(userRequest.username()).authToken());
        GameData game = gameDAO.getGame(gameResult.gameID());
        assertEquals(game.whiteUsername(), userResult.username());
    }

    @Test
    void badJoinGame() throws DataAccessException {
        userService.register(userRequest);
        GameResult gameResult = gameService.createGame(new GameRequest("newGame", null, 0));
        assertThrows(DataAccessException.class, () ->
                gameService.joinGame(new GameRequest(null, null, gameResult.gameID()),
                        authDAO.getAuthByUser(userRequest.username()).authToken()));
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
