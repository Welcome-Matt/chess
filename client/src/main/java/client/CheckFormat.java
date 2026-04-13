package client;

public class CheckFormat {

    boolean bool = false;

    public void checkFormatMove (String[] move) {
        if (move.length >= 1 && move.length <= 2 && move[0].length() == 4) {
            if (Character.isAlphabetic(move[0].charAt(0)) &&
                    Character.isDigit(move[0].charAt(1)) &&
                    Character.isAlphabetic(move[0].charAt(2)) &&
                    Character.isDigit(move[0].charAt(3))) {
                bool = true;
            } else {
                System.out.println("Invalid \"move\" format!");
            }
        } else {
            System.out.println("Invalid \"move\" format!");
        }
    }

    public void checkFormatSmall (String[] position) {
        if (position.length == 1 && position[0].length() == 2) {
            if (Character.isAlphabetic(position[0].charAt(0)) &&
                    Character.isDigit(position[0].charAt(1))) {
                bool = true;
            } else {
                System.out.println("Invalid \"highlight\" format!");
            }
        } else {
            System.out.println("Invalid \"highlight\" format!");
        }
    }

    public boolean getBool() {
        return bool;
    }
}
