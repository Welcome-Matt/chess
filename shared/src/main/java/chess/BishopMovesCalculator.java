package chess;

import java.util.ArrayList;

public class BishopMovesCalculator {
    
    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        bishopMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return bishopMoves;
    }
}
