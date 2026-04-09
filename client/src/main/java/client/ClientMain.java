package client;

import chess.*;
import exception.ResponseException;
import model.GameData;
import model.GameRequest;
import model.UserRequest;
import model.UserResult;
import facade.ServerFacade;
import ui.ChessUi;
import client.NotificationHandler;
import websocket.messages.ServerMessage;

import java.util.*;

import static ui.EscapeSequences.*;

public class ClientMain implements NotificationHandler {

    private static ServerFacade server = new ServerFacade("http://localhost:8080");
    private static WebSocketFacade ws;
    private static String status = "LOGGED_OUT";
    private static int inGame = 0;
    private static String authToken;
    private static Map<Integer, GameData> games = new HashMap<>();
    private static String currUser;
    private static int currGameID;

    public ClientMain(String serverUrl) throws ResponseException {
        server = new ServerFacade(serverUrl);
        ws = new WebSocketFacade(serverUrl, this);
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
                    if (inGame == 1) {
                        gameUi(cmd, params);
                    } else {
                        postUi(cmd, params);
                    }
                } else {
                    preUi(cmd, params);
                }

            } catch (Throwable e) {
                System.out.print(e.getMessage() + "\n");
            }
        }
    }

    private static void preUi(String cmd, String[] params) throws ResponseException {
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        switch (cmd) {
            case "register":
                if (params.length == 3) {
                    UserResult regResult = server.register(new UserRequest(params[0], params[1], params[2]));
                    status = "LOGGED_IN";
                    authToken = regResult.authToken();
                    System.out.print("Welcome " + regResult.username() + ". You have been registered and logged in.\n");
                    currUser = regResult.username();
                } else {
                    System.out.print("Invalid \"register\" format!\n");
                }

                break;
            case "login":
                if (params.length == 2) {
                    UserResult userResult = server.login(new UserRequest(params[0], params[1], "email"));
                    status = "LOGGED_IN";
                    authToken = userResult.authToken();
                    System.out.print("Hello " + params[0] + ".\n");
                    currUser = params[0];
                } else {
                    System.out.print("Invalid \"login\" format!\n");
                }

                break;
            case "quit":
                System.out.print("Goodbye!\n");
                break;
            default:
                preHelp();
        }
    }

    private static void postUi(String cmd, String[] params) throws ResponseException {
        switch (cmd) {
            case "create":
                if (params.length == 1) {
                    server.createGame(new GameRequest(params[0], null, 0), authToken);
                    System.out.print("Created game " + params[0] + "\n");
                } else {
                    System.out.print("Invalid \"create\" format!\n");
                }

                break;
            case "list":
                if (params.length > 0) {
                    System.out.print("Invalid \"list\" format!\n");
                    break;
                }

                ArrayList<GameData> gameList = server.listGame(authToken).games();
                if (gameList.isEmpty()) {
                    System.out.print("There are no games. Try to \"create\" some.\n");
                } else {
                    int i = 1;
                    games = new HashMap<>();
                    for (GameData game : gameList) {
                        games.put(i, game);
                        System.out.print(i + ". Name: " + game.gameName() + " - White Player: ");
                        if (game.whiteUsername() == null) {
                            System.out.print("<Space Available>");
                        } else {
                            System.out.print(game.whiteUsername());
                        }

                        System.out.print(" - Black Player: ");

                        if (game.blackUsername() == null) {
                            System.out.print("<Space Available>\n");
                        } else {
                            System.out.print(game.blackUsername() + "\n");
                        }

                        i++;
                    }
                }

                break;
            case "join":
                join(params);
                inGame = 1;
                break;
            case "observe":
                observe(params);
                break;
            case "logout":
                server.logout(authToken);
                status = "LOGGED_OUT";
                System.out.print("You have been logged out.\n");
                currUser = null;
                break;
            default:
                postHelp();
        }
    }

    private static void gameUi(String cmd, String[] params) throws ResponseException {
        switch (cmd) {
            case "redraw":
            case "leave":
                ws.leaveGame(authToken, currGameID);
                currGameID = -1;
            case "move":
                ws.makeMove(authToken, currGameID);
            case "resign":
                ws.resignGame(authToken, currGameID);
            case "highlight":
            default:
                postHelp();
        }
    }

    private static void join(String[] params) throws ResponseException {
        if (games.isEmpty()) {
            System.out.print("Please \"list\" the games first!\n");
        } else if (isInteger(params[0])) {
            int gameNum = Integer.parseInt(params[0]);
            if (gameNum > games.size() || gameNum < 1) {
                System.out.print("Please enter a valid game number!\n");
            } else if (params.length == 2) {
                currGameID = games.get(gameNum).gameID();
                server.joinGame(new GameRequest(null, params[1], currGameID), authToken);
                System.out.print("Joined game " + params[0] + "\n");
                ChessUi.main(games.get(gameNum).game().getBoard(), params[1]);
                ws.joinGame(authToken, currGameID);
            } else {
                System.out.print("Invalid \"join\" format!\n");
            }
        } else {
            System.out.print("Please enter a valid game number!\n");
        }
    }

    private static void observe(String[] params) {
        if (games.isEmpty()) {
            System.out.print("Please \"list\" the games first!\n");
        } else if (isInteger(params[0])) {
            int num = Integer.parseInt(params[0]);
            if (num > games.size() || num < 1) {
                System.out.print("Please enter a valid game number!\n");
            } else if (params.length == 1) {
                ChessBoard board = games.get(num).game().getBoard();
                ChessUi.main(board, "White");
            } else {
                System.out.print("Invalid \"observe\" format!\n");
            }
        } else {
            System.out.print("Please enter a valid game number!\n");
        }
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void notify(ServerMessage notification) {
        System.out.println(notification);
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
