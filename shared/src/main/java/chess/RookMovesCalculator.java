package chess;

import java.util.ArrayList;

public class RookMovesCalculator {

    ArrayList<ChessMove> rookList;
    int row;
    int col;
    int newRow;
    int newCol;
    ChessGame.TeamColor color;

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        row = myPosition.getRow();
        col = myPosition.getColumn();
        color = board.getPiece(myPosition).getTeamColor();
        rookList = new ArrayList<>();

        newRow = row + 1;
        newCol = col;
        while (((newRow) < 9) && (board.getPiece(new ChessPosition(newRow, col)) == null ||
                board.getPiece(new ChessPosition(newRow, col)).getTeamColor() != color)) {
            rookMove();
            if (board.getPiece(new ChessPosition(newRow, col)) != null &&
                    board.getPiece(new ChessPosition(newRow, col)).getTeamColor() != color) {
                break;
            }

            newRow++;
        }

        newRow = row - 1;
        while (((newRow) > 0) && (board.getPiece(new ChessPosition(newRow, col)) == null ||
                board.getPiece(new ChessPosition(newRow, col)).getTeamColor() != color)) {
            rookMove();
            if (board.getPiece(new ChessPosition(newRow, col)) != null &&
                    board.getPiece(new ChessPosition(newRow, col)).getTeamColor() != color) {
                break;
            }

            newRow--;
        }

        newCol = col + 1;
        newRow = row;
        while (((newCol) < 9) && (board.getPiece(new ChessPosition(row, newCol)) == null ||
                board.getPiece(new ChessPosition(row, newCol)).getTeamColor() != color)) {
            rookMove();
            if (board.getPiece(new ChessPosition(row, newCol)) != null &&
                    board.getPiece(new ChessPosition(row, newCol)).getTeamColor() != color) {
                break;
            }

            newCol++;
        }

        newCol = col - 1;
        while (((newCol) > 0) && (board.getPiece(new ChessPosition(row, newCol)) == null ||
                board.getPiece(new ChessPosition(row, newCol)).getTeamColor() != color)) {
            rookMove();
            if (board.getPiece(new ChessPosition(row, newCol)) != null &&
                    board.getPiece(new ChessPosition(row, newCol)).getTeamColor() != color) {
                break;
            }

            newCol--;
        }

        return rookList;
    }

    private void rookMove() {
        rookList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol), null));
    }
}

