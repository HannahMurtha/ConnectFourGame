package connect4.state;

import java.util.Scanner;

/**
 * State interface for Connect Four game states
 * Each state handles its own behavior and transitions
 */
public interface State {

    State handleTurn(GameContext context);

    String getStateName();

    boolean isGameOver();
}