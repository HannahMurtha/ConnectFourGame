package connect4.strategy;

/**
 * Strategy interface for checking different win conditions
 */
public interface WinStrategy {
    boolean checkWin(char[][] board, char piece);
    String getWinType();
}