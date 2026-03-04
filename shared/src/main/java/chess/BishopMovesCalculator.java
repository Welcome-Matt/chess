package chess;

import java.util.ArrayList;

public class BishopMovesCalculator {

    ArrayList<ChessMove> bishopList;
    int row;
    int col;
    int newRow;
    int newCol;
    ChessGame.TeamColor color;

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        row = myPosition.getRow();
        col = myPosition.getColumn();
        color = board.getPiece(myPosition).getTeamColor();
        bishopList = new ArrayList<>();

        newRow = row + 1;
        newCol = col + 1;
        while ((newRow < 9 && newCol < 9) && (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color)) {
            bishopMove();
            if (board.getPiece(new ChessPosition(newRow, newCol)) != null &&
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color) {
                break;
            }

            newRow++;
            newCol++;
        }

        newRow = row - 1;
        newCol = col + 1;
        while ((newRow > 0 && newCol < 9) && (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color)) {
            bishopMove();
            if (board.getPiece(new ChessPosition(newRow, newCol)) != null &&
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color) {
                break;
            }

            newRow--;
            newCol++;
        }

        newRow = row + 1;
        newCol = col - 1;
        while ((newRow < 9 && newCol > 0) && (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color)) {
            bishopMove();
            if (board.getPiece(new ChessPosition(newRow, newCol)) != null &&
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color) {
                break;
            }

            newRow++;
            newCol--;
        }

        newRow = row - 1;
        newCol = col - 1;
        while ((newRow > 0 && newCol > 0) && (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color)) {
            bishopMove();
            if (board.getPiece(new ChessPosition(newRow, newCol)) != null &&
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != color) {
                break;
            }

            newRow--;
            newCol--;
        }

        return bishopList;
    }

    private void bishopMove() {
        bishopList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol), null));
    }
}
