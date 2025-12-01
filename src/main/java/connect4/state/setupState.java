package connect4.state;

/**
 * Initial state before game starts
 * Automatically transitions to player turn
 */
public class setupState implements State {

    @Override
    public State handleTurn(GameContext context) {
        System.out.println("Setting up game...");
        return new playerTurnState(); // next turn :)
    }

    @Override
    public String getStateName() {
        return "SETUP";
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}