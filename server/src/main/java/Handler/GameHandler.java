package Handler;

import com.google.gson.Gson;
import dataaccess.DataAccessException;
import io.javalin.http.Context;
import service.GameRequest;
import service.GameResult;
import service.GameService;
import service.UserRequest;

public class GameHandler {
    public Context ctx;
    public final GameService gameService;

    public GameHandler(Context ctx, GameService gameService) {
        this.ctx = ctx;
        this.gameService = gameService;
    }

    public String getGameList() throws DataAccessException {

        return null;
    }

    public String createGame() throws DataAccessException {
        GameRequest request = new Gson().fromJson(ctx.body(), GameRequest.class);
        GameResult result = gameService.createGame(request);
        return new Gson().toJson(result);
    }

    public String joinGame() throws DataAccessException {

        return null;
    }


}
