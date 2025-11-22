package connect4.strategy;

/**
 * Level 1 AI: Places piece in the leftmost available column
 */
public class Level1Strategy implements OpponentStrategy {

    @Override
    public int chooseColumn(char[][] board) {
        int cols = board[0].length;

        // Find leftmost available column
        for (int col = 0; col < cols; col++) {
            if (board[0][col] == '*') {
                return col;
            }
        }

        // No columns available (board full)
        return -1;
    }
}