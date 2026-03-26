package client;

import chess.*;
import ui.ChessUi;

import java.util.Arrays;
import java.util.Scanner;
import static ui.EscapeSequences.*;

public class ClientMain {

    static String status = "LOGGED_OUT";

    public static void main(String[] args) {
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
                var msg = e.toString();
                System.out.print(msg);
            }
        }
    }

    private static void preUi(String cmd, String[] params) {
        switch (cmd) {
            case "white":
                ChessUi.main(new ChessBoard(), "White");
                break;
            case "black":
                ChessUi.main(new ChessBoard(), "Black");
                break;
            case "register":
                System.out.print("You got registered\n");
                break;
            case "login":
                status = "LOGGED_IN";
                break;
            case "quit":
                break;
            default:
                preHelp();
        }
    }

    private static void postUi(String cmd, String[] params) {
        switch (cmd) {
            case "create":
                ChessUi.main(new ChessBoard(), "White");
                break;
            case "list":
                ChessUi.main(new ChessBoard(), "Black");
                break;
            case "join":
                break;
            case "observe":
                break;
            case "logout":
                status = "LOGGED_OUT";
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
