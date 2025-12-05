package connect4.state;

/**
 * State when the game has ended (win, loss, or draw)
 * is the game over ?
 */
public class gameOverState implements State {

    private final String winner; // Player name, opponent name, or "Draw"

    public gameOverState(String winner) {
        this.winner = winner;
    }

    @Override
    public State handleTurn(GameContext context) {
        // game is over, no more turns
        System.out.println("\n=== Game Over ===");
        System.out.println("Winner: " + winner);
        System.out.println("Start a new game to play again.");
        return this; // Stay in game over state
    }

    @Override
    public String getStateName() {
        return "GAME_OVER";
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}