package connect4.characters;

import connect4.strategy.IOpponentStrategy;
import connect4.strategy.StrategyFactory;

/**
 * Represents an AI opponent in Connect Four game
 * Contains opponent information, difficulty level, and board configuration.
 */
public class Opponent implements Character {
    private final String name;
    private final int difficulty; // 1 = dumb, 2 = random, 3 = defensive
    private final int rows;
    private final int cols;
    private final char piece; // Opponent's game piece ('O')
    private IOpponentStrategy strategy;

    public Opponent(String name, int difficulty, int rows, int cols, char piece) {
        this.name = name;
        this.difficulty = difficulty;
        this.rows = rows;
        this.cols = cols;
        this.piece = piece;
    }

    // Set the strategy (call this after board is created)
    public void setStrategy(char[][] board, char playerPiece) {
        this.strategy = StrategyFactory.createStrategy(
                difficulty, board, rows, cols, piece, playerPiece
        );
    }

    // Get AI's column choice
    public int chooseColumn() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set! Call setStrategy() first.");
        }
        return strategy.chooseColumn();
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public char getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "Opponent{" +
                "name='" + name + '\'' +
                ", difficulty=" + difficulty +
                ", boardSize=" + rows + "x" + cols +
                ", piece=" + piece +
                '}';
    }
}