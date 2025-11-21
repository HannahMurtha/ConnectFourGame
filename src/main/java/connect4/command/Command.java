package connect4.command;

/**
 * Command interface for Connect Four moves.
 * Encapsulates a move action with execute and undo functionality.
 */
public interface Command {

    boolean execute(); // true if piece placement was valid

    boolean undo(); // can we undo?

    int getColumn(); // get the col where this command places/placed a piece

    int getRow(); // get the row where this command places/paced a piece. return the row number OR -1 if not placed

    char getPiece(); // Was the piece 'X' or 'O'
}