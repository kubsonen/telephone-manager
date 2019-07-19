package pl.jj.app.model;

import lombok.Data;
import pl.jj.app.component.ComponentTicTacToeGame;

import java.util.Map;

/**
 * @author JNartowicz
 */
@Data
public class TicTacToePlayerResponse {

    private String playerOName;

    private Long playerOMinuteRemaining;

    private Long playerOSecondsRemaining;

    private String playerXName;

    private Long playerXMinuteRemaining;

    private Long playerXSecondsRemaining;

}
