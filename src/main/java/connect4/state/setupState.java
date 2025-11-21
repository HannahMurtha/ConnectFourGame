package connect4.state;

import connect4.command.Command;

/**
 * Initial state before game starts
 * No moves can be made in this state!
 */
public class setupState implements State {

    @Override
    public Command makeMove(int column) {
        System.out.println("Game hasn't started yet! Cannot make moves.");
        return null;
    }

    @Override
    public State nextState() {
        return new playerTurnState(); // setupState is done, into playerTurnState
    }

    @Override
    public String getStateName() {
        return "SETUP"; // return the state
    }

    @Override
    public boolean canUndo() {
        return false; // no moves to undo during setup
    }

    @Override
    public boolean undo() {
        System.out.println("Nothing to undo during setup."); // cant undo stuff
        return false;
    }
}