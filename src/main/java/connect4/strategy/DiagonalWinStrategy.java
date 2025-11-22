package connect4.strategy;

/**
 * Checks for 4 pieces diagonally (both directions)
 */
public class DiagonalWinStrategy implements WinStrategy {

    @Override
    public boolean checkWin(char[][] board, char piece) {
        int rows = board.length;
        int cols = board[0].length;

        // Check diagonal down-right (\)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                if (board[row][col] == piece &&
                        board[row+1][col+1] == piece &&
                        board[row+2][col+2] == piece &&
                        board[row+3][col+3] == piece) {
                    return true;
                }
            }
        }

        // Check diagonal down-left (/)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 3; col < cols; col++) {
                if (board[row][col] == piece &&
                        board[row+1][col-1] == piece &&
                        board[row+2][col-2] == piece &&
                        board[row+3][col-3] == piece) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String getWinType() {
        return "Diagonal";
    }
}