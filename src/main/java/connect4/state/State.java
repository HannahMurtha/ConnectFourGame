package connect4.state;

import connect4.command.Command;

/**
 * State interface for Connect Four game states.
 * Defines the contract for all game states.
 */
public interface State {

    Command makeMove(int column); // handle moves in the current state

    State nextState(); // order matters! setupState -> playerTurnState -> opponenetTurnState -> repeat -> gameOverState

    String getStateName(); // get the current state name

    boolean canUndo(); // return true if you can undo

    boolean undo(); // handles undo operation
}