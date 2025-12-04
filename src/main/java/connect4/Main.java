package connect4;

import java.util.Scanner;

/**
 * Main class for Connect Four game
 * Orchestrates game mode selection
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

        Game game;

        if (mode == 1) {
            // Player vs Player
            System.out.println("=== Player vs Player Mode ===\n");

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

            game = new PlayerVsPlayerGame(scanner, player1Name, player2Name);

        } else {
            // Player vs AI
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine().trim();
            if (playerName.isEmpty()) {
                playerName = "Player 1";
            }
            System.out.println();

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

            game = new PlayerVsAIGame(scanner, playerName, difficulty);
        }

        // Play the game
        game.play();

        scanner.close();
    }
}