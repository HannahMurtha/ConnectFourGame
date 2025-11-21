package connect4.command;

/**
 * Command for placing a piece on the board
 * Supports execute and undo operations
 */
public class PlacePieceCommand implements Command {

    private final char[][] board; // Reference to the game board
    private final int column; // col
    private final char piece; // piece type
    private int row; // Row where piece was placed (determined during execute)
    private final int rows;

    public PlacePieceCommand(char[][] board, int column, char piece, int rows) {
        this.board = board;
        this.column = column; // where place piece
        this.piece = piece; // place 'X' or 'O'
        this.rows = rows; // # rows
        this.row = -1; // Not yet placed
    }

    @Override
    public boolean execute() {
        // Find the lowest empty row in the column (gravity effect)
        for (int r = rows - 1; r >= 0; r--) {
            if (board[r][column] == ' ') {
                board[r][column] = piece;
                this.row = r;
                return true;
            }
        }
        // Column is full
        System.out.println("Column " + column + " is full!");
        return false;
    }

    @Override
    public boolean undo() {
        if (row == -1) { // cant undo something that was never done
            System.out.println("Cannot undo - command was never executed.");
            return false;
        }

        if (board[row][column] == piece) { // else remove that piece
            board[row][column] = ' ';
            row = -1; // reset row to -1
            return true;
        }

        System.out.println("Cannot undo - board state has changed.");
        return false;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public char getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "PlacePieceCommand{" +
                "piece=" + piece +
                ", column=" + column +
                ", row=" + row +
                '}';
    }
}