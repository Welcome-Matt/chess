package chess;

import java.util.ArrayList;

public class KnightMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> knightMoves = new ArrayList<>();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();

        int newRow = currentRow + 1;
        int newCol = currentCol + 2;
        if (newRow < 9 && newCol < 9) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newRow = currentRow - 1;
        if (newRow > 0 && newCol < 9) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newRow = currentRow + 1;
        newCol = currentCol - 2;
        if (newRow < 9 && newCol > 0) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newRow = currentRow - 1;
        if (newRow > 0 && newCol > 0) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newRow = currentRow + 2;
        newCol = currentCol - 1;
        if (newRow < 9 && newCol > 0) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newCol = currentCol + 1;
        if (newRow < 9 && newCol < 9) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newRow = currentRow - 2;
        if (newRow > 0 && newCol < 9) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        newCol = currentCol - 1;
        if (newRow > 0 && newCol > 0) {
            if (board.getPiece(new ChessPosition(newRow, newCol)) == null ||
                    board.getPiece(new ChessPosition(newRow, newCol)).getTeamColor() != pieceColor) {
                knightMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(newRow, newCol), null));
            }
        }

        return knightMoves;
    }
}
