package server;

import chess.ChessGame;
import handler.GameHandler;
import handler.UserHandler;
import com.google.gson.Gson;
import dataaccess.*;
import handler.WebSocketHandler;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.GameData;
import service.GameService;
import service.UserService;

import java.util.Map;

public class Server {
    UserDAO userDAO;
    AuthDAO authDAO;
    GameDAO gameDAO;
    UserService userService;
    GameService gameService;
    private final Javalin javalin;
    private final WebSocketHandler webSocketHandler;

    public Server() {
        try {
            userDAO = new MySqlUserDAO();
            authDAO = new MySqlAuthDAO();
            gameDAO = new MySqlGameDAO();
            userService = new UserService(userDAO, authDAO);
            gameService = new GameService(gameDAO, authDAO);
        } catch (Throwable ex) {
            System.out.printf("Unable to start server: %s%n", ex.getMessage());
        }

        webSocketHandler = new WebSocketHandler(this);

        javalin = Javalin.create(config -> config.staticFiles.add("web"))
            .post("/user", this::register)
            .post("/session", this::login)
            .delete("/session", this::logout)
            .get("/game", this::listGames)
            .post("/game", this::createGame)
            .put("/game", this::joinGame)
            .delete("/db", this::clear)
            .ws("/ws", ws -> {
                ws.onConnect(webSocketHandler);
                ws.onMessage(webSocketHandler);
                ws.onClose(webSocketHandler);
            });

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }

    public void register(Context ctx) {
        try {
            ctx.status(200).result(new UserHandler(ctx, userService).getRegistered());
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    public void login(Context ctx) {
        try {
            ctx.status(200).result(new UserHandler(ctx, userService).getLoggedIn());
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    public void logout(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            userService.logout(authToken);
            ctx.status(200);
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    public void listGames(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            gameService.authenticate(authToken);
            ctx.status(200).result(new GameHandler(ctx, gameService).getGameList());
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    public void createGame(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            gameService.authenticate(authToken);
            ctx.result(new GameHandler(ctx, gameService).createGame());
            ctx.status(200);
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    public void joinGame(Context ctx) {
        try {
            String authToken = ctx.header("authorization");
            gameService.authenticate(authToken);
            new GameHandler(ctx, gameService).joinGame(authToken);
            ctx.status(200);
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    public void authenticate(String authToken) throws DataAccessException {
        gameService.authenticate(authToken);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }

    public void updateGame(int gameID, ChessGame game) throws DataAccessException {
        gameDAO.updateGame(gameID, null, null, game);
    }

    public void clear(Context ctx) throws DataAccessException {
        try {
            userService.clear();
            gameService.clear();
            ctx.status(200);
        } catch (DataAccessException ex) {
            exception(ex, ctx);
        }
    }

    private void exception(DataAccessException ex, Context ctx) {
        String error = ex.getMessage();
        switch (error) {
            case "Error: unauthorized" -> ctx.status(401).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            case "Error: bad request" -> ctx.status(400).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            case "Error: already taken" ->
                    ctx.status(403).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            default -> ctx.status(500).result(new Gson().toJson(Map.of("message", ex.getMessage())));
        }
    }
}
