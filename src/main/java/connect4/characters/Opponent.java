package connect4.characters;

/**
 * Represents an AI opponent in Connect Four game
 * Contains opponent information, difficulty level, and board configuration.
 */
public class Opponent {
    private final String name;
    private final int difficulty; // 1 = dumb, 2 = random, 3 = defensive
    private final int rows;
    private final int cols;
    private final char piece; // Opponent's game piece ('O')

    public Opponent(String name, int difficulty, int rows, int cols, char piece) {
        this.name = name;
        this.difficulty = difficulty;
        this.rows = rows;
        this.cols = cols;
        this.piece = piece;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "Opponent{" +
                "name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", boardSize=" + rows + "x" + cols +
                ", piece=" + piece +
                '}';
    }
}