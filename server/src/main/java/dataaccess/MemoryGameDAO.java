package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
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

    public ArrayList<GameData> listGame() {
        ArrayList<GameData> gameList = new ArrayList<>();
        for(int key : games.keySet()) {
            gameList.add(games.get(key));
        }

        return gameList;
    }

    public void updateGame(int gameId, String playerColor, String username, ChessGame chess) throws DataAccessException {
        GameData game = games.get(gameId);
        if (playerColor.equals("WHITE") && game.whiteUsername() == null) {
            GameData gameUpdate = new GameData(gameId, username, game.blackUsername(), game.gameName(),  game.game());
            games.remove(gameId);
            games.put(gameId, gameUpdate);
        } else if (playerColor.equals("BLACK") && game.blackUsername() == null) {
            GameData gameUpdate = new GameData(gameId, game.whiteUsername(), username, game.gameName(),  game.game());
            games.remove(gameId);
            games.put(gameId, gameUpdate);
        } else {
            throw new DataAccessException("Error: already taken");
        }

    }

    public void clearGames() {
        games.clear();
    }
}
