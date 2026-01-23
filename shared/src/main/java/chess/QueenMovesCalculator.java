package chess;

import java.util.ArrayList;

public class QueenMovesCalculator {
    
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> queenMoves = new ArrayList<ChessMove>();
        queenMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return queenMoves;
    }
}
