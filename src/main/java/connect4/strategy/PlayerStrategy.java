package connect4.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages all win strategies and checks if a player has won
 */
public class PlayerStrategy {
    private List<WinStrategy> strategies;

    public PlayerStrategy() {
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
}