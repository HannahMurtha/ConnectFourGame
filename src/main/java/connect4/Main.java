package connect4;

import connect4.characters.CharacterFactory;
import connect4.characters.Character;
import connect4.characters.Player;
import connect4.characters.Opponent;

/**
 * Main class to demonstrate Connect Four game setup
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Connect Four Game Demo ===\n");

        // 1. Create the factory
        CharacterFactory factory = new CharacterFactory();

        // 2. Create player and opponent
        Player player = factory.createPlayer("Alice");
        Opponent opponent = factory.createOpponent("AI Bot", 2);

        // 3. Display character info
        System.out.println("Characters created:");
        System.out.println(player);
        System.out.println(opponent);
        System.out.println();

        // 4. Demonstrate polymorphism with Character interface
        Character[] characters = {player, opponent};
        System.out.println("Using Character interface:");
        for (Character c : characters) {
            System.out.println("  " + c.getName() + " plays with piece: " + c.getPiece());
        }
        System.out.println();

        // 5. Create the game board
        ConnectFourDisplay game = new ConnectFourDisplay();
        System.out.println("Game board created (8x8)");
        game.displayBoard();

        // 6. Simulate some moves
        System.out.println("Simulating a few moves...\n");

        game.placePiece(3, player.getPiece());   // Alice plays column 3
        game.placePiece(3, opponent.getPiece()); // AI plays column 3
        game.placePiece(4, player.getPiece());   // Alice plays column 4
        game.placePiece(2, opponent.getPiece()); // AI plays column 2
        game.placePiece(4, player.getPiece());   // Alice plays column 4
        game.placePiece(5, opponent.getPiece()); // AI plays column 5

        game.displayBoard();

        // 7. Show game stats
        game.displayGameStats();

        // 8. Demonstrate undo
        System.out.println("Undoing last move (column 5)...\n");
        game.removePiece(5);
        game.displayBoard();

        System.out.println("=== Demo Complete ===");
    }
}