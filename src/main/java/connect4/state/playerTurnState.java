package connect4.state;

import connect4.command.Command;
import connect4.command.PlacePieceCommand;
import connect4.observers.EventType;

/**
 * State when it's the player's turn
 * Handles player input, move execution, win checking, and state transitions
 */
public class playerTurnState implements State {

    @Override
    public State handleTurn(GameContext context) {
        System.out.println("Current State: " + getStateName());
        System.out.print(context.getPlayer().getName() + " (" + context.getPlayer().getPiece() +
                "), choose column (1-7) or 'u' to undo: ");

        String input = context.getScanner().nextLine().trim();

        // Handle undo
        if (input.equalsIgnoreCase("u")) {
            Command lastCommand = context.getLastCommand();
            if (lastCommand != null && lastCommand.undo()) {
                context.getEventBus().publish(EventType.UNDO_MOVE, null);
                System.out.println("Move undone!");
            } else {
                System.out.println("Cannot undo!");
            }
            return this; // Stay in player turn
        }

        // Parse column input
        try {
            int col = Integer.parseInt(input) - 1;

            if (col < 0 || col > 6) {
                System.out.println("Invalid column! Choose 1-7.");
                return this; // Stay in player turn
            }

            // Create and execute command
            Command command = new PlacePieceCommand(
                    context.getBoard(),
                    col,
                    context.getPlayer().getPiece(),
                    context.getRows()
            );

            if (!command.execute()) {
                System.out.println("Column is full! Try another.");
                return this; // Stay in player turn
            }

            // Command executed successfully
            context.setLastCommand(command);
            context.getEventBus().publish(EventType.MADE_A_MOVE, command);

            // Check for win
            if (context.getWinChecker().checkWin(command.getRow(), command.getColumn(),
                    context.getPlayer().getPiece())) {
                context.getEventBus().publish(EventType.WIN, context.getPlayer().getName());
                context.getEventBus().publish(EventType.LOSE, context.getOpponent().getName());
                return new gameOverState(context.getPlayer().getName());
            }

            // Check for draw
            if (context.isBoardFull()) {
                context.getEventBus().publish(EventType.WIN, "Draw");
                return new gameOverState("Draw");
            }

            // Switch to opponent turn
            return new opponentTurnState();

        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Enter a number 1-7 or 'u' to undo.");
            return this; // Stay in player turn
        }
    }

    @Override
    public String getStateName() {
        return "PLAYER_TURN";
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}