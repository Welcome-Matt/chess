import dataaccess.MemoryUserDAO;
import dataaccess.UserDAO;
import server.Server;
import service.UserService;

public class Main {
    public static void main(String[] args) {

        UserDAO userDAO = new MemoryUserDAO();

        UserService userService = new UserService(userDAO);
        Server server = new Server(userService);
        server.run(8080);

        System.out.println("♕ 240 Chess Server");
    }
}
