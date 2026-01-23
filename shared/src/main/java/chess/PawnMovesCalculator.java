package chess;

import java.util.ArrayList;

public class PawnMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> pawnMoves = new ArrayList<ChessMove>();
        pawnMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return pawnMoves;
    }
}
