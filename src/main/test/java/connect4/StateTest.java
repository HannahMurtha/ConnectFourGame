package connect4.state;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import connect4.characters.Player;
import connect4.characters.Opponent;
import connect4.characters.CharacterFactory;
import connect4.observers.EventBus;
import connect4.strategy.PlayerStrategy;
import java.util.Scanner;
import java.io.ByteArrayInputStream;

/**
 * Tests for State pattern implementation
 */
class StateTest {
    private GameContext context;
    private char[][] board;
    private Player player;
    private Opponent opponent;
    private EventBus eventBus;
    private PlayerStrategy winChecker;

    @BeforeEach
    void setUp() {
        board = createEmptyBoard();
        player = CharacterFactory.createPlayer("TestPlayer", 6, 7);
        opponent = CharacterFactory.createOpponent("AI", 1, 6, 7);
        opponent.setStrategy(board, player.getPiece());
        eventBus = EventBus.getInstance();
        winChecker = new PlayerStrategy(board, 6, 7);

        Scanner scanner = new Scanner(System.in);
        context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker);
    }

    // ========== GameContext Tests ==========

    @Test
    @DisplayName("GameContext stores all required objects")
    void testGameContextStoresObjects() {
        assertNotNull(context.getScanner());
        assertNotNull(context.getPlayer());
        assertNotNull(context.getOpponent());
        assertNotNull(context.getBoard());
        assertNotNull(context.getEventBus());
        assertNotNull(context.getWinChecker());
        assertEquals(6, context.getRows());
    }

    @Test
    @DisplayName("GameContext returns correct player")
    void testGameContextPlayer() {
        assertEquals("TestPlayer", context.getPlayer().getName());
        assertEquals('X', context.getPlayer().getPiece());
    }

    @Test
    @DisplayName("GameContext returns correct opponent")
    void testGameContextOpponent() {
        assertEquals("AI", context.getOpponent().getName());
        assertEquals('O', context.getOpponent().getPiece());
    }

    @Test
    @DisplayName("GameContext tracks last command")
    void testGameContextLastCommand() {
        assertNull(context.getLastCommand());
        // setLastCommand is tested in integration
    }

    @Test
    @DisplayName("GameContext detects empty board")
    void testGameContextEmptyBoard() {
        assertFalse(context.isBoardFull());
    }

    @Test
    @DisplayName("GameContext detects full board")
    void testGameContextFullBoard() {
        fillBoard(board);
        assertTrue(context.isBoardFull());
    }

    // ----------- setupState Tests -----------

    @Test
    @DisplayName("setupState transitions to playerTurnState")
    void testSetupStateTransition() {
        setupState setup = new setupState();
        State nextState = setup.handleTurn(context);

        assertTrue(nextState instanceof playerTurnState);
    }

    @Test
    @DisplayName("setupState has correct name")
    void testSetupStateName() {
        setupState setup = new setupState();
        assertEquals("SETUP", setup.getStateName());
    }

    @Test
    @DisplayName("setupState is not game over")
    void testSetupStateNotGameOver() {
        setupState setup = new setupState();
        assertFalse(setup.isGameOver());
    }

    // ----------- playerTurnState Tests -----------

    @Test
    @DisplayName("playerTurnState has correct name")
    void testPlayerTurnStateName() {
        playerTurnState state = new playerTurnState();
        assertEquals("PLAYER_TURN", state.getStateName());
    }

    @Test
    @DisplayName("playerTurnState is not game over")
    void testPlayerTurnStateNotGameOver() {
        playerTurnState state = new playerTurnState();
        assertFalse(state.isGameOver());
    }

    @Test
    @DisplayName("playerTurnState handles valid move and transitions")
    void testPlayerTurnStateValidMove() {
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker);
        opponent.setStrategy(board, player.getPiece());

        playerTurnState state = new playerTurnState();
        State nextState = state.handleTurn(context);

        // Should place piece and transition to opponent turn
        assertEquals('X', board[5][0]);
        assertTrue(nextState instanceof opponentTurnState);
    }

    @Test
    @DisplayName("playerTurnState stays in state on invalid column")
    void testPlayerTurnStateInvalidColumn() {
        String input = "10\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker);

        playerTurnState state = new playerTurnState();
        State nextState = state.handleTurn(context);

        // Should stay in player turn
        assertTrue(nextState instanceof playerTurnState);
    }

    @Test
    @DisplayName("playerTurnState stays in state on invalid input")
    void testPlayerTurnStateInvalidInput() {
        String input = "abc\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker);

        playerTurnState state = new playerTurnState();
        State nextState = state.handleTurn(context);

        // Should stay in player turn
        assertTrue(nextState instanceof playerTurnState);
    }

    @Test
    @DisplayName("playerTurnState detects win and transitions to gameOver")
    void testPlayerTurnStateWin() {
        // Set up board with 3 in a row
        board[5][0] = 'X';
        board[5][1] = 'X';
        board[5][2] = 'X';

        String input = "4\n"; // Complete the win
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker);

        playerTurnState state = new playerTurnState();
        State nextState = state.handleTurn(context);

        assertTrue(nextState instanceof gameOverState);
    }

    // ----------- opponentTurnState Tests -----------

    @Test
    @DisplayName("opponentTurnState has correct name")
    void testOpponentTurnStateName() {
        opponentTurnState state = new opponentTurnState();
        assertEquals("OPPONENT_TURN", state.getStateName());
    }

    @Test
    @DisplayName("opponentTurnState is not game over")
    void testOpponentTurnStateNotGameOver() {
        opponentTurnState state = new opponentTurnState();
        assertFalse(state.isGameOver());
    }

    @Test
    @DisplayName("opponentTurnState makes move and transitions")
    void testOpponentTurnStateMakesMove() {
        opponentTurnState state = new opponentTurnState();
        State nextState = state.handleTurn(context);

        // AI should have made a move
        boolean piecePlaced = false;
        for (int col = 0; col < 7; col++) {
            if (board[5][col] == 'O') {
                piecePlaced = true;
                break;
            }
        }
        assertTrue(piecePlaced);

        // Should transition to player turn
        assertTrue(nextState instanceof playerTurnState);
    }

    // ----------- gameOverState Tests -----------

    @Test
    @DisplayName("gameOverState has correct name")
    void testGameOverStateName() {
        gameOverState state = new gameOverState("TestPlayer");
        assertEquals("GAME_OVER", state.getStateName());
    }

    @Test
    @DisplayName("gameOverState is the correct state")
    void testGameOverStateIsGameOver() {
        gameOverState state = new gameOverState("TestPlayer");
        assertTrue(state.isGameOver());
    }


    @Test
    @DisplayName("gameOverState stores winner name")
    void testGameOverStateWinner() {
        gameOverState state = new gameOverState("Alice");
        // Winner is private, but state should work correctly
        assertDoesNotThrow(() -> state.handleTurn(context));
    }

    @Test
    @DisplayName("gameOverState has draw")
    void testGameOverStateDraw() {
        gameOverState state = new gameOverState("Draw");
        assertDoesNotThrow(() -> state.handleTurn(context));
    }

    // ----------- State Transition Integration Tests -----------

    @Test
    @DisplayName("Complete game flow ie setup -> player -> opponent")
    void testCompleteGameFlow() {
        String input = "1\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker);
        opponent.setStrategy(board, player.getPiece());

        // Start with setup
        State state = new setupState();

        // Setup -> player Turn
        state = state.handleTurn(context);
        assertTrue(state instanceof playerTurnState);

        // Player Turn -> opponent Turn
        state = state.handleTurn(context);
        assertTrue(state instanceof opponentTurnState);

        // Opponent Turn -> player Turn
        state = state.handleTurn(context);
        assertTrue(state instanceof playerTurnState);
    }

    @Test
    @DisplayName("All non-terminal states return false for isGameOver")
    void testNonTerminalStates() {
        assertFalse(new setupState().isGameOver());
        assertFalse(new playerTurnState().isGameOver());
        assertFalse(new opponentTurnState().isGameOver());
    }

    @Test
    @DisplayName("gameOverState returns true only for isGameOver")
    void testTerminalState() {
        assertTrue(new gameOverState("winner").isGameOver());
    }

    // ----------- Helper Methods -----------

    private char[][] createEmptyBoard() {
        char[][] board = new char[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = ' ';
            }
        }
        return board;
    }

    private void fillBoard(char[][] board) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 'X';
            }
        }
    }
}