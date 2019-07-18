package pl.jj.app.util;

/**
 * @author JNartowicz
 */
public interface TicTacToeSubject {
    void registerObserver(TicTacToeObserver observer);
    void unregisterObserver();
}
