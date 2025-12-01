package connect4.state;

import java.util.Scanner;

/**
 * State interface for Connect Four game states.
 * Each state handles its own behavior and transitions.
 */
public interface State {

    /**
     * Handle the current turn/state logic
     * @param context Contains all game objects needed
     * @return The next state (or this if staying in same state)
     */
    State handleTurn(GameContext context);

    String getStateName();

    boolean isGameOver();
}