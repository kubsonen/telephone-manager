package pl.jj.app.model;

import lombok.Data;
import pl.jj.app.component.ComponentTicTacToeGame.Move;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JNartowicz
 */
@Data
public class TicTacToeGameResponse {

    private Boolean finishGame;

    private Map<Move, String> gameLayout;

    private void addMove(Move move, String figure) {
        if(gameLayout == null) gameLayout = new HashMap<>();
        gameLayout.put(move, figure);
    }

    public void addPlayerOMove(Move move) {
        addMove(move, "O");
    }

    public void addPlayerXMove(Move move) {
        addMove(move, "X");
    }

    public static TicTacToeGameResponse finishResponse() {
        TicTacToeGameResponse ticTacToeGameResponse = new TicTacToeGameResponse();
        ticTacToeGameResponse.setFinishGame(true);
        return ticTacToeGameResponse;
    }

}
