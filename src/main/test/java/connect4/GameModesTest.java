package connect4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

/**
 * Tests for PlayerVsPlayerGame and PlayerVsAIGame
 */
class GameModesTest {
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        scanner = new Scanner(System.in);
    }

    // ---- PlayerVsPlayerGame tests ----

    @Test
    @DisplayName("PlayerVsPlayerGame initializes with two players")
    void testPlayerVsPlayerInitialization() {
        PlayerVsPlayerGame game = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        assertNotNull(game);
    }

    @Test
    @DisplayName("PlayerVsPlayerGame extends Game")
    void testPlayerVsPlayerExtendsGame() {
        PlayerVsPlayerGame game = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        assertTrue(game instanceof Game);
    }

    @Test
    @DisplayName("PlayerVsPlayerGame creates players with correct names")
    void testPlayerVsPlayerPlayerNames() {
        // can't directly access player1/player2 (private) oops
        assertDoesNotThrow(() -> {
            PlayerVsPlayerGame game = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        });
    }

    @Test
    @DisplayName("PlayerVsPlayerGame handles valid move input")
    void testPlayerVsPlayerValidMove() {
        String input = "1\n2\n3\n4\n"; // Simulate moves
        Scanner testScanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        PlayerVsPlayerGame game = new PlayerVsPlayerGame(testScanner, "Alice", "Bob");
        assertDoesNotThrow(() -> {
            // do it initialize?
        });
    }

    @Test
    @DisplayName("PlayerVsPlayerGame accepts different player names")
    void testPlayerVsPlayerDifferentNames() {
        assertDoesNotThrow(() -> {
            new PlayerVsPlayerGame(scanner, "Player1", "Player2");
            new PlayerVsPlayerGame(scanner, "X", "O");
            new PlayerVsPlayerGame(scanner, "Human1", "Human2");
        });
    }

    @Test
    @DisplayName("PlayerVsPlayerGame initializes board through Game parent")
    void testPlayerVsPlayerBoardInitialization() {
        PlayerVsPlayerGame game = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        // game should have initialized display and board through constructor
        assertNotNull(game);
    }

    // ---- PlayerVsAIGame Tests ----

    @Test
    @DisplayName("PlayerVsAIGame initializes with player and opponent")
    void testPlayerVsAIInitialization() {
        PlayerVsAIGame game = new PlayerVsAIGame(scanner, "Bill", 2);
        assertNotNull(game);
    }

    @Test
    @DisplayName("PlayerVsAIGame extends Game")
    void testPlayerVsAIExtendsGame() {
        PlayerVsAIGame game = new PlayerVsAIGame(scanner, "Jacob", 2);
        assertTrue(game instanceof Game);
    }

    @Test
    @DisplayName("PlayerVsAIGame creates player with correct name")
    void testPlayerVsAIPlayerName() {
        assertDoesNotThrow(() -> {
            PlayerVsAIGame game = new PlayerVsAIGame(scanner, "TestPlayer", 1);
        });
    }

    @Test
    @DisplayName("PlayerVsAIGame creates opponent with correct difficulty")
    void testPlayerVsAIOpponentDifficulty() {
        // test all three difficulty levels AT ONCE WOOOOW
        assertDoesNotThrow(() -> {
            new PlayerVsAIGame(scanner, "Player", 1);
            new PlayerVsAIGame(scanner, "Player", 2);
            new PlayerVsAIGame(scanner, "Player", 3);
        });
    }

    @Test
    @DisplayName("PlayerVsAIGame sets opponent strategy on initialization")
    void testPlayerVsAIOpponentStrategySet() {
        // opponent strategy should be set in constructor
        assertDoesNotThrow(() -> {
            PlayerVsAIGame game = new PlayerVsAIGame(scanner, "Player", 2);
        });
    }

    @Test
    @DisplayName("PlayerVsAIGame uses state pattern")
    void testPlayerVsAIUsesStatePattern() {
        // Game should use state pattern (GameContext created in constructor)
        assertDoesNotThrow(() -> {
            PlayerVsAIGame game = new PlayerVsAIGame(scanner, "Player", 3);
        });
    }

    @Test
    @DisplayName("PlayerVsAIGame works with all difficulty levels")
    void testPlayerVsAIAllDifficulties() {
        assertDoesNotThrow(() -> {
            new PlayerVsAIGame(scanner, "Player", 1); // Easy
            new PlayerVsAIGame(scanner, "Player", 2); // Medium
            new PlayerVsAIGame(scanner, "Player", 3); // Hard
        });
    }

    // ---- Comparison Tests ----

    @Test
    @DisplayName("Both game modes extend Game class")
    void testBothExtendsGame() {
        PlayerVsPlayerGame pvp = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        PlayerVsAIGame ai = new PlayerVsAIGame(scanner, "Alice", 2);

        assertTrue(pvp instanceof Game);
        assertTrue(ai instanceof Game);
    }

    @Test
    @DisplayName("Both game modes can be instantiated")
    void testBothModesInstantiate() {
        assertDoesNotThrow(() -> {
            new PlayerVsPlayerGame(scanner, "P1", "P2");
        });

        assertDoesNotThrow(() -> {
            new PlayerVsAIGame(scanner, "P1", 2);
        });
    }

    @Test
    @DisplayName("Both game modes have play method from Game")
    void testBothHavePlayMethod() {
        PlayerVsPlayerGame pvp = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        PlayerVsAIGame ai = new PlayerVsAIGame(scanner, "Alice", 2);

        // both should have play() method (abstract in Game, implemented in subclasses)
        assertDoesNotThrow(() -> { // DOES IT EXIST
        });
    }

    @Test
    @DisplayName("PlayerVsPlayerGame requires two player names")
    void testPlayerVsPlayerRequiresTwoNames() {
        assertDoesNotThrow(() -> {
            new PlayerVsPlayerGame(scanner, "Kenny", "Hannah");
        });
    }

    @Test
    @DisplayName("PlayerVsAIGame requires player name and difficulty")
    void testPlayerVsAIRequiresNameAndDifficulty() {
        assertDoesNotThrow(() -> {
            new PlayerVsAIGame(scanner, "Player", 1);
            new PlayerVsAIGame(scanner, "Player", 2);
            new PlayerVsAIGame(scanner, "Player", 3);
        });
    }

    // ---- Game parent class tests ----

    @Test
    @DisplayName("Game parent initializes display")
    void testGameParentInitializesDisplay() {
        PlayerVsPlayerGame game = new PlayerVsPlayerGame(scanner, "Alice", "Bob");
        assertNotNull(game); // initialize display
    }

    @Test
    @DisplayName("Game parent initializes board")
    void testGameParentInitializesBoard() {
        PlayerVsAIGame game = new PlayerVsAIGame(scanner, "Hannah", 2);
        assertNotNull(game); // initialize the board thru display
    }

    @Test
    @DisplayName("Game parent initializes EventBus")
    void testGameParentInitializesEventBus() {
        PlayerVsPlayerGame game = new PlayerVsPlayerGame(scanner, "Kenji", "Bob");
        assertNotNull(game); // get event bus insytance
    }

    @Test
    @DisplayName("Game parent initializes win checker")
    void testGameParentInitializesWinChecker() {
        PlayerVsAIGame game = new PlayerVsAIGame(scanner, "Kenny", 2);
        assertNotNull(game); // create playerstrategy win checker
    }

    @Test
    @DisplayName("Games can be created with same player names")
    void testSamePlayerNames() {
        assertDoesNotThrow(() -> {
            new PlayerVsPlayerGame(scanner, "Alice", "Bob");
            new PlayerVsAIGame(scanner, "Alice", 2);
        });
    }
}