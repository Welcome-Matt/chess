package chess;

import java.util.ArrayList;

public class QueenMovesCalculator {
    
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        RookMovesCalculator cardinalMoves = new RookMovesCalculator();
        BishopMovesCalculator ordinalMoves = new BishopMovesCalculator();
        ArrayList<ChessMove> queenMoves = cardinalMoves.pieceMoves(board, myPosition);
        ArrayList<ChessMove> queenMoves2 = ordinalMoves.pieceMoves(board, myPosition);
        queenMoves.addAll(queenMoves2);
        return queenMoves;
    }
}
