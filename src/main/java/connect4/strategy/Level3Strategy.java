// Level3Strategy.java
package connect4.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Level 3 AI: Defensive strategy - blocks opponent's 2 or 3 in a row
 * Falls back to random placement if no threat detected
 */
public class Level3Strategy implements OpponentStrategy {
    private Random random;

    public Level3Strategy() {
        this.random = new Random();
    }

    @Override
    public int chooseColumn(char[][] board) {
        int rows = board.length;
        int cols = board[0].length;

        // First, check if opponent has 3 in a row and block it
        int blockColumn = findThreatenedColumn(board, 3);
        if (blockColumn != -1) {
            return blockColumn;
        }

        // Next, check if opponent has 2 in a row and block it
        blockColumn = findThreatenedColumn(board, 2);
        if (blockColumn != -1) {
            return blockColumn;
        }

        // No immediate threat, place randomly
        List<Integer> availableColumns = new ArrayList<>();
        for (int col = 0; col < cols; col++) {
            if (board[0][col] == '*') {
                availableColumns.add(col);
            }
        }

        if (!availableColumns.isEmpty()) {
            return availableColumns.get(random.nextInt(availableColumns.size()));
        }

        return -1;
    }

    /**
     * Find a column that would block opponent's consecutive pieces
     *
     * @param board            The game board
     * @param consecutiveCount Number of consecutive pieces to look for (2 or 3)
     * @return Column to block, or -1 if no threat found
     */
    private int findThreatenedColumn(char[][] board, int consecutiveCount) {
        int rows = board.length;
        int cols = board[0].length;

        // Check horizontal threats
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                int count = 0;
                int emptyCol = -1;

                for (int i = 0; i < 4; i++) {
                    if (board[row][col + i] == 'X') {
                        count++;
                    } else if (board[row][col + i] == '*') {
                        // Check if we can place here (not floating)
                        if (row == rows - 1 || board[row + 1][col + i] != '*') {
                            emptyCol = col + i;
                        }
                    }
                }

                if (count == consecutiveCount && emptyCol != -1) {
                    return emptyCol;
                }
            }
        }

        // Check vertical threats
        for (int col = 0; col < cols; col++) {
            for (int row = 0; row <= rows - 4; row++) {
                int count = 0;
                int emptyRow = -1;

                for (int i = 0; i < 4; i++) {
                    if (board[row + i][col] == 'X') {
                        count++;
                    } else if (board[row + i][col] == '*') {
                        emptyRow = row + i;
                    }
                }

                // For vertical, block at the lowest empty spot in the threat
                if (count == consecutiveCount && emptyRow != -1) {
                    // Make sure it's the lowest empty spot
                    if (emptyRow == rows - 1 || board[emptyRow + 1][col] != '*') {
                        return col;
                    }
                }
            }
        }

        // Check diagonal threats (down-right \)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= cols - 4; col++) {
                int count = 0;
                int emptyCol = -1;

                for (int i = 0; i < 4; i++) {
                    if (board[row + i][col + i] == 'X') {
                        count++;
                    } else if (board[row + i][col + i] == '*') {
                        if (row + i == rows - 1 || board[row + i + 1][col + i] != '*') {
                            emptyCol = col + i;
                        }
                    }
                }

                if (count == consecutiveCount && emptyCol != -1) {
                    return emptyCol;
                }
            }
        }

        // Check diagonal threats (down-left /)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 3; col < cols; col++) {
                int count = 0;
                int emptyCol = -1;

                for (int i = 0; i < 4; i++) {
                    if (board[row + i][col - i] == 'X') {
                        count++;
                    } else if (board[row + i][col - i] == '*') {
                        if (row + i == rows - 1 || board[row + i + 1][col - i] != '*') {
                            emptyCol = col - i;
                        }
                    }
                }

                if (count == consecutiveCount && emptyCol != -1) {
                    return emptyCol;
                }
            }
        }

        return -1;
    }
}