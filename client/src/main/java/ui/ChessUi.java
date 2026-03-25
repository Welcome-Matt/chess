package ui;

import chess.ChessBoard;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import static ui.EscapeSequences.*;

public class ChessUi {

    private static final int BOARD_SIZE_IN_SQUARES = 10;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 3;

    public static void main(ChessBoard board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);
        drawChessBoard(out);
        drawHeaders(out);

        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
    }

    private static void drawHeaders(PrintStream out) {
        setDarkGreen(out);
        out.print("   ");
        String[] header = {"a", "b", "c", "d", "e", "f", "g", "h"};
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES - 2; ++boardCol) {
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

    private static void drawChessBoard(PrintStream out) {
        int sideNum = 8;
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
            sideNum--;
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
