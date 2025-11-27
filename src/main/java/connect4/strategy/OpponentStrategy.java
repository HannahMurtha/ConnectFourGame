package connect4.strategy;

import java.util.Random;

/**
 * Strategy for AI opponent moves
 * Level 1: Place on leftmost available column
 * Level 2: Random placement
 * Level 3: Defensive - blocks player if they have 2 or 3 in a row
 */
public class OpponentStrategy {

    private final char[][] board;
    private final int rows;
    private final int cols;
    private final int difficulty;
    private final char opponentPiece;
    private final char playerPiece;
    private final Random random;

    public OpponentStrategy(char[][] board, int rows, int cols, int difficulty,
                            char opponentPiece, char playerPiece) {
        this.board = board;
        this.rows = rows;
        this.cols = cols;
        this.difficulty = difficulty;
        this.opponentPiece = opponentPiece;
        this.playerPiece = playerPiece;
        this.random = new Random();
    }

    /**
     * Choose a column based on difficulty level
     */
    public int chooseColumn() {
        switch (difficulty) {
            case 1:
                return leftmostMove();
            case 2:
                return randomMove();
            case 3:
                return defensiveMove();
            default:
                return randomMove();
        }
    }

    /**
     * Level 1: Place on the leftmost available column
     */
    private int leftmostMove() {
        for (int col = 0; col < cols; col++) {
            if (isColumnAvailable(col)) {
                return col;
            }
        }
        return -1; // Board is full
    }

    /**
     * Level 2: Random placement
     */
    private int randomMove() {
        int attempts = 0;
        while (attempts < cols * 2) {
            int col = random.nextInt(cols);
            if (isColumnAvailable(col)) {
                return col;
            }
            attempts++;
        }
        // Fallback to leftmost if random fails
        return leftmostMove();
    }

    /**
     * Level 3: Defensive play - block player if they have 2 or 3 in a row
     */
    private int defensiveMove() {
        // First, check if we can block a winning move (3 in a row)
        int blockCol = findBlockingMove(3);
        if (blockCol != -1) {
            System.out.println("Blocking potential win at column " + (blockCol + 1));
            return blockCol;
        }

        // Next, check if we can block 2 in a row
        blockCol = findBlockingMove(2);
        if (blockCol != -1) {
            System.out.println("Blocking 2 in a row at column " + (blockCol + 1));
            return blockCol;
        }

        // Otherwise, make a random move
        return randomMove();
    }

    /**
     * Find a column that would block the player from getting 'count' in a row
     */
    private int findBlockingMove(int count) {
        // Check each column
        for (int col = 0; col < cols; col++) {
            if (!isColumnAvailable(col)) continue;

            int row = getNextAvailableRow(col);
            if (row == -1) continue;

            // Temporarily place opponent piece
            board[row][col] = opponentPiece;

            // Check if this blocks player's threat
            boolean blocksPlayer = checkThreat(row, col, playerPiece, count);

            // Remove temporary piece
            board[row][col] = ' ';

            if (blocksPlayer) {
                return col;
            }
        }
        return -1;
    }

    /**
     * Check if placing a piece at (row, col) would block a threat
     */
    private boolean checkThreat(int row, int col, char piece, int count) {
        // Check horizontal threat
        if (countInDirection(row, col, 0, 1, piece) +
                countInDirection(row, col, 0, -1, piece) >= count - 1) {
            return true;
        }

        // Check vertical threat
        if (countInDirection(row, col, 1, 0, piece) +
                countInDirection(row, col, -1, 0, piece) >= count - 1) {
            return true;
        }

        // Check diagonal (top-left to bottom-right)
        if (countInDirection(row, col, 1, 1, piece) +
                countInDirection(row, col, -1, -1, piece) >= count - 1) {
            return true;
        }

        // Check diagonal (bottom-left to top-right)
        if (countInDirection(row, col, -1, 1, piece) +
                countInDirection(row, col, 1, -1, piece) >= count - 1) {
            return true;
        }

        return false;
    }

    /**
     * Count consecutive pieces in a given direction
     */
    private int countInDirection(int row, int col, int rowDir, int colDir, char piece) {
        int count = 0;
        int r = row + rowDir;
        int c = col + colDir;

        while (r >= 0 && r < rows && c >= 0 && c < cols && board[r][c] == piece) {
            count++;
            r += rowDir;
            c += colDir;
        }

        return count;
    }

    /**
     * Check if a column has available space
     */
    private boolean isColumnAvailable(int col) {
        return col >= 0 && col < cols && board[0][col] == ' ';
    }

    /**
     * Get the next available row in a column (where piece would drop)
     */
    private int getNextAvailableRow(int col) {
        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][col] == ' ') {
                return row;
            }
        }
        return -1;
    }
}