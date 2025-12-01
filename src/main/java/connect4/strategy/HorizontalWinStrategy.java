package connect4.strategy;

/**
 * Checks for 4 pieces horizontally in a row
 */
public class HorizontalWinStrategy implements WinStrategy {

    @Override
    public boolean checkWin(char[][] board, char piece) {
        int rows = board.length;
        int cols = board[0].length;

        // check each row - AI assisted
        for (int row = 0; row < rows; row++) {
            int count = 0;
            for (int col = 0; col < cols; col++) {
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
        return "Horizontal";
    }
}