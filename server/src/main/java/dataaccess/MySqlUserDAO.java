package dataaccess;

import model.*;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MySqlUserDAO implements UserDAO {

    public MySqlUserDAO() throws DataAccessException {
        configureUserDAO();
    }

    public void createUser(UserData user) throws DataAccessException {
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        var statement = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        Update.executeUpdate(statement, user.username(), hashedPassword, user.email());
    }

    public UserData getUser(String username) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username, password, email FROM user WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(statement)){
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String SqlUsername = rs.getString("username");
                        String SqlPassword = rs.getString("password");
                        String SqlEmail = rs.getString("email");
                        return new UserData(SqlUsername, SqlPassword, SqlEmail);
                    }
                }
            }
        } catch (Exception ex) {
            throw new DataAccessException("Error: unable to read data");
        }

        return null;
    }

    public void clearUserData() throws DataAccessException {
        var statement = "TRUNCATE user";
        Update.executeUpdate(statement);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS user (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              PRIMARY KEY (`username`),
              INDEX(password)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureUserDAO() throws DataAccessException {
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
