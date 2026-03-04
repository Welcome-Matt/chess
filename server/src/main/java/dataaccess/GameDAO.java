package dataaccess;

import model.GameData;

public interface GameDAO {
    GameData createGame(String gameName) throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    void listGame() throws DataAccessException;

    void updateGame() throws DataAccessException;

    void clearGames() throws DataAccessException;
}
