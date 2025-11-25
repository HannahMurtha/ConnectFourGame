package connect4.strategy;

/**
 * Checks for 4 pieces vertically in a column
 */
public class VerticalWinStrategy implements WinStrategy {

    @Override
    public boolean checkWin(char[][] board, char piece) {
        int rows = board.length;
        int cols = board[0].length;

        // Check each column
        for (int col = 0; col < cols; col++) {
            int count = 0;
            for (int row = 0; row < rows; row++) {
                if (board[row][col] == piece) {
                    count++;
                    if (count >= 4) {
                        return true;
                    }
                } else {
                    count = 0;
                }
            }
        }
        return false;
    }

    @Override
    public String getWinType() {
        return "Vertical";
    }
}