package chess;

import java.util.ArrayList;

public class RookMovesCalculator {

    public ArrayList<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> rookMoves = new ArrayList<ChessMove>();
        rookMoves.add(new ChessMove(new ChessPosition(2,3), new ChessPosition(2,4), null));
        return rookMoves;
    }
}
