package connect4.state;
import connect4.command.Command;

/**
 * State when it's the opponent's (AI) turn
 * AI makes automatic moves, player cannot intervene
 */
public class opponentTurnState implements State {

    @Override
    public Command makeMove(int column) {
        System.out.println("Opponent making move in column " + column); // AI logic will determine the column based on difficulty
        return null; // Will be replaced with actual Command creation
    }

    @Override
    public State nextState() {
        return new playerTurnState(); // back to player turn usually
    }

    @Override
    public String getStateName() {
        return "OPPONENT_TURN"; // update the state
    }

    @Override
    public boolean canUndo() {
        return false; // cannot undo during opponent's turn
    }

    @Override
    public boolean undo() {
        System.out.println("Cannot undo during opponent's turn.");
        return false;
    }
}