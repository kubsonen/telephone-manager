package pl.jj.app.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
public class TicTacToeGame {

    private static final String X = "X";
    private static final String O = "O";

    private static Player playerX;

    private static Player playerO;

    private static Boolean inGame;

    private static TicTacToeObserver observer;

    private TicTacToeGame() {}

    private static Boolean gameIsInProgress() {
        if (inGame == null) return false;
        return inGame;
    }

    public synchronized static void registerObserver(TicTacToeObserver o) {
        observer = o;
    }

    public synchronized static void setPlayer(String userName, String preferSymbol) {
        setPlayer(Player.builder().userName(userName).lastAction(new Date()).build(), preferSymbol);
    }

    private synchronized static void setPlayer(Player player, String preferSymbol) {
        if (gameIsInProgress()) {
            switch (preferSymbol) {
                case X:
                    if (playerX != null) {
                        setPlayer(player, O);
                    } else {
                        playerX = player;
                    }
                    break;
                case O:
                    if (playerO != null) {
                        setPlayer(player, X);
                    } else {
                        playerO = player;
                    }
                    break;
                default:
                    return;
            }
            startGame();
        }
    }

    private synchronized static void startGame() {
        if (playerO != null && playerX != null) {
            inGame = true;
        }
    }

    @Getter
    @Setter
    @Builder
    private static class Player {

        private String userName;

        private Date lastAction;

        private List<Moves> moves;

    }

    enum Moves {
        LT, CT, RT, LC, CC, RC, LB, CB, RB
    }

}
