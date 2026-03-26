package client;

import chess.*;
import exception.ResponseException;
import model.GameData;
import model.GameRequest;
import model.UserRequest;
import model.UserResult;
import server.ServerFacade;
import ui.ChessUi;

import java.util.*;

import static ui.EscapeSequences.*;

public class ClientMain {

    private static ServerFacade server = new ServerFacade("http://localhost:8080");
    private static String status = "LOGGED_OUT";
    private static String authToken;
    private static Map<Integer, GameData> games = new HashMap<>();

    public ClientMain(String serverUrl) {
        server = new ServerFacade(serverUrl);
    }

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("♕ 240 Chess Client: Type \"help\" to get a list of commands.");

        Scanner scanner = new Scanner(System.in);
        var result = "";

        while (!result.equals("quit")) {
            System.out.print("\n"+ SET_TEXT_COLOR_WHITE + "[" + status + "] " + ">>> ");
            String line = scanner.nextLine();

            try {
                String[] tokens = line.split(" ");
                String cmd = (tokens.length > 0) ? tokens[0] : "help";
                String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
                result = cmd;

                if (status.equals("LOGGED_IN")) {
                    if (cmd.equals("quit")) {
                        result = "Hamburger";
                    }

                    postUi(cmd, params);
                } else {
                    preUi(cmd, params);
                }

            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private static void preUi(String cmd, String[] params) throws ResponseException {
        switch (cmd) {
            case "white":
                ChessUi.main(new ChessBoard(), "White");
                break;
            case "black":
                ChessUi.main(new ChessBoard(), "Black");
                break;
            case "register":
                UserResult regResult = server.register(new UserRequest(params[0], params[1], params[2]));
                status = "LOGGED_IN";
                authToken = regResult.authToken();
                System.out.print(regResult.username() + " has been registered\n");
                break;
            case "login":
                if (params.length == 2) {
                    UserResult userResult = server.login(new UserRequest(params[0], params[1], "email"));
                    status = "LOGGED_IN";
                    authToken = userResult.authToken();
                } else {
                    System.out.print("Invalid login format!\n");
                }

                break;
            case "quit":
                break;
            default:
                preHelp();
        }
    }

    private static void postUi(String cmd, String[] params) throws ResponseException {
        switch (cmd) {
            case "create":
                server.createGame(new GameRequest(params[0], null, 0), authToken);
                break;
            case "list":
                ArrayList<GameData> gameList = server.listGame(authToken).games();
                int i = 1;
                games = new HashMap<>();
                for (GameData game : gameList) {
                    games.put(i, game);
                    System.out.print(i + ". Name: " + game.gameName() +
                            " - White Player: " + game.whiteUsername() +
                            " - Black Player: " + game.blackUsername() + "\n");
                    i++;
                }
                break;
            case "join":
                break;
            case "observe":
                break;
            case "logout":
                server.logout(authToken);
                status = "LOGGED_OUT";
                System.out.print("You have been logged out\n");
                break;
            default:
                postHelp();
        }
    }

    private static void preHelp() {
        System.out.print(
            SET_TEXT_COLOR_BLUE +
            "  register <username> <password> <email>" +
            SET_TEXT_COLOR_MAGENTA +
            " - Create a chess account\n" +
            SET_TEXT_COLOR_BLUE +
            "  login <username> <password>" +
            SET_TEXT_COLOR_MAGENTA +
            " - Start playing chess\n" +
            SET_TEXT_COLOR_BLUE +
            "  quit" +
            SET_TEXT_COLOR_MAGENTA +
            " - Stop playing chess\n" +
            SET_TEXT_COLOR_BLUE +
            "  help" +
            SET_TEXT_COLOR_MAGENTA +
            " - Shows possible commands\n"
            + RESET_TEXT_COLOR
        );
    }

    private static void postHelp() {
        System.out.print(
            SET_TEXT_COLOR_BLUE +
            "  create <name>" +
            SET_TEXT_COLOR_MAGENTA +
            " - Creates a chess game\n" +
            SET_TEXT_COLOR_BLUE +
            "  list" +
            SET_TEXT_COLOR_MAGENTA +
            " - Shows a list of games\n" +
            SET_TEXT_COLOR_BLUE +
            "  join <id> <WHITE|BLACK>" +
            SET_TEXT_COLOR_MAGENTA +
            " - Join a game\n" +
            SET_TEXT_COLOR_BLUE +
            "  observe <id>" +
            SET_TEXT_COLOR_MAGENTA +
            " - Watch a game\n" +
            SET_TEXT_COLOR_BLUE +
            "  logout" +
            SET_TEXT_COLOR_MAGENTA +
            " - Logout of chess\n" +
            SET_TEXT_COLOR_BLUE +
            "  help" +
            SET_TEXT_COLOR_MAGENTA +
            " - Shows possible commands\n"
            + RESET_TEXT_COLOR
        );
    }
}
