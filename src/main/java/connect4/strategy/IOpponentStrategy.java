package connect4.strategy;

/**
 * Strategy interface for opponent AI behavior
 */
public interface IOpponentStrategy {
    int chooseColumn();
    String getStrategyName();
}