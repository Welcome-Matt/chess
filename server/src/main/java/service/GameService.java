package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;

import java.util.ArrayList;

public class GameService {
    private final AuthDAO authData;
    private final GameDAO gameData;

    public GameService(GameDAO gameData, AuthDAO authData) {
        this.gameData = gameData;
        this.authData = authData;
    }

    public GameResult listGames() throws DataAccessException {
        ArrayList<GameData> gameList = gameData.listGame();
        return new GameResult(null, gameList);
    }

    public GameResult createGame(GameRequest request) throws DataAccessException {
        if (request.gameName() == null) {
            throw new DataAccessException("Error: bad request");
        }

        GameData game = gameData.createGame(request.gameName());
        return new GameResult(game.gameID(), null);
    }

    public void joinGame(GameRequest request, String authToken) throws DataAccessException {
        if (request.playerColor() != null &&
                (request.playerColor().equals("WHITE") ||
                request.playerColor().equals("BLACK")) &&
                gameData.getGame(request.gameID()) != null) {
            gameData.updateGame(request.gameID(), request.playerColor(), authData.getAuth(authToken).username());
        } else {
            throw new DataAccessException("Error: bad request");
        }
    }

    public void clear() throws DataAccessException {
        gameData.clearGames();
    }

    public void authenticate(String authToken) throws DataAccessException {
        if (authData.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
