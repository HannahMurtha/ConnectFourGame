package connect4.characters;

/**
 * Factory class for creating Player and Opponent instances
 * Ensures both characters share the same board configuration
 */
public class CharacterFactory {

    private static final char PLAYER_PIECE = 'X';
    private static final char OPPONENT_PIECE = 'O';
    private static final int DEFAULT_ROWS = 6;
    private static final int DEFAULT_COLS = 7;

    // Create player with specified board size
    public static Player createPlayer(String name, int rows, int cols) {
        return new Player(name, rows, cols, PLAYER_PIECE);
    }

    // Create player with default 6x7 board
    public static Player createPlayer(String name) {
        return createPlayer(name, DEFAULT_ROWS, DEFAULT_COLS);
    }

    // Create opponent with specified board size
    public static Opponent createOpponent(String name, int difficulty, int rows, int cols) {
        return new Opponent(name, difficulty, rows, cols, OPPONENT_PIECE);
    }

    // Create opponent with default 6x7 board
    public static Opponent createOpponent(String name, int difficulty) {
        return createOpponent(name, difficulty, DEFAULT_ROWS, DEFAULT_COLS);
    }

    // Create both player and opponent for a complete game
    public static Object[] createGame(String playerName, String opponentName, int difficulty, int rows, int cols) {
        Player player = createPlayer(playerName, rows, cols);
        Opponent opponent = createOpponent(opponentName, difficulty, rows, cols);
        return new Object[]{player, opponent};
    }
}