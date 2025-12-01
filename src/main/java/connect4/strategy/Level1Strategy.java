package connect4.strategy;

/**
 * Level 1: Dumb AI - Always places on leftmost available column
 */
public class Level1Strategy implements IOpponentStrategy {

    private final char[][] board;
    private final int cols;

    public Level1Strategy(char[][] board, int cols) {
        this.board = board;
        this.cols = cols;
    }

    @Override
    public int chooseColumn() {
        // Place on the leftmost available column
        for (int col = 0; col < cols; col++) {
            if (isColumnAvailable(col)) {
                return col;
            }
        }
        return -1; // Board is full
    }

    private boolean isColumnAvailable(int col) {
        return col >= 0 && col < cols && board[0][col] == ' ';
    }

    @Override
    public String getStrategyName() {
        return "Level 1 - Leftmost";
    }
}