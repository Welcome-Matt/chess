package dataaccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DataaccessTests {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserData userData = new UserData("Mr.Cool", "Mr.CoolPassword", "Mr.email");
    UserData badUserData = new UserData(null, "Password", "hamburger@gamil.com");
    GameData gameData = new GameData(1, null, null, "newGame", new ChessGame());

    public DataaccessTests() {
        try {
            userDAO = new MySqlUserDAO();
            authDAO = new MySqlAuthDAO();
            gameDAO = new MySqlGameDAO();
        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }
    }

    @BeforeEach
    void clear() throws DataAccessException {
        userDAO.clearUserData();
        gameDAO.clearGames();
        authDAO.clearAuthData();
    }

    @Test
    void createUser() throws DataAccessException {
        userDAO.createUser(userData);
        UserData userData1 = userDAO.getUser("Mr.Cool");
        assertTrue(BCrypt.checkpw(userData.password(),userData1.password()));
        assertEquals(userData.email(), userData1.email());
    }

    @Test
    void badCreateUser() {
        assertThrows(DataAccessException.class, () ->
                userDAO.createUser(badUserData));
    }

    @Test
    void getUser() throws DataAccessException {
        createUser();
    }

    @Test
    void badGetUser() throws DataAccessException {
        userDAO.createUser(userData);
        assertNull(userDAO.getUser("hamburger"));
    }

    @Test
    void createAuth() throws DataAccessException {
        String authToken = authDAO.createAuth(userData.username());
        assertEquals(authToken, authDAO.getAuth(authToken).authToken());
    }

    @Test
    void badCreateAuth() {
        assertThrows(DataAccessException.class, () ->
                authDAO.createAuth(null));
    }

    @Test
    void getAuthByUser() throws DataAccessException {
        String authToken = authDAO.createAuth(userData.username());
        AuthData authData = authDAO.getAuthByUser(userData.username());
        assertEquals(authToken, authData.authToken());
    }

    @Test
    void badGetAuthByUser() throws DataAccessException {
        authDAO.createAuth(userData.username());
        assertNull(authDAO.getAuthByUser(null));
    }

    @Test
    void getAuth() throws DataAccessException {
        createAuth();
    }

    @Test
    void badGetAuth() throws DataAccessException {
        authDAO.createAuth(userData.username());
        assertNull(authDAO.getAuth("Total an auth Token"));
    }

    @Test
    void deleteAuth() throws DataAccessException {
        String authToken = authDAO.createAuth(userData.username());
        AuthData authData = authDAO.getAuth(authToken);
        authDAO.deleteAuth(authData);
        assertNull(authDAO.getAuth(authToken));
    }

    @Test
    void badDeleteAuth() throws DataAccessException {
        authDAO.createAuth(userData.username());
        assertThrows(DataAccessException.class, () ->
                authDAO.deleteAuth(new AuthData(null, "someone")));
    }

    @Test
    void createGame() throws DataAccessException {
        GameData gameData1 = gameDAO.createGame("newGame");
        assertEquals(gameData1, gameData);
    }

    @Test
    void badCreateGame() {
        assertThrows(DataAccessException.class, () ->
                gameDAO.createGame(null));
    }

    @Test
    void getGame() throws DataAccessException {
        GameData gameData1 = gameDAO.createGame("newGame");
        GameData gameData2 = gameDAO.getGame(gameData1.gameID());
        assertEquals(gameData2, gameData1);
    }

    @Test
    void badGetGame() throws DataAccessException {
        gameDAO.createGame("newGame");
        assertNull(gameDAO.getGame(-8));
    }

    @Test
    void listGame() throws DataAccessException {
        gameDAO.createGame("newGame");
        gameDAO.createGame("newGame2");
        ArrayList<GameData> list = gameDAO.listGame();
        assertEquals(2, list.size());
    }

    @Test
    void badListGame() throws DataAccessException {
        badCreateGame();
    }

    @Test
    void updateGame() throws DataAccessException {
        GameData gameData1 = gameDAO.createGame("newGame");
        gameDAO.updateGame(gameData1.gameID(), "WHITE", "Mr.Cool", null);
        GameData gameData2 = gameDAO.getGame(gameData1.gameID());
        assertEquals("Mr.Cool", gameData2.whiteUsername());
    }

    @Test
    void badUpdateGame() throws DataAccessException {
        GameData gameData1 = gameDAO.createGame("newGame");
        gameDAO.updateGame(gameData1.gameID(), "WHITE", "Mr.Cool", null);
        assertThrows(DataAccessException.class, () ->
                gameDAO.updateGame(gameData1.gameID(), "WHITE", "MR.CoolBro", null));
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
