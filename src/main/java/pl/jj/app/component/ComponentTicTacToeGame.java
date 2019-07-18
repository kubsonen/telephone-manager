package pl.jj.app.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.jj.app.util.TicTacToeObserver;
import pl.jj.app.util.TicTacToeSubject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ComponentTicTacToeGame implements TicTacToeSubject {

    private final String X = "X";
    private final String O = "O";

    private Player playerX;

    private Player playerO;

    private Boolean inGame;

    private TicTacToeObserver observer;

    private Boolean gameIsInProgress() {
        if (inGame == null) return false;
        return inGame;
    }

    private Player getPlayer(String nick) {
        if (playerO != null && playerO.userName.equals(nick)) return playerO;
        if (playerX != null && playerX.userName.equals(nick)) return playerX;
        return null;
    }

    private boolean containsMove(Move move, Player... players) {
        if (players != null) {
            for (Player  p: players) {
                if (p.moves != null) {
                    for (Move m: p.moves)
                        if (m.equals(move)) return true;
                }
            }
        }
        return false;
    }

    public synchronized void setPlayer(String userName, String preferSymbol) {
        setPlayer(Player.builder().userName(userName).lastAction(new Date()).build(), preferSymbol);
    }

    private synchronized void setPlayer(Player player, String preferSymbol) {
        if (!gameIsInProgress()) {
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

    private synchronized void startGame() {
        if (playerO != null && playerX != null) {
            inGame = true;
        }
    }

    private synchronized void move(String nick, Move move) {
        if (gameIsInProgress()) {
            Player player = getPlayer(nick);
            if(player != null && !containsMove(move, playerO, playerX)) {
                player.addMove(move);
                this.updateGameStatus();
            }
        }
    }

    private void updateGameStatus() {
        
        if(observer != null) observer.update();
    }

    @Getter
    @Setter
    @Builder
    private static class Player {

        private String userName;

        private Date lastAction;

        private List<Move> moves;

        public void addMove(Move move) {
            if (moves != null) moves = new ArrayList<>();
            moves.add(move);
        }

    }

    enum Move {
        LT, CT, RT, LC, CC, RC, LB, CB, RB
    }

    @Override
    public void registerObserver(TicTacToeObserver observer) {
        this.observer = observer;
    }

    @Override
    public void unregisterObserver() {
        this.observer = null;
    }
}
