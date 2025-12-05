package connect4;

import connect4.observers.EventBus;
import connect4.observers.EventType;
import connect4.strategy.PlayerStrategy;
import connect4.command.Command;

/**
 * Base Game class with common game logic
 * logic here happens **every** game
 */
public abstract class Game {
    protected ConnectFourDisplay display;
    protected char[][] board;
    protected EventBus eventBus;
    protected PlayerStrategy winChecker;
    protected Command lastCommand;
    protected final int rows = 6;
    protected final int cols = 7;

    public Game() {
        this.display = new ConnectFourDisplay(rows, cols);
        this.board = display.getBoard();
        this.eventBus = EventBus.getInstance();
        this.winChecker = new PlayerStrategy(board, rows, cols);
        this.lastCommand = null;

        // Subscribe display to events
        eventBus.subscribe(EventType.MADE_A_MOVE, display);
        eventBus.subscribe(EventType.UNDO_MOVE, display);
        eventBus.subscribe(EventType.WIN, display);
        eventBus.subscribe(EventType.LOSE, display);
    }

    // start and run game
    public abstract void play();

    // is the board full
    protected boolean isBoardFull() {
        for (int c = 0; c < cols; c++) {
            if (board[0][c] == ' ') {
                return false;
            }
        }
        return true;
    }

    // load empty board
    protected void displayInitialBoard() {
        display.displayBoard();
    }
}