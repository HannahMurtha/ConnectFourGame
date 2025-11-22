package connect4.characters;

/**
 * Represents an AI opponent in Connect Four game
 * Always plays on an 8x8 board with piece 'O'
 */
public class Opponent implements Character {
    private final String name;
    private final int difficulty; // 1 = leftmost, 2 = random, 3 = defensive
    private final char piece = 'O'; // Opponent always uses 'O'

    public Opponent(String name, int difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    // getters
    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public char getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "Opponent{" +
                "name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", boardSize=8x8" +
                ", piece=" + piece +
                '}';
    }
}