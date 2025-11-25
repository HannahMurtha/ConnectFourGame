//package connect4;
//import connect4.characters.Player;
//import connect4.characters.Opponent;
//import connect4.strategy.WinStrategy;
//import connect4.observers.EventBus;
//
///**
// * Displays and manages the Connect Four game board
// * Works with State Pattern to track game flow
// */
//public class ConnectFourDisplay {
//    private char[][] board;
//    private int rows; // 7
//    private int cols; // 7
//    private int numOfTurns;
//    private String winner;
//    private WinStrategy winStrategy;
//    private EventBus eventBus;
//
//    public ConnectFourDisplay() { // constructor of the board
//        this.rows = 7;
//        this.cols = 7;
//        this.board = new char[7][7]; // mostly *
//        this.numOfTurns = 0;
//        this.eventBus = new EventBus();
//        initializeBoard();
//    }
//
//
//    private void initializeBoard() { // all empty slots
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                board[i][j] = '*';
//            }
//        }
//    }
//
//    public void displayBoard() { // current board state
//        System.out.println("\n=== Connect Four ===");
//        System.out.println("Grid Size: " + rows + "x" + cols);
//        System.out.println();
//
//        // Column numbers
//        System.out.print("  "); // colums
//        for (int j = 0; j < cols; j++) {
//            System.out.print(j + " ");
//        }
//        System.out.println();
//
//        for (int i = 0; i < rows; i++) { // display the pieces
//            System.out.print("  ");
//            for (int j = 0; j < cols; j++) {
//                System.out.print(board[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//    }
//
//    public boolean placePiece(int col, char piece) { // places a piece w/ gravity
//        if (col < 0 || col >= cols) {
//            return false;
//        }
//
//        for (int row = rows - 1; row >= 0; row--) { // find the lowest empty row in the col
//            if (board[row][col] == '*') {
//                board[row][col] = piece;
//                numOfTurns++;
//                return true;
//            }
//        }
//
//        return false; // col is full
//    }
//
//    /**
//     * Remove a piece from a column (for undo functionality)
//     */
//    public boolean removePiece(int col) { // remove a piece from a col (UNDO)
//        if (col < 0 || col >= cols) {
//            return false;
//        }
//
//        for (int row = 0; row < rows; row++) { // highest non-empty row in this col
//            if (board[row][col] != '*') {
//                board[row][col] = '*';
//                numOfTurns--;
//                return true;
//            }
//        }
//
//        return false; // full
//    }
//
//    public boolean isColumnFull(int col) { // is a col full
//        if (col < 0 || col >= cols) {
//            return true;
//        }
//        return board[0][col] != '*';
//    }
//
//
//    public boolean isBoardFull() { // is the board full w no winner? DRAW
//        for (int j = 0; j < cols; j++) {
//            if (board[0][j] == '*') {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public void resetBoard() { // back to intial state
//        initializeBoard();
//        numOfTurns = 0;
//        winner = null;
//        winStrategy = null;
//    }
//
//    // getters and setters
//    public char[][] getBoard() {
//        return board;
//    }
//
//    public int getRows() {
//        return rows;
//    }
//
//    public int getCols() {
//        return cols;
//    }
//
//    public EventBus getEventBus() {
//        return eventBus;
//    }
//
//    public int getNumOfTurns() {
//        return numOfTurns;
//    }
//
//    public String getWinner() {
//        return winner;
//    }
//
//    public void setWinner(String winner) {
//        this.winner = winner;
//    }
//
//    public WinStrategy getWinStrategy() {
//        return winStrategy;
//    }
//
//    public void setWinStrategy(WinStrategy winStrategy) {
//        this.winStrategy = winStrategy;
//    }
//
//    public void displayGameStats() { //displays game stats
//        System.out.println("\n=== Game Statistics ===");
//        System.out.println("Grid Size: " + rows + "x" + cols);
//        System.out.println("Turns Played: " + numOfTurns);
//        if (winner != null) {
//            System.out.println("Winner: " + winner);
//            if (winStrategy != null) {
//                System.out.println("Win Type: " + winStrategy.getClass().getSimpleName());
//            }
//        }
//        System.out.println("======================\n");
//    }
//}