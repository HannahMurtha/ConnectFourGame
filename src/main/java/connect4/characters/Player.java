package connect4.characters;

/**
 * Represents a human player in Connect Four game
 * Always plays on an 7x7 board
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
                ", boardSize=7x7" +
                ", piece=" + piece +
                '}';
    }
}