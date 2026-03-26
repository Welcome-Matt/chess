package handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import model.GameRequest;
import model.GameResult;
import service.GameService;

public class GameHandler {
    public Context ctx;
    public final GameService gameService;

    public GameHandler(Context ctx, GameService gameService) {
        this.ctx = ctx;
        this.gameService = gameService;
    }

    public String getGameList() throws DataAccessException {
        GameResult result = gameService.listGames();
        return new Gson().toJson(result);
    }

    public String createGame() throws DataAccessException {
        GameRequest request = new Gson().fromJson(ctx.body(), GameRequest.class);
        GameResult result = gameService.createGame(request);
        return new Gson().toJson(result);
    }

    public void joinGame(String authToken) throws DataAccessException {
        GameRequest request = new Gson().fromJson(ctx.body(), GameRequest.class);
        gameService.joinGame(request, authToken);
    }
}
