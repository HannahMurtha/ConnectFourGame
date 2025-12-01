package connect4;

import connect4.characters.CharacterFactory;
import connect4.characters.Player;
import connect4.characters.Opponent;
import connect4.command.Command;
import connect4.command.PlacePieceCommand;
import connect4.observers.EventBus;
import connect4.observers.EventType;
import connect4.state.*;
import connect4.strategy.PlayerStrategy;

import java.util.Scanner;

/**
 * Main class for Connect Four game
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Connect Four Game ===\n");

        // set player name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();

        // default if empty
        if (playerName.isEmpty()) {
            playerName = "Player 1";
        }
        System.out.println();

        // Let player choose difficulty
        System.out.println("Choose opponent difficulty:");
        System.out.println("1 - Easy (Leftmost placement)");
        System.out.println("2 - Medium (Random placement)");
        System.out.println("3 - Hard (Defensive AI)");
        System.out.print("Enter difficulty (1-3): ");

        int difficulty = 2; // default
        try {
            difficulty = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (difficulty < 1 || difficulty > 3) {
                System.out.println("Invalid choice, using Medium difficulty.");
                difficulty = 2;
            }
        } catch (Exception e) {
            System.out.println("Invalid input, using Medium difficulty.");
            scanner.nextLine(); // clear buffer
        }
        System.out.println();

        // 1. Create player and opponent (6 rows, 7 cols)
        Player player = CharacterFactory.createPlayer(playerName, 6, 7);
        Opponent opponent = CharacterFactory.createOpponent("AI Bot", difficulty, 6, 7);

        System.out.println("Characters created:");
        System.out.println(player);
        System.out.println(opponent);
        System.out.println();

        // 2. Create the display/board
        ConnectFourDisplay display = new ConnectFourDisplay(6, 7);
        char[][] board = display.getBoard();

        // 3. Set opponent's strategy (needs board reference)
        opponent.setStrategy(board, player.getPiece());

        // 4. Set up EventBus and subscribe display to events
        EventBus eventBus = EventBus.getInstance();
        eventBus.subscribe(EventType.MADE_A_MOVE, display);
        eventBus.subscribe(EventType.UNDO_MOVE, display);
        eventBus.subscribe(EventType.WIN, display);
        eventBus.subscribe(EventType.LOSE, display);

        // 5. Create win checker
        PlayerStrategy winChecker = new PlayerStrategy(board, 6, 7);

        // 6. Initialize game state
        State currentState = new setupState();
        currentState = currentState.nextState(); // Move to playerTurnState

        // 7. Game loop
        boolean gameRunning = true;
        Command lastCommand = null;

        display.displayBoard();

        while (gameRunning) {
            System.out.println("Current State: " + currentState.getStateName());

            if (currentState.getStateName().equals("PLAYER_TURN")) {
                // Player's turn
                System.out.print(player.getName() + " (" + player.getPiece() + "), choose column (1-7) or 'u' to undo: ");
                String input = scanner.nextLine().trim();

                // Check for undo
                if (input.equalsIgnoreCase("u")) {
                    if (currentState.canUndo() && lastCommand != null) {
                        if (lastCommand.undo()) {
                            eventBus.publish(EventType.UNDO_MOVE, null);
                            System.out.println("Move undone!");
                        }
                    } else {
                        System.out.println("Cannot undo!");
                    }
                    continue;
                }

                // Parse column input
                try {
                    int col = Integer.parseInt(input) - 1;

                    if (col < 0 || col > 6) {
                        System.out.println("Invalid column! Choose 1-7.");
                        continue;
                    }

                    // Create and execute command
                    Command command = new PlacePieceCommand(board, col, player.getPiece(), 6);

                    if (command.execute()) {
                        lastCommand = command;
                        eventBus.publish(EventType.MADE_A_MOVE, command);

                        // Check for win
                        if (winChecker.checkWin(command.getRow(), command.getColumn(), player.getPiece())) {
                            currentState = new gameOverState(player.getName());
                            eventBus.publish(EventType.WIN, player.getName());
                            eventBus.publish(EventType.LOSE, opponent.getName());
                            gameRunning = false;
                        } else if (isBoardFull(board)) {
                            currentState = new gameOverState("Draw");
                            eventBus.publish(EventType.WIN, "Draw");
                            gameRunning = false;
                        } else {
                            // Switch to opponent turn
                            currentState = new opponentTurnState(opponent, board, 6);
                        }
                    } else {
                        System.out.println("Column is full! Try another.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Enter a number 1-7 or 'u' to undo.");
                }

            } else if (currentState.getStateName().equals("OPPONENT_TURN")) {
                // Opponent's turn (AI)
                System.out.println(opponent.getName() + "'s turn...");

                int aiCol = opponent.chooseColumn();

                if (aiCol == -1) {
                    System.out.println("Board is full! It's a draw.");
                    currentState = new gameOverState("Draw");
                    eventBus.publish(EventType.WIN, "Draw");
                    gameRunning = false;
                    continue;
                }

                Command command = new PlacePieceCommand(board, aiCol, opponent.getPiece(), 6);

                if (command.execute()) {
                    System.out.println(opponent.getName() + " placed piece in column " + (aiCol + 1));
                    eventBus.publish(EventType.MADE_A_MOVE, command);

                    // Check for win
                    if (winChecker.checkWin(command.getRow(), command.getColumn(), opponent.getPiece())) {
                        currentState = new gameOverState(opponent.getName());
                        eventBus.publish(EventType.WIN, opponent.getName());
                        eventBus.publish(EventType.LOSE, player.getName());
                        gameRunning = false;
                    } else if (isBoardFull(board)) {
                        currentState = new gameOverState("Draw");
                        eventBus.publish(EventType.WIN, "Draw");
                        gameRunning = false;
                    } else {
                        // Switch back to player turn
                        currentState = new playerTurnState(opponent, board, 6);
                    }
                }
            } else if (currentState.getStateName().equals("GAME_OVER")) {
                gameRunning = false;
            }
        }

        System.out.println("\n=== Game Over ===");
        if (currentState.getStateName().equals("GAME_OVER")) {
            System.out.println("Winner: " + ((gameOverState) currentState).getWinner());
        }

        scanner.close();
    }

    private static boolean isBoardFull(char[][] board) {
        for (int c = 0; c < 7; c++) {
            if (board[0][c] == ' ') {
                return false;
            }
        }
        return true;
    }
}