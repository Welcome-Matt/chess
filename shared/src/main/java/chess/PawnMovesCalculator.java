package chess;

import java.util.ArrayList;

public class PawnMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> pawnMoves = new ArrayList<>();
        ChessGame.TeamColor pieceColor = board.getPiece(myPosition).getTeamColor();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        if (pieceColor == ChessGame.TeamColor.WHITE && currentRow != 8) {
            if (currentRow == 2 &&
                    board.getPiece(new ChessPosition(currentRow + 1, currentCol)) == null &&
                    board.getPiece(new ChessPosition(currentRow + 2, currentCol)) == null) {
                pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 2, currentCol), null));
            }

            if (board.getPiece(new ChessPosition(currentRow + 1, currentCol)) == null) {
                if (currentRow == 7) {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol), ChessPiece.PieceType.QUEEN));
                } else {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol), null));
                }
            }

            if (currentCol + 1 < 9 &&
                    board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1)) != null &&
                    board.getPiece(new ChessPosition(currentRow + 1, currentCol + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (currentRow == 7) {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol + 1), ChessPiece.PieceType.QUEEN));
                } else {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol + 1), null));
                }
            }

            if (currentCol - 1 > 0 &&
                    board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1)) != null &&
                    board.getPiece(new ChessPosition(currentRow + 1, currentCol - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (currentRow == 7) {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol - 1), ChessPiece.PieceType.QUEEN));
                } else {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow + 1, currentCol - 1), null));
                }
            }
        }

        if (pieceColor == ChessGame.TeamColor.BLACK && currentRow != 1) {
            if (currentRow == 7 &&
                    board.getPiece(new ChessPosition(currentRow - 1, currentCol)) == null &&
                    board.getPiece(new ChessPosition(currentRow - 2, currentCol)) == null) {
                pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 2, currentCol), null));
            }

            if (board.getPiece(new ChessPosition(currentRow - 1, currentCol)) == null) {
                if (currentRow == 2) {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol), ChessPiece.PieceType.QUEEN));
                } else {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol), null));
                }
            }

            if (currentCol - 1 > 0 &&
                    board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1)) != null &&
                    board.getPiece(new ChessPosition(currentRow - 1, currentCol - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (currentRow == 2) {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol - 1), ChessPiece.PieceType.QUEEN));
                } else {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol - 1), null));
                }
            }

            if (currentCol + 1 < 9 &&
                    board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1)) != null &&
                    board.getPiece(new ChessPosition(currentRow - 1, currentCol + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (currentRow == 2) {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.BISHOP));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.ROOK));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.KNIGHT));
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol + 1), ChessPiece.PieceType.QUEEN));
                } else {
                    pawnMoves.add(new ChessMove(new ChessPosition(currentRow, currentCol), new ChessPosition(currentRow - 1, currentCol + 1), null));
                }
            }
        }

        return pawnMoves;
    }
}
