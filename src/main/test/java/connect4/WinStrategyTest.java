package connect4.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Win Strategy implementations
 */
class WinStrategyTest {
    private char[][] board;

    @BeforeEach
    void setUp() {
        board = createEmptyBoard();
    }

    // -------------- HorizontalWinStrategy Tests --------------

    @Test
    @DisplayName("HorizontalWinStrategy detects 4 in a row at bottom")
    void testHorizontalWinBottom() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();

        board[5][0] = 'X';
        board[5][1] = 'X';
        board[5][2] = 'X';
        board[5][3] = 'X';

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("HorizontalWinStrategy detects 4 in a row in middle")
    void testHorizontalWinMiddle() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();

        board[3][2] = 'O';
        board[3][3] = 'O';
        board[3][4] = 'O';
        board[3][5] = 'O';

        assertTrue(strategy.checkWin(board, 'O'));
    }

    @Test
    @DisplayName("HorizontalWinStrategy detects 5+ in a row")
    void testHorizontalWinMoreThanFour() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();

        for (int col = 0; col < 7; col++) {
            board[5][col] = 'X';
        }

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("HorizontalWinStrategy returns false for only 3 in a row")
    void testHorizontalNoWinThree() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();

        board[5][0] = 'X';
        board[5][1] = 'X';
        board[5][2] = 'X';

        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("HorizontalWinStrategy returns false when interrupted")
    void testHorizontalNoWinInterrupted() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();

        board[5][0] = 'X';
        board[5][1] = 'X';
        board[5][2] = 'O'; // Interruption
        board[5][3] = 'X';
        board[5][4] = 'X';

        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("HorizontalWinStrategy returns false on empty board")
    void testHorizontalNoWinEmpty() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();
        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("HorizontalWinStrategy has correct win type")
    void testHorizontalWinType() {
        HorizontalWinStrategy strategy = new HorizontalWinStrategy();
        assertEquals("Horizontal", strategy.getWinType());
    }

    // -------------- VerticalWinStrategy Tests --------------

    @Test
    @DisplayName("VerticalWinStrategy detects 4 in a column")
    void testVerticalWin() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();

        board[5][0] = 'X';
        board[4][0] = 'X';
        board[3][0] = 'X';
        board[2][0] = 'X';

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("VerticalWinStrategy detects 4 in middle column")
    void testVerticalWinMiddleColumn() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();

        board[5][3] = 'O';
        board[4][3] = 'O';
        board[3][3] = 'O';
        board[2][3] = 'O';

        assertTrue(strategy.checkWin(board, 'O'));
    }

    @Test
    @DisplayName("VerticalWinStrategy detects 5+ in a column")
    void testVerticalWinMoreThanFour() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();

        for (int row = 0; row < 6; row++) {
            board[row][0] = 'X';
        }

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("VerticalWinStrategy returns false for only 3 in a column")
    void testVerticalNoWinThree() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();

        board[5][0] = 'X';
        board[4][0] = 'X';
        board[3][0] = 'X';

        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("VerticalWinStrategy returns false when interrupted")
    void testVerticalNoWinInterrupted() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();

        board[5][0] = 'X';
        board[4][0] = 'X';
        board[3][0] = 'O'; // Interruption
        board[2][0] = 'X';
        board[1][0] = 'X';

        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("VerticalWinStrategy returns false on empty board")
    void testVerticalNoWinEmpty() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();
        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("VerticalWinStrategy has correct win type")
    void testVerticalWinType() {
        VerticalWinStrategy strategy = new VerticalWinStrategy();
        assertEquals("Vertical", strategy.getWinType());
    }

    // -------------- DiagonalWinStrategy Tests --------------

    @Test
    @DisplayName("DiagonalWinStrategy detects down-right diagonal (\\)")
    void testDiagonalWinDownRight() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[2][0] = 'X';
        board[3][1] = 'X';
        board[4][2] = 'X';
        board[5][3] = 'X';

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy detects down-left diagonal (/)")
    void testDiagonalWinDownLeft() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[2][3] = 'O';
        board[3][2] = 'O';
        board[4][1] = 'O';
        board[5][0] = 'O';

        assertTrue(strategy.checkWin(board, 'O'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy detects diagonal from top-left")
    void testDiagonalWinTopLeft() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[0][0] = 'X';
        board[1][1] = 'X';
        board[2][2] = 'X';
        board[3][3] = 'X';

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy detects diagonal from top-right")
    void testDiagonalWinTopRight() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[0][6] = 'O';
        board[1][5] = 'O';
        board[2][4] = 'O';
        board[3][3] = 'O';

        assertTrue(strategy.checkWin(board, 'O'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy detects 5+ in diagonal")
    void testDiagonalWinMoreThanFour() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[1][0] = 'X';
        board[2][1] = 'X';
        board[3][2] = 'X';
        board[4][3] = 'X';
        board[5][4] = 'X';

        assertTrue(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy returns false for only 3 in diagonal")
    void testDiagonalNoWinThree() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[3][0] = 'X';
        board[4][1] = 'X';
        board[5][2] = 'X';

        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy returns false when interrupted")
    void testDiagonalNoWinInterrupted() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();

        board[2][0] = 'X';
        board[3][1] = 'X';
        board[4][2] = 'O'; // Interruption
        board[5][3] = 'X';

        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy returns false on empty board")
    void testDiagonalNoWinEmpty() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();
        assertFalse(strategy.checkWin(board, 'X'));
    }

    @Test
    @DisplayName("DiagonalWinStrategy has correct win type")
    void testDiagonalWinType() {
        DiagonalWinStrategy strategy = new DiagonalWinStrategy();
        assertEquals("Diagonal", strategy.getWinType());
    }

    // -------------- Edge Case Tests --------------

    @Test
    @DisplayName("Strategies work with different piece characters")
    void testDifferentPieceCharacters() {
        board[5][0] = 'A';
        board[5][1] = 'A';
        board[5][2] = 'A';
        board[5][3] = 'A';

        HorizontalWinStrategy strategy = new HorizontalWinStrategy();
        assertTrue(strategy.checkWin(board, 'A'));
    }

    @Test
    @DisplayName("All strategies implement WinStrategy interface")
    void testAllImplementWinStrategy() {
        assertTrue(new HorizontalWinStrategy() instanceof WinStrategy);
        assertTrue(new VerticalWinStrategy() instanceof WinStrategy);
        assertTrue(new DiagonalWinStrategy() instanceof WinStrategy);
    }

    @Test
    @DisplayName("Multiple wins on same board detected correctly")
    void testMultipleWins() {
        // Set up both horizontal and vertical wins for X
        board[5][0] = 'X';
        board[5][1] = 'X';
        board[5][2] = 'X';
        board[5][3] = 'X';

        board[5][0] = 'X';
        board[4][0] = 'X';
        board[3][0] = 'X';
        board[2][0] = 'X';

        HorizontalWinStrategy horizontal = new HorizontalWinStrategy();
        VerticalWinStrategy vertical = new VerticalWinStrategy();

        assertTrue(horizontal.checkWin(board, 'X'));
        assertTrue(vertical.checkWin(board, 'X'));
    }

    // -------------- helper method yay --------------

    private char[][] createEmptyBoard() {
        char[][] board = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }
}