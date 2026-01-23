package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {

    public ArrayList<ChessMove> piece(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece  = board.getPiece(myPosition);
        if(piece.getPieceType() == ChessPiece.PieceType.KING) {
            //return;
        } else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            //return;
        } else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            //return;
        } else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            KnightMovesCalculator moveList = new KnightMovesCalculator();
            return moveList.moveCalculator(board, myPosition);
        } else if(piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            //return rookMovesCalc(myPosition);
        } else if(piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            //return;
        }

        return new ArrayList<ChessMove>();
    }

    ArrayList<ChessMove> rookMovesCalc(ChessPosition myPosition) {
        ArrayList<ChessMove> rookMoves = new ArrayList<ChessMove>();
        rookMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return rookMoves;
    }
}
