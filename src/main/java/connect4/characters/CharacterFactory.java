package connect4.characters;

/**
 * Factory class for creating Player and Opponent instances
 * Play on an 8x8 board
 */
public class CharacterFactory {

    public Player createPlayer(String name) {
        return new Player(name, 'X');
    }

    public Opponent createOpponent(String name, int difficulty) {
        return new Opponent(name, difficulty);
    }
}