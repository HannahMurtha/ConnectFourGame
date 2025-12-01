package connect4;

import connect4.characters.CharacterFactory;
import connect4.characters.Player;
import connect4.characters.Opponent;
import connect4.observers.EventBus;
import connect4.observers.EventType;
import connect4.state.*;
import connect4.strategy.PlayerStrategy;

import java.util.Scanner;

/**
 * Main class for Connect Four game
 * Now properly uses State pattern - states handle their own behavior
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Connect Four Game ===\n");

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

        State currentState = new setupState(); // intial game state
        display.displayBoard(); // display board

        // game loop - states handles it
        while (!currentState.isGameOver()) {
            currentState = currentState.handleTurn(context);
        }

        currentState.handleTurn(context); // final winner
        scanner.close();
    }
}