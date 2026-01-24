package chess;

import java.util.ArrayList;

public class PieceMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece piece = board.getPiece(myPosition);
        if(piece.getPieceType() == ChessPiece.PieceType.KING) {
            KingMovesCalculator moveList = new KingMovesCalculator();
            return moveList.pieceMoves(board, myPosition);

        } else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN) {
            QueenMovesCalculator moveList = new QueenMovesCalculator();
            return moveList.pieceMoves(board, myPosition);

        } else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP) {
            BishopMovesCalculator moveList = new BishopMovesCalculator();
            return moveList.pieceMoves(board, myPosition);

        } else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT) {
            KnightMovesCalculator moveList = new KnightMovesCalculator();
            return moveList.pieceMoves(board, myPosition);

        } else if(piece.getPieceType() == ChessPiece.PieceType.ROOK) {
            RookMovesCalculator moveList = new RookMovesCalculator();
            return moveList.pieceMoves(board, myPosition);

        } else if(piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            PawnMovesCalculator moveList = new PawnMovesCalculator();
            return moveList.pieceMoves(board, myPosition);
        }

        return new ArrayList<>();
    }
}
