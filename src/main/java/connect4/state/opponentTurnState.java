package connect4.state;

import connect4.command.Command;
import connect4.command.PlacePieceCommand;
import connect4.observers.EventType;

/**
 * State when it's the opponent's (AI) turn
 * Handles AI move execution, win checking, and state transitions
 */
public class opponentTurnState implements State {

    @Override
    public State handleTurn(GameContext context) {
        System.out.println("Current State: " + getStateName());
        System.out.println(context.getOpponent().getName() + "'s turn...");

        // AI chooses column
        int aiCol = context.getOpponent().chooseColumn();

        if (aiCol == -1) {
            System.out.println("Board is full! It's a draw.");
            context.getEventBus().publish(EventType.WIN, "Draw");
            return new gameOverState("Draw");
        }

        // Create and execute command
        Command command = new PlacePieceCommand(
                context.getBoard(),
                aiCol,
                context.getOpponent().getPiece(),
                context.getRows()
        );

        if (!command.execute()) {
            System.out.println("AI move failed!");
            return this; // Stay in opponent turn (shouldn't happen)
        }

        System.out.println(context.getOpponent().getName() + " placed piece in column " + (aiCol + 1));
        context.getEventBus().publish(EventType.MADE_A_MOVE, command);

        // Check for win
        if (context.getWinChecker().checkWin(command.getRow(), command.getColumn(),
                context.getOpponent().getPiece())) {
            context.getEventBus().publish(EventType.WIN, context.getOpponent().getName());
            context.getEventBus().publish(EventType.LOSE, context.getPlayer().getName());
            return new gameOverState(context.getOpponent().getName());
        }

        // Check for draw
        if (context.isBoardFull()) {
            context.getEventBus().publish(EventType.WIN, "Draw");
            return new gameOverState("Draw");
        }

        // Switch back to player turn
        return new playerTurnState();
    }

    @Override
    public String getStateName() {
        return "OPPONENT_TURN";
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}