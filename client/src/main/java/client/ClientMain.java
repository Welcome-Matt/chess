package client;

import chess.*;
import ui.ChessUi;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class ClientMain {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            System.out.print("\n"+ SET_TEXT_COLOR_WHITE + ">>> ");
            String line = scanner.nextLine();

            try {
                result = line;
                if (result.equals("board")) {
                    ChessUi.main(new ChessBoard(), "White");
                }

            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }

        System.out.println();
    }
}
