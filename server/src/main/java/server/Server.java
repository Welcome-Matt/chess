package server;

import handler.GameHandler;
import handler.UserHandler;
import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.*;
import io.javalin.http.Context;
import service.GameService;
import service.UserService;

import java.util.Map;

public class Server {
    UserDAO userDAO = new MemoryUserDAO();
    AuthDAO authDAO = new MemoryAuthDAO();
    GameDAO gameDAO = new MemoryGameDAO();
    private final UserService userService = new UserService(userDAO, authDAO);
    private final GameService gameService = new GameService(gameDAO, authDAO);
    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
            .post("/user", this::register)
            .post("/session", this::login)
            .delete("/session", this::logout)
            .get("/game", this::listGames)
            .post("/game", this::createGame)
            .put("/game", this::joinGame)
            .delete("/db", this::clear);
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

    public void logout(Context ctx) throws DataAccessException {
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

    public void clear(Context ctx) throws DataAccessException {
        userService.clear();
        gameService.clear();
        ctx.status(200);
    }

    private void exception(DataAccessException ex, Context ctx) {
        String error = ex.getMessage();
        switch (error) {
            case "Error: unauthorized" -> ctx.status(401).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            case "Error: bad request" -> ctx.status(400).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            case "Error: already taken" ->
                    ctx.status(403).result(new Gson().toJson(Map.of("message", ex.getMessage())));
        }
    }
}
