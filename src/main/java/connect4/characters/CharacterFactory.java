package connect4.characters;

/**
 * Factory class for creating Player and Opponent instances
 * Play on a 7x7 board
 */
public class CharacterFactory {

    public Player createPlayer(String name) {
        return new Player(name, 'X');
    }

    public Opponent createOpponent(String name, int difficulty) {
        return new Opponent(name, difficulty);
    }
}