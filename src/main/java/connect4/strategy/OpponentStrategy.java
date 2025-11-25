package connect4.strategy;

/**
 * Strategy interface for AI opponent move selection
 */
public interface OpponentStrategy {
    int chooseColumn(char[][] board);
}