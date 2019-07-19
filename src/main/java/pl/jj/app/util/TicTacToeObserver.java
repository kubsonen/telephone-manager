package pl.jj.app.util;

import pl.jj.app.model.TicTacToeGameResponse;
import pl.jj.app.model.TicTacToePlayerResponse;

/**
 * @author JNartowicz
 */
public interface TicTacToeObserver {
    void updateGameContent(TicTacToeGameResponse gameResponse);
    void updatePlayerState(TicTacToePlayerResponse playerResponse);
}
