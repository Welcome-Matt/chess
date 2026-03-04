package service;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import model.GameData;

public class GameService {
    private final AuthDAO authData;
    private final GameDAO gameData;

    public GameService(GameDAO gameData, AuthDAO authData) {
        this.gameData = gameData;
        this.authData = authData;
    }

    public void listGames(String authToken) throws DataAccessException {
        if (authData.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }


    }

    public GameResult createGame(GameRequest request) throws DataAccessException {
        if (request.gameName() == null) {
            throw new DataAccessException("Error: bad request");
        }

        GameData game = gameData.createGame(request.gameName());
        return new GameResult(game.gameID(), null);
    }

    public void authenticate(String authToken) throws DataAccessException {
        if (authData.getAuth(authToken) == null) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
