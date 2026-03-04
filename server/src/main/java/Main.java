import dataaccess.AuthDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import server.Server;
import service.UserService;

public class Main {
    public static void main(String[] args) {

        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();

        UserService userService = new UserService(userDAO, authDAO);
        Server server = new Server(userService);
        server.run(8080);

        System.out.println("♕ 240 Chess Server");
    }
}
