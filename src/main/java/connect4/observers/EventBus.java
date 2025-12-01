/*
win
lose
made a move
undo move
 */
package connect4.observers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EventBus for managing observers and publishing events
 * Handles win, lose, made_a_move, and undo_move events
 */
public class EventBus {
    private static EventBus instance;
    private final Map<EventType, List<IObserver>> observers;

    private EventBus() {
        observers = new HashMap<>();
        for (EventType type : EventType.values()) {
            observers.put(type, new ArrayList<>());
        }
    }

    public static EventBus getInstance() {
        if (instance == null) {
            instance = new EventBus();
        }
        return instance;
    }

    public void subscribe(EventType eventType, IObserver observer) {
        observers.get(eventType).add(observer);
    }

    public void unsubscribe(EventType eventType, IObserver observer) {
        observers.get(eventType).remove(observer);
    }

    public void publish(EventType eventType, Object data) {
        for (IObserver observer : observers.get(eventType)) {
            observer.update(eventType, data);
        }
    }
}