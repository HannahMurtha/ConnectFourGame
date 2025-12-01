package connect4;

import connect4.observers.EventType;
import connect4.observers.IObserver;

/**
 * Generates and displays the Connect Four board
 * Observes game events to update display
 */
public class ConnectFourDisplay implements IObserver {

    private char[][] board;
    private final int rows;
    private final int cols;

    public ConnectFourDisplay(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new char[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void displayBoard() {
        System.out.println("\n  1   2   3   4   5   6   7");
        System.out.println("-----------------------------");
        for (int i = 0; i < rows; i++) {
            System.out.print("| ");
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-----------------------------");
        }
        System.out.println();
    }

    public char[][] getBoard() {
        return board;
    }

    @Override
    public void update(EventType eventType, Object data) {
        switch (eventType) {
            case MADE_A_MOVE:
                System.out.println("Move made!");
                displayBoard();
                break;
            case UNDO_MOVE:
                System.out.println("Move undone!");
                displayBoard();
                break;
            case WIN:
                System.out.println(" -- " + data + " WINS! --");
                displayBoard();
                break;
            case LOSE:
                System.out.println(data + " loses!");
                break;
        }
    }
}