package server;

import Handler.RegisterHandler;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import io.javalin.*;
import io.javalin.http.Context;
import service.UserService;

import java.util.Map;

public class Server {

    private final UserService userService;
    private final Javalin javalin;

    public Server() {
        this(new UserService(new MemoryUserDAO()));
    }

    public Server(UserService userService) {
        this.userService = userService;
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
            .post("/user", this::register)
            //.post("/session", this::login)
            //.delete("/session", this::logout)
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
            ctx.result(new RegisterHandler(ctx, userService).getRegistered());
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

    public void clear(Context ctx) {
        ctx.status(200);
    }
}
