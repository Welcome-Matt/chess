package chess;

import java.util.ArrayList;

public class PawnMovesCalculator {

    ArrayList<ChessMove> pawnList;
    int row;
    int col;
    int newRow;
    int newCol;
    ChessGame.TeamColor color;

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        row = myPosition.getRow();
        col = myPosition.getColumn();
        color = board.getPiece(myPosition).getTeamColor();
        pawnList = new ArrayList<>();

        if (color == ChessGame.TeamColor.WHITE) {
            if (row == 2 && board.getPiece(new ChessPosition(row + 1, col)) == null &&
                    board.getPiece(new ChessPosition(row + 2, col)) == null) {
                pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(row + 2, col), null));
            }

            if (((row + 1) < 9) && board.getPiece(new ChessPosition(row + 1, col)) == null) {
                newRow = row + 1;
                newCol = col;

                if (row == 7) {
                    promMove();
                } else {
                    pawnMove();
                }
            }

            if (((row + 1) < 9 && (col + 1) < 9) &&
                    board.getPiece(new ChessPosition(row + 1, col + 1)) != null &&
                    board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() != color) {
                newRow = row + 1;
                newCol = col + 1;

                if (row == 7) {
                    promMove();
                } else {
                    pawnMove();
                }
            }

            if (((row + 1) < 9 && (col - 1) > 0) &&
                    board.getPiece(new ChessPosition(row + 1, col - 1)) != null &&
                    board.getPiece(new ChessPosition(row + 1, col - 1)).getTeamColor() != color) {
                newRow = row + 1;
                newCol = col - 1;

                if (row == 7) {
                    promMove();
                } else {
                    pawnMove();
                }
            }

        } else {
            if (row == 7 && board.getPiece(new ChessPosition(row - 1, col)) == null &&
                    board.getPiece(new ChessPosition(row - 2, col)) == null) {
                pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(row - 2, col), null));
            }

            if (((row - 1) > 0) && board.getPiece(new ChessPosition(row - 1, col)) == null) {
                newRow = row - 1;
                newCol = col;

                if (row == 2) {
                    promMove();
                } else {
                    pawnMove();
                }
            }

            if (((row - 1) > 0 && (col - 1) > 0) &&
                    board.getPiece(new ChessPosition(row - 1, col - 1)) != null &&
                    board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() != color) {
                newRow = row - 1;
                newCol = col - 1;

                if (row == 2) {
                    promMove();
                } else {
                    pawnMove();
                }
            }

            if (((row - 1) > 0 && (col + 1) < 9) &&
                    board.getPiece(new ChessPosition(row - 1, col + 1)) != null &&
                    board.getPiece(new ChessPosition(row - 1, col + 1)).getTeamColor() != color) {
                newRow = row - 1;
                newCol = col + 1;

                if (row == 2) {
                    promMove();
                } else {
                    pawnMove();
                }
            }
        }

        return pawnList;
    }

    private void promMove() {
        pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol),
                ChessPiece.PieceType.QUEEN));
        pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol),
                ChessPiece.PieceType.ROOK));
        pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol),
                ChessPiece.PieceType.KNIGHT));
        pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol),
                ChessPiece.PieceType.BISHOP));
    }

    private void pawnMove() {
        pawnList.add(new ChessMove(new ChessPosition(row, col), new ChessPosition(newRow, newCol), null));
    }
}
