package chess;

import java.util.ArrayList;

public class RookMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> rookMoves = new ArrayList<ChessMove>();

        int rookRow = myPosition.getRow() + 1;
        int rookCol = myPosition.getColumn();
        while (rookRow < 9) {
            if (board.getPiece(new ChessPosition(rookRow, rookCol)) == null) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                rookRow++;
            } else if (board.getPiece(new ChessPosition(rookRow, rookCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                break;
            } else {
                break;
            }
        }

        rookRow = myPosition.getRow() - 1;
        while (rookRow > 0) {
            if (board.getPiece(new ChessPosition(rookRow, rookCol)) == null) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                rookRow--;
            } else if (board.getPiece(new ChessPosition(rookRow, rookCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                break;
            } else {
                break;
            }
        }

        rookRow = myPosition.getRow();
        rookCol = myPosition.getColumn() + 1;
        while (rookCol < 9) {
            if (board.getPiece(new ChessPosition(rookRow, rookCol)) == null) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                rookCol++;
            } else if (board.getPiece(new ChessPosition(rookRow, rookCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                break;
            } else {
                break;
            }
        }

        rookCol = myPosition.getColumn() - 1;
        while (rookCol > 0) {
            if (board.getPiece(new ChessPosition(rookRow, rookCol)) == null) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                rookCol--;
            } else if (board.getPiece(new ChessPosition(rookRow, rookCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                rookMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(rookRow, rookCol), null));
                break;
            } else {
                break;
            }
        }

        return rookMoves;
    }
}
