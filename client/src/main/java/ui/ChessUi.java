package ui;

import chess.*;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class ChessUi {

    private static ChessBoard uiBoard;
    private static Collection<ChessMove> validMoves;

    public static void main(ChessBoard board, String team, ChessPosition piecePosition) {
        if (team.equals("OBSERVE")) {
            team = "WHITE";
        }

        if (piecePosition != null && board.getPiece(piecePosition) != null) {
            validMoves = board.getPiece(piecePosition).pieceMoves(board, piecePosition);
        }
        uiBoard = board;
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, team);
        drawChessBoard(out, team);
        drawHeaders(out, team);

        out.print(RESET_BG_COLOR);
        out.print(RESET_TEXT_COLOR);
        validMoves = null;
    }

    private static void drawHeaders(PrintStream out, String team) {
        String[] header;
        setDarkGreen(out);
        out.print("   ");
        if (team.equals("WHITE")) {
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
        int boardRow = 7;
        if (team.equals("BLACK")) {
            sideNum = 1;
            boardRow = 0;
        }

        while (boardRow < 8 && boardRow >= 0) {
            printNum(out, sideNum);

            drawRowOfSquares(out, boardRow, team);

            printNum(out, sideNum);
            resetColor(out);
            out.println();

            if (team.equals("BLACK")) {
                sideNum++;
                boardRow++;
            } else {
                sideNum--;
                boardRow--;
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, int boardRow, String team) {
        int boardCol = 0;
        if (team.equals("BLACK")) {
            boardCol = 7;
        }

        while (boardCol < 8 && boardCol >= 0) {
            if (boardRow % 2 == 0) {
                if (boardCol % 2 == 1) {
                    setLightGrey(out);
                } else {
                    setDarkGrey(out);
                }

            } else {
                if (boardCol % 2 == 1) {
                    setDarkGrey(out);
                } else {
                    setLightGrey(out);
                }
            }

            drawSquare(out, boardCol, boardRow);
            if (team.equals("BLACK")) {
                boardCol--;
            } else {
                boardCol++;
            }
        }
    }

    private static void drawSquare(PrintStream out, int col, int row) {
        ChessPiece piece = uiBoard.getPiece(new ChessPosition(row+1, col+1));
        if (validMoves != null) {
            for (ChessMove move : validMoves) {
                if (new ChessPosition(row + 1, col + 1).equals(move.getEndPosition()) ||
                        new ChessPosition(row + 1, col + 1).equals(move.getStartPosition())) {
                    if (new ChessPosition(row + 1, col + 1).equals(move.getStartPosition())) {
                        setRed(out);
                    } else if (row % 2 == 0) {
                        if (col % 2 == 1) {
                            setLightYellow(out);
                        } else {
                            setYellow(out);
                        }

                    } else {
                        if (col % 2 == 1) {
                            setYellow(out);
                        } else {
                            setLightYellow(out);
                        }
                    }
                }
            }
        }

        if (piece != null && piece.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            out.print(SET_TEXT_COLOR_WHITE);
        } else {
            out.print(SET_TEXT_COLOR_BLACK);
        }

        if (piece == null) {
            out.print("   ");
        } else {
            switch (piece.getPieceType()) {
                case KING:
                    out.print(" K ");
                    break;
                case QUEEN:
                    out.print(" Q ");
                    break;
                case ROOK:
                    out.print(" R ");
                    break;
                case BISHOP:
                    out.print(" B ");
                    break;
                case KNIGHT:
                    out.print(" N ");
                    break;
                case PAWN:
                    out.print(" P ");
                    break;
            }
        }
    }

    private static void printNum(PrintStream out, int num) {
        out.print(SET_BG_COLOR_DARK_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
        out.print(" " + num + " ");
    }

    private static void setRed(PrintStream out) {
        out.print(SET_TEXT_COLOR_RED);
        out.print(SET_BG_COLOR_RED);
    }

    private static void setLightYellow(PrintStream out) {
        out.print(SET_TEXT_COLOR_LIGHT_YELLOW);
        out.print(SET_BG_COLOR_LIGHT_YELLOW);
    }

    private static void setYellow(PrintStream out) {
        out.print(SET_BG_COLOR_YELLOW);
        out.print(SET_TEXT_COLOR_YELLOW);
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
