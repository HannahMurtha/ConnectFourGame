package connect4;

import connect4.characters.CharacterFactory;
import connect4.characters.Player;
import connect4.characters.Opponent;
import connect4.observers.EventBus;
import connect4.observers.EventType;
import connect4.state.*;
import connect4.strategy.PlayerStrategy;
import connect4.command.Command;
import connect4.command.PlacePieceCommand;

import java.util.Scanner;

/**
 * Main class for Connect Four game
 * Now properly uses State pattern - states handle their own behavior
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Connect Four Game ===\n");

        // Choose game mode
        System.out.println("Choose game mode:");
        System.out.println("1 - Player vs Player");
        System.out.println("2 - Player vs AI");
        System.out.print("Enter mode (1-2): ");

        int mode = 2; // default to AI
        try {
            mode = scanner.nextInt();
            scanner.nextLine(); // consume newline
            if (mode < 1 || mode > 2) {
                System.out.println("Invalid choice, using Player vs AI mode.");
                mode = 2;
            }
        } catch (Exception e) {
            System.out.println("Invalid input, using Player vs AI mode.");
            scanner.nextLine(); // clear buffer
        }
        System.out.println();

        if (mode == 1) {
            playPlayerVsPlayer(scanner);
        } else {
            playPlayerVsAI(scanner);
        }

        scanner.close();
    }

    /**
     * Player vs Player mode - Simple implementation without extra state files
     */
    private static void playPlayerVsPlayer(Scanner scanner) {
        System.out.println("=== Player vs Player Mode ===\n");

        // Get player names
        System.out.print("Enter Player 1 name: ");
        String player1Name = scanner.nextLine().trim();
        if (player1Name.isEmpty()) {
            player1Name = "Player 1";
        }

        System.out.print("Enter Player 2 name: ");
        String player2Name = scanner.nextLine().trim();
        if (player2Name.isEmpty()) {
            player2Name = "Player 2";
        }
        System.out.println();

        System.out.println(player1Name + " plays with: X");
        System.out.println(player2Name + " plays with: O");
        System.out.println();

        // create the display/board
        ConnectFourDisplay display = new ConnectFourDisplay(6, 7);
        char[][] board = display.getBoard();

        // set up EventBus and subscribe display
        EventBus eventBus = EventBus.getInstance();
        eventBus.subscribe(EventType.MADE_A_MOVE, display);
        eventBus.subscribe(EventType.UNDO_MOVE, display);
        eventBus.subscribe(EventType.WIN, display);
        eventBus.subscribe(EventType.LOSE, display);

        PlayerStrategy winChecker = new PlayerStrategy(board, 6, 7);

        display.displayBoard();

        // Simple PvP game loop
        boolean gameRunning = true;
        Command lastCommand = null;
        boolean isPlayer1Turn = true;

        while (gameRunning) {
            String currentPlayerName = isPlayer1Turn ? player1Name : player2Name;
            char currentPiece = isPlayer1Turn ? 'X' : 'O';

            System.out.println("Current State: " + (isPlayer1Turn ? "PLAYER_1_TURN" : "PLAYER_2_TURN"));
            System.out.print(currentPlayerName + " (" + currentPiece + "), choose column (1-7) or 'u' to undo: ");

            String input = scanner.nextLine().trim();

            // Check for undo
            if (input.equalsIgnoreCase("u")) {
                if (lastCommand != null && lastCommand.undo()) {
                    eventBus.publish(EventType.UNDO_MOVE, null);
                    System.out.println("Move undone!");
                    isPlayer1Turn = !isPlayer1Turn; // Switch back
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
                Command command = new PlacePieceCommand(board, col, currentPiece, 6);

                if (!command.execute()) {
                    System.out.println("Column is full! Try another.");
                    continue;
                }

                lastCommand = command;
                eventBus.publish(EventType.MADE_A_MOVE, command);

                // Check for win
                if (winChecker.checkWin(command.getRow(), command.getColumn(), currentPiece)) {
                    eventBus.publish(EventType.WIN, currentPlayerName);
                    String loserName = isPlayer1Turn ? player2Name : player1Name;
                    eventBus.publish(EventType.LOSE, loserName);
                    gameRunning = false;
                } else if (isBoardFull(board)) {
                    eventBus.publish(EventType.WIN, "Draw");
                    gameRunning = false;
                } else {
                    // Switch turns
                    isPlayer1Turn = !isPlayer1Turn;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a number 1-7 or 'u' to undo.");
            }
        }

        System.out.println("\n=== Game Over ===");
    }

    /**
     * Player vs AI mode
     */
    private static void playPlayerVsAI(Scanner scanner) {
        // Get player name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine().trim();

        // Use default if empty
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

        // create player and opponent
        Player player = CharacterFactory.createPlayer(playerName, 6, 7);
        Opponent opponent = CharacterFactory.createOpponent("AI Bot", difficulty, 6, 7);

        System.out.println("Characters created:");
        System.out.println(player);
        System.out.println(opponent);
        System.out.println();

        // create the display/board
        ConnectFourDisplay display = new ConnectFourDisplay(6, 7);
        char[][] board = display.getBoard();

        // set opponent's strategy
        opponent.setStrategy(board, player.getPiece());

        // set up EventBus and subscribe display
        EventBus eventBus = EventBus.getInstance();
        eventBus.subscribe(EventType.MADE_A_MOVE, display);
        eventBus.subscribe(EventType.UNDO_MOVE, display);
        eventBus.subscribe(EventType.WIN, display);
        eventBus.subscribe(EventType.LOSE, display);

        PlayerStrategy winChecker = new PlayerStrategy(board, 6, 7); // win checker
        GameContext context = new GameContext(scanner, player, opponent, board, 6, eventBus, winChecker); // Create game context (contains all game objects)

        State currentState = new setupState(); // initial game state
        display.displayBoard(); // display board

        // game loop - states handles it
        while (!currentState.isGameOver()) {
            currentState = currentState.handleTurn(context);
        }

        currentState.handleTurn(context); // final winner
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