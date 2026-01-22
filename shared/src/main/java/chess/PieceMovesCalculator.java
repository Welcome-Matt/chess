package chess;

import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator {
    ChessBoard board;
    ChessPosition myPosition;

    public PieceMovesCalculator(ChessBoard board, ChessPosition myPosition) {
        this.board = board;
        this.myPosition = myPosition;
    }
    public Collection<ChessMove> piece(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}
