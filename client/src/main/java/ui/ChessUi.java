package ui;

import chess.ChessBoard;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class ChessUi {

    public static void main(ChessBoard board, String team) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, team);
        drawChessBoard(out, team);
        drawHeaders(out, team);

        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    private static void drawHeaders(PrintStream out, String team) {
        String[] header;
        setDarkGreen(out);
        out.print("   ");
        if (team.equals("White")) {
            header = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        } else {
            header = new String[]{"h", "g", "f", "e", "d", "c", "b", "a"};
        }

        for (int boardCol = 0; boardCol < header.length; ++boardCol) {
            printHeaderText(out, header[boardCol]);
        }

        out.print("   ");
        resetColor(out);
        out.println();
    }

    private static void printHeaderText(PrintStream out, String text) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(" " + text + " ");

        setDarkGreen(out);
    }

    private static void drawChessBoard(PrintStream out, String team) {
        int sideNum = 8;
        if (team.equals("Black")) {
            sideNum = 1;
        }

        for (int boardRow = 0; boardRow < 8; ++boardRow) {
            printNum(out, sideNum);

            if (boardRow % 2 == 0) {
                drawRowOfSquares(out);
            } else {
                drawRowOfSquares1(out);
            }

            printNum(out, sideNum);
            resetColor(out);
            out.println();

            if (team.equals("Black")) {
                sideNum++;
            } else {
                sideNum--;
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out) {
        for (int boardCol = 0; boardCol < 4; ++boardCol) {
                setLightGrey(out);
                out.print(SET_TEXT_COLOR_WHITE);
                out.print(" B ");

                setDarkGrey(out);
                out.print(SET_TEXT_COLOR_BLACK);
                out.print(" B ");
        }
    }

    private static void drawRowOfSquares1(PrintStream out) {
        for (int boardCol = 0; boardCol < 4; ++boardCol) {
            setDarkGrey(out);
            out.print("   ");

            setLightGrey(out);
            out.print("   ");
        }
    }

    private static void printNum(PrintStream out, int num) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
        out.print(" " + num + " ");
    }

    private static void setLightGrey(PrintStream out) {
        out.print(SET_BG_COLOR_LIGHT_GREY);
        out.print(SET_TEXT_COLOR_LIGHT_GREY);
    }

    private static void setDarkGrey(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_DARK_GREY);
    }

    private static void setDarkGreen(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_DARK_GREEN);
    }

    private static void resetColor(PrintStream out) {
        out.print(RESET_TEXT_COLOR);
        out.print(RESET_BG_COLOR);
    }
}
