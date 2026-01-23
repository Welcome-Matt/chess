package chess;

import java.util.ArrayList;

public class KingMovesCalculator {
    
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> kingMoves = new ArrayList<ChessMove>();
        kingMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return kingMoves;
    }
}
