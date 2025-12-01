/*
tell game when game over
says who won
 */
package connect4.observers;

/**
 * Observer interface for game events
 * Tells game when game is over, who won
 */
public interface IObserver {
    void update(EventType eventType, Object data);
}