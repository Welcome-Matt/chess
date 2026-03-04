package server;

import Handler.UserHandler;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import io.javalin.http.Context;
import service.UserService;

import java.util.Map;

public class Server {

    private final UserService userService;
    private final Javalin javalin;

    public Server() {
        this(new UserService(new MemoryUserDAO(), new MemoryAuthDAO()));
    }

    public Server(UserService userService) {
        this.userService = userService;
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
            .post("/user", this::register)
            .post("/session", this::login)
            .delete("/session", this::logout)
            //.get("/game", this::listGames)
            //.post("/game", this::createGame)
            //.put("/game", this::joinGame)
            .delete("/db", this::clear);

        // Register your endpoints and exception handlers here.

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
            ctx.result(new UserHandler(ctx, userService).getRegistered());
            ctx.status(200);
        } catch (DataAccessException ex) {
            String error = ex.getMessage();
            if (error.equals("Error: already taken")) {
                ctx.status(403).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            } else if (error.equals("Error: bad request")) {
                ctx.status(400).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            }
        }
    }

    public void login(Context ctx) {
        try {
            ctx.result(new UserHandler(ctx, userService).getLoggedIn());
            ctx.status(200);
        } catch (DataAccessException ex) {
            String error = ex.getMessage();
            if (error.equals("Error: unauthorized")) {
                ctx.status(401).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            } else if (error.equals("Error: bad request")) {
                ctx.status(400).result(new Gson().toJson(Map.of("message", ex.getMessage())));
            }
        }
    }

    public void logout(Context ctx) throws DataAccessException {
        try {
            String authToken = ctx.header("authorization");
            userService.logout(authToken);
            ctx.status(200);
        } catch (DataAccessException ex) {
            ctx.status(401).result(new Gson().toJson(Map.of("message", ex.getMessage())));
        }
    }

    public void clear(Context ctx) throws DataAccessException {
        userService.clear();
        ctx.status(200);
    }
}
