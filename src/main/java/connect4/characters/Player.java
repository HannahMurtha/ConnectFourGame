package connect4.characters;

/**
 * Human player in Connect Four game
 * Contains player information and board configuration
 */
public class Player {
    private final String name;
    private final int rows;
    private final int cols;
    private final char piece; // Player's game piece ('X')

    public Player(String name, int rows, int cols, char piece) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.piece = piece;
    }

    // Getters
    public String getName() {
        return name;
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
        return "Player{" +
                "name='" + name + '\'' +
                ", boardSize=" + rows + "x" + cols +
                ", piece=" + piece +
                '}';
    }
}