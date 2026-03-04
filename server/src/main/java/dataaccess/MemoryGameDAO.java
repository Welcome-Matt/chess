package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.HashMap;

public class MemoryGameDAO implements GameDAO {
    private int nextId = 1;
    private final HashMap<Integer, GameData> games = new HashMap<>();

    public GameData createGame(String gameName) {
        GameData game = new GameData(nextId++, null, null, gameName, new ChessGame());
        games.put(game.gameID(), game);
        return game;
    }

    public GameData getGame(int gameId) {
        return games.get(gameId);
    }

    public void listGame() {

    }

    public void updateGame() {

    }

    public void clearGames() {

    }
}
