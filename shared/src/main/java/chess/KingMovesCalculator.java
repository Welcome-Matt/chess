package chess;

import java.util.ArrayList;

public class KingMovesCalculator {

    ArrayList<ChessMove> kingList;
    int row;
    int col;
    int newRow;
    int newCol;
    ChessGame.TeamColor color;
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        row = myPosition.getRow();
        col = myPosition.getColumn();
        color = board.getPiece(myPosition).getTeamColor();
        kingList = new ArrayList<>();

        if (((row + 1) < 9) && (board.getPiece(new ChessPosition(row + 1, col)) == null ||
                board.getPiece(new ChessPosition(row + 1, col)).getTeamColor() != color)) {
            newRow = row + 1;
            newCol = col;
            kingMove();
        }

        if (((row - 1) > 0) && (board.getPiece(new ChessPosition(row - 1, col)) == null ||
                board.getPiece(new ChessPosition(row - 1, col)).getTeamColor() != color)) {
            newRow = row - 1;
            newCol = col;
            kingMove();
        }

        if (((col - 1) > 0) && (board.getPiece(new ChessPosition(row, col - 1)) == null ||
                board.getPiece(new ChessPosition(row, col - 1)).getTeamColor() != color)) {
            newRow = row;
            newCol = col - 1;
            kingMove();
        }

        if (((col + 1) < 9) && (board.getPiece(new ChessPosition(row, col + 1)) == null ||
                board.getPiece(new ChessPosition(row, col + 1)).getTeamColor() != color)) {
            newRow = row;
            newCol = col + 1;
            kingMove();
        }

        if (((col + 1) < 9 && (row + 1) < 9) && (board.getPiece(new ChessPosition(row + 1, col + 1)) == null ||
                board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() != color)) {
            newRow = row + 1;
            newCol = col + 1;
            kingMove();
        }

        if (((col + 1) < 9 && (row - 1) > 0) && (board.getPiece(new ChessPosition(row - 1, col + 1)) == null ||
                board.getPiece(new ChessPosition(row - 1, col + 1)).getTeamColor() != color)) {
            newRow = row - 1;
            newCol = col + 1;
            kingMove();
        }

        if (((col - 1) > 0 && (row - 1) > 0) && (board.getPiece(new ChessPosition(row - 1, col - 1)) == null ||
                board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() != color)) {
            newRow = row - 1;
            newCol = col - 1;
            kingMove();
        }

        if (((col - 1) > 0 && (row + 1) < 9) && (board.getPiece(new ChessPosition(row + 1, col - 1)) == null ||
                board.getPiece(new ChessPosition(row + 1, col - 1)).getTeamColor() != color)) {
            newRow = row + 1;
            newCol = col - 1;
            kingMove();
        }

        return kingList;
    }

    private void kingMove() {
        kingList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol), null));
    }
}