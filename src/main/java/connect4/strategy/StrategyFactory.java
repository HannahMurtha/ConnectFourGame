package connect4.strategy;

/**
 * Factory for creating opponent strategies based on difficulty
 */
public class StrategyFactory {

    public static IOpponentStrategy createStrategy(int difficulty, char[][] board,
                                                   int rows, int cols,
                                                   char opponentPiece, char playerPiece) {
        switch (difficulty) {
            case 1:
                return new Level1Strategy(board, cols);
            case 2:
                return new Level2Strategy(board, cols);
            case 3:
                return new Level3Strategy(board, rows, cols, opponentPiece, playerPiece);
            default:
                return new Level2Strategy(board, cols); // Default to random
        }
    }
}
