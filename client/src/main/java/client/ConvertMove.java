package client;

import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;

public class ConvertMove {

    public ChessMove main(String[] move, String color) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i < move[0].length(); i++) {
            char c = move[0].charAt(i);
            if (color.equals("WHITE")) {
                if (Character.isDigit(c)) {
                    positions.add(convertNum(c));
                } else {
                    positions.add(convertChar(c));
                }
            } else {
                if (Character.isDigit(c)) {
                    positions.add(convertNum(c));
                } else {
                    positions.add(convertChar(c));
                }
            }
        }

        ChessPiece.PieceType type;
        if (move.length == 2) {
            type = convertType(move[1]);
        } else {
            type = null;
        }

        return new ChessMove(new ChessPosition(positions.get(1), positions.get(0)),
                new ChessPosition(positions.get(3), positions.get(2)), type);
    }

    public ChessPosition highLightPiece(String position) {
        return new ChessPosition(convertNum(position.charAt(1)), convertChar(position.charAt(0)));
    }

    private ChessPiece.PieceType convertType(String type) {
        type = type.toUpperCase();
        return switch (type) {
            case "ROOK" -> ChessPiece.PieceType.ROOK;
            case "QUEEN" -> ChessPiece.PieceType.QUEEN;
            case "BISHOP" -> ChessPiece.PieceType.BISHOP;
            case "KNIGHT" -> ChessPiece.PieceType.KNIGHT;
            default -> ChessPiece.PieceType.KING;
        };
    }

    private int convertChar(char character) {
        return switch (character) {
            case 'a' -> 1;
            case 'b' -> 2;
            case 'c' -> 3;
            case 'd' -> 4;
            case 'e' -> 5;
            case 'f' -> 6;
            case 'g' -> 7;
            case 'h' -> 8;
            default -> 10;
        };
    }

    private int convertCharRev(char character) {
        return switch (character) {
            case 'a' -> 8;
            case 'b' -> 7;
            case 'c' -> 6;
            case 'd' -> 5;
            case 'e' -> 4;
            case 'f' -> 3;
            case 'g' -> 2;
            case 'h' -> 1;
            default -> 10;
        };
    }

    private int convertNumRev(char num) {
        return switch (num) {
            case '1' -> 8;
            case '2' -> 7;
            case '3' -> 6;
            case '4' -> 5;
            case '5' -> 4;
            case '6' -> 3;
            case '7' -> 2;
            case '8' -> 1;
            default -> 10;
        };
    }

    private int convertNum(char num) {
        return switch (num) {
            case '1' -> 1;
            case '2' -> 2;
            case '3' -> 3;
            case '4' -> 4;
            case '5' -> 5;
            case '6' -> 6;
            case '7' -> 7;
            case '8' -> 8;
            default -> 10;
        };
    }
}
