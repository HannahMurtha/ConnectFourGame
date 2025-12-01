package connect4.strategy;

import java.util.Random;

/**
 * Level 2: Random AI - Places pieces randomly
 */
public class Level2Strategy implements IOpponentStrategy {

    private final char[][] board;
    private final int cols;
    private final Random random;

    public Level2Strategy(char[][] board, int cols) {
        this.board = board;
        this.cols = cols;
        this.random = new Random();
    }

    @Override
    public int chooseColumn() {
        int attempts = 0;
        while (attempts < cols * 2) {
            int col = random.nextInt(cols);
            if (isColumnAvailable(col)) {
                return col;
            }
            attempts++;
        }

        // Fallback to leftmost if random fails
        for (int col = 0; col < cols; col++) {
            if (isColumnAvailable(col)) {
                return col;
            }
        }
        return -1;
    }

    private boolean isColumnAvailable(int col) {
        return col >= 0 && col < cols && board[0][col] == ' ';
    }

    @Override
    public String getStrategyName() {
        return "Level 2 - Random";
    }
}