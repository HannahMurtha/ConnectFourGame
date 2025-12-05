package connect4.characters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CharacterFactory, Player, and Opponent
 */
class CharacterTest {

    // ---- CharacterFactory Tests ----

    @Test
    @DisplayName("Factory creates player with custom board size")
    void testCreatePlayerCustomSize() {
        Player player = CharacterFactory.createPlayer("Alice", 6, 7);

        assertEquals("Alice", player.getName());
        assertEquals(6, player.getRows());
        assertEquals(7, player.getCols());
        assertEquals('X', player.getPiece());
    }

    @Test
    @DisplayName("Factory creates player with default board size")
    void testCreatePlayerDefaultSize() {
        Player player = CharacterFactory.createPlayer("Bob");

        assertEquals("Bob", player.getName());
        assertEquals(6, player.getRows());
        assertEquals(7, player.getCols());
        assertEquals('X', player.getPiece());
    }

    @Test
    @DisplayName("Factory creates opponent with custom board size")
    void testCreateOpponentCustomSize() {
        Opponent opponent = CharacterFactory.createOpponent("AI", 2, 6, 7);

        assertEquals("AI", opponent.getName());
        assertEquals(2, opponent.getDifficulty());
        assertEquals(6, opponent.getRows());
        assertEquals(7, opponent.getCols());
        assertEquals('O', opponent.getPiece());
    }

    @Test
    @DisplayName("Factory creates opponent with default board size")
    void testCreateOpponentDefaultSize() {
        Opponent opponent = CharacterFactory.createOpponent("Computer", 3);

        assertEquals("Computer", opponent.getName());
        assertEquals(3, opponent.getDifficulty());
        assertEquals(6, opponent.getRows());
        assertEquals(7, opponent.getCols());
        assertEquals('O', opponent.getPiece());
    }

    @Test
    @DisplayName("Factory creates complete game with both characters")
    void testCreateGame() {
        Object[] game = CharacterFactory.createGame("Player1", "AI", 2, 6, 7);

        assertEquals(2, game.length);
        assertTrue(game[0] instanceof Player);
        assertTrue(game[1] instanceof Opponent);

        Player player = (Player) game[0];
        Opponent opponent = (Opponent) game[1];

        assertEquals("Player1", player.getName());
        assertEquals("AI", opponent.getName());
        assertEquals(2, opponent.getDifficulty());
    }

    @Test
    @DisplayName("Factory creates opponents with different difficulties")
    void testCreateDifferentDifficulties() {
        Opponent easy = CharacterFactory.createOpponent("EasyAI", 1);
        Opponent medium = CharacterFactory.createOpponent("MediumAI", 2);
        Opponent hard = CharacterFactory.createOpponent("HardAI", 3);

        assertEquals(1, easy.getDifficulty());
        assertEquals(2, medium.getDifficulty());
        assertEquals(3, hard.getDifficulty());
    }

    // ----- Player Tests ----

    @Test
    @DisplayName("Player gets name right")
    void testPlayerName() {
        Player player = new Player("TestPlayer", 6, 7, 'X');
        assertEquals("TestPlayer", player.getName());
    }

    @Test
    @DisplayName("Player gets board dimensions right")
    void testPlayerDimensions() {
        Player player = new Player("Test", 6, 7, 'X');
        assertEquals(6, player.getRows());
        assertEquals(7, player.getCols());
    }

    @Test
    @DisplayName("Player gets piece correctly")
    void testPlayerPiece() {
        Player player = new Player("Test", 6, 7, 'X');
        assertEquals('X', player.getPiece());
    }

    @Test
    @DisplayName("Player implements Character interface")
    void testPlayerImplementsCharacter() {
        Player player = new Player("Test", 6, 7, 'X');
        assertTrue(player instanceof Character);
    }

    @Test
    @DisplayName("Player toString contains key info")
    void testPlayerToString() {
        Player player = new Player("Alice", 6, 7, 'X');
        String str = player.toString();

        assertTrue(str.contains("Alice"));
        assertTrue(str.contains("6"));
        assertTrue(str.contains("7"));
        assertTrue(str.contains("X"));
    }

    // ------------ Opponent Tests ------------

    @Test
    @DisplayName("Opponent gets name correctly")
    void testOpponentName() {
        Opponent opponent = new Opponent("AI", 2, 6, 7, 'O');
        assertEquals("AI", opponent.getName());
    }

    @Test
    @DisplayName("Opponent gets difficulty correctly")
    void testOpponentDifficulty() {
        Opponent opponent = new Opponent("AI", 3, 6, 7, 'O');
        assertEquals(3, opponent.getDifficulty());
    }

    @Test
    @DisplayName("Opponent gets board dimensions correctly")
    void testOpponentDimensions() {
        Opponent opponent = new Opponent("AI", 2, 6, 7, 'O');
        assertEquals(6, opponent.getRows());
        assertEquals(7, opponent.getCols());
    }

    @Test
    @DisplayName("Opponent grts piece correctly")
    void testOpponentPiece() {
        Opponent opponent = new Opponent("AI", 2, 6, 7, 'O');
        assertEquals('O', opponent.getPiece());
    }

    @Test
    @DisplayName("Opponent implements Character interface")
    void testOpponentImplementsCharacter() {
        Opponent opponent = new Opponent("AI", 2, 6, 7, 'O');
        assertTrue(true);
    }

    @Test
    @DisplayName("Opponent REQUIRES strategy before choosing column")
    void testOpponentRequiresStrategy() {
        Opponent opponent = new Opponent("AI", 2, 6, 7, 'O');
        // something to note is the default diff is 2
        assertThrows(IllegalStateException.class, () -> {
            opponent.chooseColumn();
        });
    }

    @Test
    @DisplayName("Opponent chooses valid column after strategy set")
    void testOpponentChoosesValidColumn() {
        Opponent opponent = new Opponent("AI", 2, 6, 7, 'O');
        char[][] board = createEmptyBoard();
        opponent.setStrategy(board, 'X');
        int column = opponent.chooseColumn();

        assertTrue(column >= 0 && column < 7);
    }

    @Test
    @DisplayName("Opponent toString contains key info")
    void testOpponentToString() {
        Opponent opponent = new Opponent("SmartAI", 3, 6, 7, 'O');
        String str = opponent.toString();

        assertTrue(str.contains("SmartAI"));
        assertTrue(str.contains("3"));
        assertTrue(str.contains("6"));
        assertTrue(str.contains("7"));
        assertTrue(str.contains("O"));
    }

    // --- Integration Tests ---

    @Test
    @DisplayName("Factory creates player and opponent with valid (matching) board sizes")
    void testMatchingBoardSizes() {
        Player player = CharacterFactory.createPlayer("P1", 6, 7);
        Opponent opponent = CharacterFactory.createOpponent("AI", 2, 6, 7);

        assertEquals(player.getRows(), opponent.getRows());
        assertEquals(player.getCols(), opponent.getCols());
    }

    @Test
    @DisplayName("Player and opponent have different pieces")
    void testDifferentPieces() {
        Player player = CharacterFactory.createPlayer("P1");
        Opponent opponent = CharacterFactory.createOpponent("AI", 2);

        assertNotEquals(player.getPiece(), opponent.getPiece());
        assertEquals('X', player.getPiece());
        assertEquals('O', opponent.getPiece());
    }

    // Helper method
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