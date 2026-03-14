package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MySqlGameDAO implements GameDAO {
    private int nextId = 1;

    public MySqlGameDAO() throws DataAccessException {
        configureGameDAO();
    }

    public GameData createGame(String gameName) throws DataAccessException {
        int gameID = nextId;
        var statement = "INSERT INTO games (gameID, gameName, json) VALUES (?, ?, ?)";
        String json = new Gson().toJson(new ChessGame());
        Update.executeUpdate(statement, gameID, gameName, json);
        ChessGame game = new Gson().fromJson(json, ChessGame.class);
        nextId++;
        return new GameData(gameID, null, null, gameName, game);
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM games WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return readGame(rs);
                    }
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException("Error: unable to read data");
        }

        return null;
    }

    public ArrayList<GameData> listGame() throws DataAccessException {
        ArrayList<GameData> gameList = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM games WHERE gameID=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        gameList.add(readGame(rs));
                    }
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException("Error: unable to read data");
        }

        return gameList;
    }

    public void updateGame(int gameId, String playerColor, String username) throws DataAccessException {
        GameData game = getGame(gameId);
        if (playerColor.equals("WHITE") && game.whiteUsername() == null) {
            var statement = "UPDATE games SET whiteUsername=? WHERE gameID=?";
            Update.executeUpdate(statement, username, gameId);
        } else if (playerColor.equals("BLACK") && game.blackUsername() == null) {
            var statement = "UPDATE games SET blackUsername=? WHERE gameID=?";
            Update.executeUpdate(statement, username, gameId);
        } else {
            throw new DataAccessException("Error: already taken");
        }
    }

    public void clearGames() throws DataAccessException {
        var statement = "TRUNCATE games";
        Update.executeUpdate(statement);
    }

    private GameData readGame(ResultSet rs) throws SQLException {
        int SqlGameId = rs.getInt("gameID");
        String SqlWhite = rs.getString("whiteUsername");
        String SqlBlack = rs.getString("blackUsername");
        String SqlName = rs.getString("gameName");
        String SqlJson = rs.getString("json");
        ChessGame game = new Gson().fromJson(SqlJson, ChessGame.class);
        return new GameData(SqlGameId, SqlWhite, SqlBlack, SqlName, game);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `gameID` int NOT NULL,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) NOT NULL,
              `json` TEXT NOT NULL,
              PRIMARY KEY (`gameID`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureGameDAO() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: database failed");
        }
    }
}
