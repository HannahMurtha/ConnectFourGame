package connect4.state;
import connect4.command.Command;
import connect4.characters.Opponent;

/**
 * State when it's the player's turn
 * Player can make moves and undo previous moves.
 */
public class playerTurnState implements State {

    private Command lastCommand;
    private final Opponent opponent;
    private final char[][] board;
    private final int rows;

    // Constructor without parameters (for initial state)
    public playerTurnState() {
        this.opponent = null;
        this.board = null;
        this.rows = 0;
    }

    // Constructor with parameters (for transitions from opponent turn)
    public playerTurnState(Opponent opponent, char[][] board, int rows) {
        this.opponent = opponent;
        this.board = board;
        this.rows = rows;
    }

    @Override
    public Command makeMove(int column) {
        System.out.println("Player making move in column " + column);
        return null; // Will be replaced with actual Command creation
    }

    @Override
    public State nextState() {
        if (opponent != null && board != null && rows > 0) {
            return new opponentTurnState(opponent, board, rows);
        }
        // If no opponent data, stay in player turn
        return this;
    }

    @Override
    public String getStateName() {
        return "PLAYER_TURN";
    }

    @Override
    public boolean canUndo() {
        return true;
    }

    @Override
    public boolean undo() {
        if (lastCommand != null) {
            lastCommand.undo();
            return true;
        }
        System.out.println("No move to undo.");
        return false;
    }

    public void setLastCommand(Command command) {
        this.lastCommand = command;
    }
}