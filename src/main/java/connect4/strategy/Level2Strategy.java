package connect4.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Level 2 AI: Places piece in a random available column
 */
public class Level2Strategy implements OpponentStrategy {
    private Random random;

    public Level2Strategy() {
        this.random = new Random();
    }

    @Override
    public int chooseColumn(char[][] board) {
        int cols = board[0].length;
        List<Integer> availableColumns = new ArrayList<>();

        // Find all available columns
        for (int col = 0; col < cols; col++) {
            if (board[0][col] == '*') {
                availableColumns.add(col);
            }
        }

        // Return random available column
        if (!availableColumns.isEmpty()) {
            return availableColumns.get(random.nextInt(availableColumns.size()));
        }

        // No columns available (board full)
        return -1;
    }
}