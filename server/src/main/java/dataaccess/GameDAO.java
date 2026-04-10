package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;

public interface GameDAO {
    GameData createGame(String gameName) throws DataAccessException;

    GameData getGame(int gameId) throws DataAccessException;

    ArrayList<GameData> listGame() throws DataAccessException;

    void updateGame(int gameId, String playerColor, String username, ChessGame chess) throws DataAccessException;

    void clearGames() throws DataAccessException;
}
