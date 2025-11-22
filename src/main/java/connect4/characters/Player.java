package connect4.characters;

/**
 * Represents a human player in Connect Four game
 * Always plays on an 8x8 board
 */
public class Player implements Character {
    private final String name;
    private final char piece; // Player's game piece ('X')

    public Player(String name, char piece) {
        this.name = name;
        this.piece = piece;
    }

    // getters
    public String getName() {
        return name;
    }

    public char getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", boardSize=8x8" +
                ", piece=" + piece +
                '}';
    }
}