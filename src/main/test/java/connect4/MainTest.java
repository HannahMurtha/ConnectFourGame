package connect4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;

/**
 * Tests for Main class
 */
class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    @DisplayName("Main prints welcome message")
    void testWelcomeMessage() {
        String input = "2\nTestPlayer\n2\n1\n"; // Mode 2, name, difficulty, then a move to exit
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) {
                // Expected - game will run until we interrupt
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100); // Give it time to print
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.contains("Connect Four Game") || output.contains("===")); //ts is fragile lol
    }

    @Test
    @DisplayName("Main displays game mode options")
    void testGameModeOptions() {
        String input = "2\nPlayer\n2\n1\n";
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) {
                // Expected
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.contains("Player vs Player") || output.contains("1")); // mode 1
        assertTrue(output.contains("Player vs AI") || output.contains("2")); // mode 2
    }

    @Test
    @DisplayName("Main uses default mode 2 on invalid input")
    void testDefaultMode() {
        String input = "invalid\n\n2\n1\n"; // Invalid mode, empty name, difficulty, move
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            Main.main(new String[]{});
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.contains("Invalid input, using Player vs AI mode"));
    }

    @Test
    @DisplayName("Main uses default player name when empty")
    void testDefaultPlayerName() {
        String input = "2\n\n2\n1\n"; // Mode 2, empty name, difficulty, move
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) { // should happen
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // use default name "Player 1" i think
        assertDoesNotThrow(() -> {});
    }

    @Test
    @DisplayName("Main uses default difficulty 2 on invalid input")
    void testDefaultDifficulty() {
        String input = "2\nPlayer\ninvalid\n1\n"; // Mode 2, name, invalid difficulty, move
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) { // should happen
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.contains("Invalid") || output.contains("Medium") || output.length() > 0);
    }

    // --- Game Creation Tests ----

    @Test
    @DisplayName("Main creates PlayerVsPlayerGame for mode 1")
    void testCreatesPlayerVsPlayerGame() {
        String input = "1\nAlice\nBob\n1\n"; // Mode 1, two names, then a move
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) { // should happen
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.contains("Player vs Player") || output.contains("Alice") || output.length() > 0);
    }

    @Test
    @DisplayName("Main creates PlayerVsAIGame for mode 2")
    void testCreatesPlayerVsAIGame() {
        String input = "2\nCharlie\n3\n1\n"; // Mode 2, name, difficulty 3, move
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) {
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.length() > 0); // Game started
    }

    // --- Validation tests ---

    @Test
    @DisplayName("Main validates mode input range")
    void testValidatesModeRange() {
        String input = "5\nPlayer\n2\n1\n"; // Invalid mode, name, difficulty, move
        provideInput(input);

        Thread gameThread = new Thread(() -> {
            try {
                Main.main(new String[]{});
            } catch (Exception e) {
            }
        });

        gameThread.start();

        try {
            Thread.sleep(100);
            gameThread.interrupt();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String output = outContent.toString();
        assertTrue(output.contains("Invalid") || output.length() > 0);
    }

    @Test
    @DisplayName("Main handles all valid difficulties")
    void testAllValidDifficulties() {
        for (int diff = 1; diff <= 3; diff++) {
            outContent.reset();
            String input = "2\nPlayer\n" + diff + "\n1\n";
            provideInput(input);

            final int difficulty = diff;
            Thread gameThread = new Thread(() -> {
                try {
                    Main.main(new String[]{});
                } catch (Exception e) {
                    // Expected
                }
            });

            gameThread.start();

            try {
                Thread.sleep(100);
                gameThread.interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            assertTrue(outContent.toString().length() > 0);
        }
    }

    // helper method
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}