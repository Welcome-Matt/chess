package chess;

import java.util.ArrayList;

public class BishopMovesCalculator {
    
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        int bishopRow = myPosition.getRow() + 1;
        int bishopCol = myPosition.getColumn() + 1;
        while(bishopRow < 9 && bishopCol < 9) {
            if(board.getPiece(new ChessPosition(bishopRow, bishopCol)) == null) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                bishopRow++;
                bishopCol++;
            } else if (board.getPiece(new ChessPosition(bishopRow, bishopCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                break;
            } else {
                break;
            }
        }

        bishopRow = myPosition.getRow() - 1;
        bishopCol = myPosition.getColumn() + 1;
        while(bishopRow > 0 && bishopCol < 9) {
            if(board.getPiece(new ChessPosition(bishopRow, bishopCol)) == null) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                bishopRow--;
                bishopCol++;
            } else if (board.getPiece(new ChessPosition(bishopRow, bishopCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                break;
            } else {
                break;
            }
        }

        bishopRow = myPosition.getRow() + 1;
        bishopCol = myPosition.getColumn() - 1;
        while(bishopRow < 9 && bishopCol > 0) {
            if(board.getPiece(new ChessPosition(bishopRow, bishopCol)) == null) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                bishopRow++;
                bishopCol--;
            } else if (board.getPiece(new ChessPosition(bishopRow, bishopCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                break;
            } else {
                break;
            }
        }

        bishopRow = myPosition.getRow() - 1;
        bishopCol = myPosition.getColumn() - 1;
        while(bishopRow > 0 && bishopCol > 0) {
            if(board.getPiece(new ChessPosition(bishopRow, bishopCol)) == null) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                bishopRow--;
                bishopCol--;
            } else if (board.getPiece(new ChessPosition(bishopRow, bishopCol)).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                bishopMoves.add(new ChessMove(new ChessPosition(myPosition.getRow(), myPosition.getColumn()), new ChessPosition(bishopRow, bishopCol), null));
                break;
            } else {
                break;
            }
        }

        return bishopMoves;
    }
}
