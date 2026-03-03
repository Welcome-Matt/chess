package server;

import Handler.RegisterHandler;
import dataaccess.DataAccessException;
import io.javalin.*;
import io.javalin.http.Context;

public class Server {

    private final Javalin javalin;

    public Server() {
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

    public void register(Context ctx) throws DataAccessException {
         ctx.result(new RegisterHandler(ctx).getRegistered());
         ctx.status(200);
    }

    public void clear(Context ctx) {
        ctx.status(200);
    }
}
