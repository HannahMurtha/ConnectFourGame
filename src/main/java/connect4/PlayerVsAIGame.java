package connect4;
import connect4.characters.CharacterFactory;
import connect4.characters.Player;
import connect4.characters.Opponent;
import connect4.observers.EventType;
import connect4.state.*;
import connect4.strategy.PlayerStrategy;

import java.util.Scanner;

/**
 * Player vs AI game mode
 */
public class PlayerVsAIGame extends Game {
    private final Scanner scanner;
    private final Player player;
    private final Opponent opponent;
    private final GameContext context;

    public PlayerVsAIGame(Scanner scanner, String playerName, int difficulty) {
        super();
        this.scanner = scanner;
        this.player = CharacterFactory.createPlayer(playerName, rows, cols);
        this.opponent = CharacterFactory.createOpponent("AI Bot", difficulty, rows, cols);

        // Set opponent's strategy
        opponent.setStrategy(board, player.getPiece());

        System.out.println("Characters created:");
        System.out.println(player);
        System.out.println(opponent);
        System.out.println();

        // Create game context for state pattern
        this.context = new GameContext(scanner, player, opponent, board, rows, eventBus, winChecker);
    }

    @Override
    public void play() {
        State currentState = new setupState(); // initial game state
        displayInitialBoard();

        // game loop - states handle it
        while (!currentState.isGameOver()) {
            currentState = currentState.handleTurn(context);
        }

        currentState.handleTurn(context); // final winner
    }
}