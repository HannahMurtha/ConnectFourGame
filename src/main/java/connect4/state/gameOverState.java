package connect4.state;

import connect4.command.Command;

/**
 * State when the game has ended (win, loss, or draw).
 * No moves can be made in this state.
 */
public class gameOverState implements State {

    private final String winner; // "Player", "Opponent", or "Draw"

    public gameOverState(String winner) {
        this.winner = winner;
    }

    @Override
    public Command makeMove(int column) {
        System.out.println("Game is over! No more moves allowed.");
        System.out.println("Winner: " + winner);
        return null;
    }

    @Override
    public State nextState() {
        // Game over is terminal state, can only restart
        System.out.println("Game over. Start a new game to play again.");
        return this; // Stay in game over state
    }

    @Override
    public String getStateName() {
        return "GAME_OVER"; // return game over state
    }

    @Override
    public boolean canUndo() {
        return false; // cannot undo when game is over lol
    }

    @Override
    public boolean undo() {
        System.out.println("Cannot undo. Game is over.");
        return false;
    }

    public String getWinner() {
        return winner; // might be null :)
    }
}