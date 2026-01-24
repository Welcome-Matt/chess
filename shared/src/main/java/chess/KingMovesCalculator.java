package chess;

import java.util.ArrayList;

public class KingMovesCalculator {
    
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> kingMoves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        int newRow = currentRow + 1;
        if (newRow < 9) {
            if (board.getPiece(new ChessPosition(newRow, currentCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, currentCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, currentCol), null));
            }
        }

        int newCol = currentCol + 1;
        if (newRow < 9 && newCol < 9) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }

            if (board.getPiece(new ChessPosition(currentRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(currentRow, newCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow, newCol), null));
            }
        }

        newCol = currentCol - 1;
        if (newRow < 9 && newCol > 0) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }

            if (board.getPiece(new ChessPosition(currentRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(currentRow, newCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow, newCol), null));
            }
        }

        newRow = currentRow - 1;
        if (newRow > 0) {
            if (board.getPiece(new ChessPosition(newRow, currentCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, currentCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, currentCol), null));
            }
        }

        newCol = currentCol + 1;
        if (newRow > 0 && newCol < 9) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newCol = currentCol - 1;
        if (newRow > 0 && newCol > 0) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                kingMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        return kingMoves;
    }
}
