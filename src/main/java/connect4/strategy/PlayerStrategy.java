package connect4.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all win strategies and checks if a player has won
 */
public class PlayerStrategy {
    private List<WinStrategy> strategies;
    private final char[][] board;
    private final int rows;
    private final int cols;

    public PlayerStrategy(char[][] board, int rows, int cols) {
        this.board = board;
        this.rows = rows;
        this.cols = cols;
        strategies = new ArrayList<>();
        strategies.add(new VerticalWinStrategy());
        strategies.add(new HorizontalWinStrategy());
        strategies.add(new DiagonalWinStrategy());
    }

    /**
     * Check if the given piece has won using any strategy
     * @return The winning strategy if won, null otherwise
     */
    public WinStrategy checkForWin(char[][] board, char piece) {
        for (WinStrategy strategy : strategies) {
            if (strategy.checkWin(board, piece)) {
                return strategy;
            }
        }
        return null;
    }

    /**
     * Check if placing a piece at (row, col) results in a win
     * This method is called after each move
     */
    public boolean checkWin(int row, int col, char piece) {
        // Check all win strategies
        for (WinStrategy strategy : strategies) {
            if (strategy.checkWin(board, piece)) {
                return true;
            }
        }
        return false;
    }
}