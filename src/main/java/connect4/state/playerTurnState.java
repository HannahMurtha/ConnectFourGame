package connect4.state;
import connect4.command.Command;

/**
 * State when it's the player's turn
 * Player can make moves and undo previous moves.
 */
public class playerTurnState implements State {

    private Command lastCommand;

    @Override
    public Command makeMove(int column) { //Move logic is handled by the COMMAND pattern
        System.out.println("Player making move in column " + column);
        return null; // Will be replaced with actual Command creation
    }

    @Override
    public State nextState() {
        return new opponentTurnState(); // Swap to opponent after player turn
    }

    @Override
    public String getStateName() {
        return "PLAYER_TURN"; // Return state name
    }

    @Override
    public boolean canUndo() {
        return true; // Player can undo during their turn
    }

    @Override
    public boolean undo() {
        if (lastCommand != null) { // is the last command valid
            lastCommand.undo();
            return true;
        }
        System.out.println("No move to undo.");
        return false;
    }

    public void setLastCommand(Command command) { // set up the prev command
        this.lastCommand = command;
    }
}