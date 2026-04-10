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

    public MySqlGameDAO() throws DataAccessException {
        configureGameDAO();
    }

    public GameData createGame(String gameName) throws DataAccessException {
        var statement = "INSERT INTO games (gameName, json) VALUES (?, ?)";
        String json = new Gson().toJson(new ChessGame());
        Update.executeUpdate(statement, gameName, json);
        ChessGame game = new Gson().fromJson(json, ChessGame.class);

        return new GameData(getGameID(gameName).gameID(), null, null, gameName, game);
    }

    public GameData getGameID(String gameName) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM games WHERE gameName=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, gameName);
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
            var statement = "SELECT gameID, whiteUsername, blackUsername, gameName, json FROM games";
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
        int sqlGameId = rs.getInt("gameID");
        String sqlWhite = rs.getString("whiteUsername");
        String sqlBlack = rs.getString("blackUsername");
        String sqlName = rs.getString("gameName");
        String sqlJson = rs.getString("json");
        ChessGame game = new Gson().fromJson(sqlJson, ChessGame.class);
        return new GameData(sqlGameId, sqlWhite, sqlBlack, sqlName, game);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `gameID` INT AUTO_INCREMENT NOT NULL,
              `whiteUsername` varchar(256) DEFAULT NULL,
              `blackUsername` varchar(256) DEFAULT NULL,
              `gameName` varchar(256) NOT NULL,
              `json` TEXT NOT NULL,
              PRIMARY KEY (`gameID`)
            )
            """
    };

    private void configureGameDAO() throws DataAccessException {
        Update.configureDAO(createStatements);
    }
}
