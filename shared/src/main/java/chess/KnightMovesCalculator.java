package chess;

import java.util.ArrayList;

public class KnightMovesCalculator {
    int currentRow;
    int newRow;
    int currentCol;
    int newCol;
    ArrayList<ChessMove> knightMoves;
    ChessBoard board;
    ChessGame.TeamColor pieceColor;

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        this.knightMoves = new ArrayList<>();
        this.board = board;
        this.currentRow = myPosition.getRow();
        this.currentCol = myPosition.getColumn();
        this.pieceColor = board.getPiece(myPosition).getTeamColor();

        this.newRow = currentRow + 1;
        this.newCol = currentCol + 2;
        if (newRow < 9 && newCol < 9) {
            checkNAdd();
        }

        newRow = currentRow - 1;
        if (newRow > 0 && newCol < 9) {
            checkNAdd();
        }

        newRow = currentRow + 1;
        newCol = currentCol - 2;
        if (newRow < 9 && newCol > 0) {
            checkNAdd();
        }

        newRow = currentRow - 1;
        if (newRow > 0 && newCol > 0) {
            checkNAdd();
        }

        newRow = currentRow + 2;
        newCol = currentCol - 1;
        if (newRow < 9 && newCol > 0) {
            checkNAdd();
        }

        newCol = currentCol + 1;
        if (newRow < 9 && newCol < 9) {
            checkNAdd();
        }

        newRow = currentRow - 2;
        if (newRow > 0 && newCol < 9) {
            checkNAdd();
        }

        newCol = currentCol - 1;
        if (newRow > 0 && newCol > 0) {
            checkNAdd();
        }

        return knightMoves;
    }

    private void checkNAdd() {
        if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
            knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
        }
    }
}
