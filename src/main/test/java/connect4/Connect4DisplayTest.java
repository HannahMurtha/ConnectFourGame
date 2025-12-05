package connect4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ConnectFourDisplay
 */
class Connect4DisplayTest {
    private ConnectFourDisplay display;
    private char[][] board;

    @BeforeEach
    void setUp() {
        display = new ConnectFourDisplay(6, 7);
        board = display.getBoard();
    }

    @Test
    @DisplayName("Display creates board with correct dimensions")
    void testBoardDimensions() {
        assertEquals(6, board.length);
        assertEquals(7, board[0].length);
    }

    @Test
    @DisplayName("Board starts with all empty spaces")
    void testBoardStartsEmpty() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertEquals(' ', board[row][col]);
            }
        }
    }

    @Test
    @DisplayName("Can place piece on board")
    void testPlacePiece() {
        board[5][0] = 'X';
        assertEquals('X', board[5][0]);
    }

    @Test
    @DisplayName("Can place multiple pieces")
    void testPlaceMultiplePieces() {
        board[5][0] = 'X';
        board[5][1] = 'O';
        board[4][0] = 'X';

        assertEquals('X', board[5][0]);
        assertEquals('O', board[5][1]);
        assertEquals('X', board[4][0]);
    }

    @Test
    @DisplayName("getBoard returns the board")
    void testGetBoard() {
        char[][] retrievedBoard = display.getBoard();
        assertSame(board, retrievedBoard);
    }

    @Test
    @DisplayName("displayBoard doesn't crash with empty board")
    void testDisplayEmptyBoard() {
        assertDoesNotThrow(() -> display.displayBoard());
    }

    @Test
    @DisplayName("displayBoard doesn't crash with pieces")
    void testDisplayBoardWithPieces() {
        board[5][3] = 'X';
        board[5][4] = 'O';
        assertDoesNotThrow(() -> display.displayBoard());
    }

    @Test
    @DisplayName("Board modifications persist")
    void testBoardModifications() {
        board[2][3] = 'X';
        assertEquals('X', display.getBoard()[2][3]);
    }

    @Test
    @DisplayName("Can fill entire board")
    void testFillBoard() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                board[row][col] = 'X';
            }
        }

        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                assertEquals('X', board[row][col]);
            }
        }
    }
}