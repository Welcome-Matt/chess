package client;

import exception.ResponseException;
import model.GameRequest;
import model.GameResult;
import model.UserRequest;
import model.UserResult;
import org.junit.jupiter.api.*;
import server.Server;
import facade.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @BeforeEach
    public void setUp() throws ResponseException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    void register() throws Exception {
        UserResult result = facade.register(new UserRequest("player1", "password", "p1@email.com"));
        assertTrue(result.authToken().length() > 10);
    }

    @Test
    void badRegister() {
        assertThrows(ResponseException.class, () ->
                facade.register(new UserRequest("player1", null, "p1@email.com")));
    }

    @Test
    void login() throws ResponseException {
        UserResult result = facade.register(new UserRequest("player1", "password", "p1@email.com"));
        UserResult login = facade.login(new UserRequest("player1", "password", null));
        assertNotEquals(login.authToken(), result.authToken());
    }

    @Test
    void badLogin() {
        assertThrows(ResponseException.class, () ->
                facade.login(new UserRequest("player1", null, null)));
    }

    @Test
    void logout() throws ResponseException {
        facade.register(new UserRequest("player1", "password", "p1@email.com"));
        UserResult login = facade.login(new UserRequest("player1", "password", null));
        facade.logout(login.authToken());
        UserResult login2 = facade.login(new UserRequest("player1", "password", null));
        assertNotEquals(login.authToken(), login2.authToken());
    }

    @Test
    void badLogout() {
        assertThrows(ResponseException.class, () ->
                facade.logout("authToken"));
    }

    @Test
    void createGame() throws ResponseException {
        UserResult result = facade.register(new UserRequest("player1", "password", "p1@email.com"));
        facade.createGame(new GameRequest("newgame", null, 0), result.authToken());
        assertFalse(facade.listGame(result.authToken()).games().isEmpty());
    }

    @Test
    void badCreateGame() {
        assertThrows(ResponseException.class, () ->
                facade.createGame(new GameRequest("newgame", null, 0), "authToken"));
    }

    @Test
    void listGame() throws ResponseException {
        UserResult result = facade.register(new UserRequest("player1", "password", "p1@email.com"));
        facade.createGame(new GameRequest("newgame", null, 0), result.authToken());
        facade.createGame(new GameRequest("newgame2", null, 0), result.authToken());
        assertEquals(2, facade.listGame(result.authToken()).games().size());
    }

    @Test
    void badListGame() {
        assertThrows(ResponseException.class, () ->
                facade.listGame("authToken"));
    }

    @Test
    void joinGame() throws ResponseException {
        UserResult result = facade.register(new UserRequest("player1", "password", "p1@email.com"));
        facade.createGame(new GameRequest("newgame", null, 0), result.authToken());
        facade.joinGame(new GameRequest(null, "WHITE", 1), result.authToken());
        GameResult games = facade.listGame(result.authToken());
        assertEquals(games.games().getFirst().whiteUsername(), result.username());
    }

    @Test
    void badJoinGame() {
        assertThrows(ResponseException.class, () ->
                facade.joinGame(new GameRequest(null, "WHITE", 1), "authToken"));
    }

    @Test
    void clear() throws ResponseException {
        facade.register(new UserRequest("player1", "password", "p1@email.com"));
        facade.clear();
        facade.register(new UserRequest("player1", "password", "p1@email.com"));
    }
}
