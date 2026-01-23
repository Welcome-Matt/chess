package chess;

import java.util.ArrayList;

public class KnightMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> knightMoves = new ArrayList<ChessMove>();
        knightMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return knightMoves;
    }
}
