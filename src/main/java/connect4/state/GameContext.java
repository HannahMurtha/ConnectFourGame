package connect4.state;

import connect4.characters.Player;
import connect4.characters.Opponent;
import connect4.command.Command;
import connect4.observers.EventBus;
import connect4.strategy.PlayerStrategy;

import java.util.Scanner;

/**
 * Context object containing all game state and objects
 * Passed to states so they can access what they need
 */
public class GameContext {
    private final Scanner scanner;
    private final Player player;
    private final Opponent opponent;
    private final char[][] board;
    private final int rows;
    private final EventBus eventBus;
    private final PlayerStrategy winChecker;
    private Command lastCommand;

    public GameContext(Scanner scanner, Player player, Opponent opponent, char[][] board, int rows, EventBus eventBus, PlayerStrategy winChecker) { // shouldve been builder but oh well
        this.scanner = scanner;
        this.player = player;
        this.opponent = opponent;
        this.board = board;
        this.rows = rows;
        this.eventBus = eventBus;
        this.winChecker = winChecker;
        this.lastCommand = null;
    } // prolly should be builder pattern at this point lol

    // Getters
    public Scanner getScanner() { return scanner; }
    public Player getPlayer() { return player; }
    public Opponent getOpponent() { return opponent; }
    public char[][] getBoard() { return board; }
    public int getRows() { return rows; }
    public EventBus getEventBus() { return eventBus; }
    public PlayerStrategy getWinChecker() { return winChecker; }
    public Command getLastCommand() { return lastCommand; }

    // Setter for last command
    public void setLastCommand(Command command) {
        this.lastCommand = command;
    }

    // Helper method to check if board is full
    public boolean isBoardFull() {
        for (int c = 0; c < 7; c++) {
            if (board[0][c] == ' ') {
                return false;
            }
        }
        return true;
    }
}