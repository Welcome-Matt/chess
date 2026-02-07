package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard currentBoard = new ChessBoard();
    TeamColor currentTeam = TeamColor.WHITE;
    public ChessGame() {
        currentBoard.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTeam = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    private ChessBoard makeBoardCopy() {
        ChessBoard copyBoard = new ChessBoard();
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                ChessPiece currentBoardPiece = currentBoard.getPiece(new ChessPosition(x,y));
                if (currentBoardPiece != null) {
                    copyBoard.addPiece(new ChessPosition(x, y), new ChessPiece(currentBoardPiece.getTeamColor(), currentBoardPiece.getPieceType()));
                }
            }
        }

        return copyBoard;
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoveList = new ArrayList<>();
        Collection<ChessMove> pieceMoveList = currentBoard.getPiece(startPosition).pieceMoves(currentBoard, startPosition);
        ChessBoard copyBoard = makeBoardCopy();
        if (currentBoard.getPiece(startPosition) != null) {
            for (ChessMove move : pieceMoveList) {
                currentBoard.addPiece(move.getEndPosition(), copyBoard.getPiece(startPosition));
                currentBoard.addPiece(startPosition, null);
                if (!isInCheck(copyBoard.getPiece(startPosition).getTeamColor())) {
                    validMoveList.add(move);
                }

                currentBoard = copyBoard;
                copyBoard = makeBoardCopy();
            }

            return validMoveList;
        } else {
            return null;
        }
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        ChessPiece currentPiece;
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                currentPiece = currentBoard.getPiece(new ChessPosition(x,y));
                if (currentPiece != null && currentPiece.getPieceType() == ChessPiece.PieceType.KING &&
                        currentPiece.getTeamColor() == teamColor) {
                    kingPosition = new ChessPosition(x,y);
                }
            }
        }

        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                currentPiece = currentBoard.getPiece(new ChessPosition(x,y));
                if (currentPiece != null &&
                        currentPiece.getTeamColor() != teamColor) {
                    Collection<ChessMove> currentPieceMoves = currentPiece.pieceMoves(currentBoard, new ChessPosition(x,y));
                    for (ChessMove pos : currentPieceMoves) {
                        assert kingPosition != null;
                        if (pos.getEndPosition().getColumn() == kingPosition.getColumn() && pos.getEndPosition().getRow() == kingPosition.getRow()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        currentBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return currentBoard;
    }
}
