package connect4.characters;

/**
 * Factory class for creating Player and Opponent instances
 * Ensures both characters share the same board configuration
 */
public class CharacterFactory {

    private static final char PLAYER_PIECE = 'X';
    private static final char OPPONENT_PIECE = 'O';

    public static Player createPlayer(String name, int rows, int cols) {
        return new Player(name, rows, cols, PLAYER_PIECE);
    }

    public static Opponent createOpponent(String name, int difficulty, int rows, int cols) {
        return new Opponent(name, difficulty, rows, cols, OPPONENT_PIECE);
    }

    public static Object[] createGame(String playerName, String opponentName, int difficulty, int rows, int cols) {
        Player player = createPlayer(playerName, rows, cols);
        Opponent opponent = createOpponent(opponentName, difficulty, rows, cols);
        return new Object[]{player, opponent};
    }
}