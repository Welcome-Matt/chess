package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySqlAuthDAO implements AuthDAO {

    public MySqlAuthDAO() throws DataAccessException {
        configureAuthDAO();
    }

    public String createAuth(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        var statement = "INSERT INTO auth (authToken, username) VALUES (?, ?)";
        Update.executeUpdate(statement, auth.authToken(), auth.username());
        return authToken;
    }

    public AuthData getAuthByUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(2, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String SqlAuthToken = rs.getString("authToken");
                        String SqlUsername = rs.getString("username");
                        return new AuthData(SqlAuthToken, SqlUsername);
                    }
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException("Error: unauthorized");
        }

        return null;
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT authToken, username FROM user WHERE authToken=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String SqlAuthToken = rs.getString("authToken");
                        String SqlUsername = rs.getString("username");
                        return new AuthData(SqlAuthToken, SqlUsername);
                    }
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException("Error: unauthorized");
        }

        return null;
    }

    public void deleteAuth(AuthData auth) throws DataAccessException {
        var statement = "DELETE FROM auth WHERE authToken=?";
        Update.executeUpdate(statement, auth);
    }

    public void clearAuthData() throws DataAccessException {
        var statement = "TRUNCATE auth";
        Update.executeUpdate(statement);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS auth (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`, `username`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureAuthDAO() throws DataAccessException {
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
