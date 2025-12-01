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

    // 1 - leftmost available option
    private int leftmostMove() {
        for (int col = 0; col < cols; col++) {
            if (isColumnAvailable(col)) {
                return col;
            }
        }
        return -1; // Board is full
    }

    // 2 - random placement
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

    // -------------- ASSISTED BY AI AND THE INTERNET FOR LOGIC HERE!!! ------------------

    // 3 - defensive placement
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

    // find a col that would block the player from getting 4 in a row
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

    // does it block a threat
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

    // counts consecutive pieces in a given direction
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


    // do it have availible space
    private boolean isColumnAvailable(int col) {
        return col >= 0 && col < cols && board[0][col] == ' ';
    }

    // next availiable row in a col (where piece would drop)
    private int getNextAvailableRow(int col) {
        for (int row = rows - 1; row >= 0; row--) {
            if (board[row][col] == ' ') {
                return row;
            }
        }
        return -1;
    }

    // -------------- ASSISTED BY AI AND THE INTERNET FOR LOGIC ENDS HERE!!! ------------------
}