package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {

    public ArrayList<ChessMove> piece(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece  = board.getPiece(myPosition);
        if(piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            return rookMovesCalc(myPosition);
        }

        return rookMovesCalc(myPosition);
    }

    ArrayList<ChessMove> rookMovesCalc(ChessPosition myPosition) {
        ArrayList<ChessMove> rookMoves = new ArrayList<ChessMove>();
        rookMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return rookMoves;
    }
}
