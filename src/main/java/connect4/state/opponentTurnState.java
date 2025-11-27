package connect4.state;
import connect4.command.Command;
import connect4.command.PlacePieceCommand;
import connect4.characters.Opponent;

/**
 * State when it's the opponent's (AI) turn
 * AI makes automatic moves, player cannot intervene
 */
public class opponentTurnState implements State {

    private final Opponent opponent;
    private final char[][] board;
    private final int rows;

    public opponentTurnState(Opponent opponent, char[][] board, int rows) {
        this.opponent = opponent;
        this.board = board;
        this.rows = rows;
    }

    @Override
    public Command makeMove(int column) {
        // AI chooses column automatically using strategy
        int aiColumn = opponent.chooseColumn();
        System.out.println("Opponent making move in column " + (aiColumn + 1));

        // Create and return the command
        return new PlacePieceCommand(board, aiColumn, opponent.getPiece(), rows);
    }

    @Override
    public State nextState() {
        return new playerTurnState(); // back to player turn
    }

    @Override
    public String getStateName() {
        return "OPPONENT_TURN";
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