package pl.jj.app.component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.jj.app.model.TicTacToeGameResponse;
import pl.jj.app.model.TicTacToePlayerResponse;
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

    private final Long maxTimeOutOfMove = 60000l;
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

    private Player getPlayer(String nick) throws Exception{
        Player actualPlayer = null;
        if(playerO.getLastAction().getTime() > playerX.getLastAction().getTime()) {
            actualPlayer = playerX;
        } else {
            actualPlayer = playerO;
        }

        if (actualPlayer.userName.equals(nick)) return actualPlayer;
        throw new Exception("Player not found.");
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
        setPlayer(Player.builder().userName(userName).lastAction(new Date()).build(), preferSymbol, false);
    }

    public synchronized void move(String nick, Move move) {
        try {
            if (gameIsInProgress()) {
                Player player = getPlayer(nick);
                if(player != null && !containsMove(move, playerO, playerX)) {
                    player.addMove(move);
                    player.setLastAction(new Date());
                    this.updateGameStatus(playerO, playerX);
                }
            }
        } catch (Throwable t) {
            this.finishGame();
        }

    }

    private synchronized void setPlayer(Player player, String preferSymbol, Boolean second) {
        if (!gameIsInProgress()) {
            switch (preferSymbol) {
                case X:
                    if (playerX != null) {
                        if (second) setPlayer(player, O, true);
                    } else {
                        playerX = player;
                    }
                    break;
                case O:
                    if (playerO != null) {
                        if (second) setPlayer(player, X, true);
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

    private void updateGameStatus(Player playerO, Player playerX) {

        try {

            //Check sb won
            if (playerO.getCountOfMoves() > 2)
                if(areWinMoves(playerO.getHashMoves())) {
                    this.finishGame();
                }

            if (playerX.getCountOfMoves() > 2)
                if(areWinMoves(playerX.getHashMoves())) {
                    this.finishGame();
                }

            TicTacToeGameResponse ticTacToeGameResponse = new TicTacToeGameResponse();
            if (playerO.getCountOfMoves() != 0)
                for (Move move: playerO.getMoves())
                    ticTacToeGameResponse.addPlayerOMove(move);

            if (playerX.getCountOfMoves() != 0)
                for (Move move: playerX.getMoves())
                    ticTacToeGameResponse.addPlayerXMove(move);


            if(observer != null) observer.updateGameContent(ticTacToeGameResponse);

        } catch (Throwable t) {
            finishGame();
        }

    }

    private void finishGame() {
        playerX = null;
        playerO = null;
        inGame = false;
        observer.updateGameContent(TicTacToeGameResponse.finishResponse());
    }

    //Function check that inputted moved are win moves
    private Boolean areWinMoves(Integer[] hashMoves) {
        List<Integer> sumList = new ArrayList<>();
        moveCombination(hashMoves, new Integer[3], sumList, 0,0,3);
        for(Integer sum: sumList)
            if((sum % 3) ==  0 && ((sum / 3) % 3) != 0)
                return true;
        return false;
    }

    private void moveCombination(Integer[] inputArray, Integer[] tempCombination, List<Integer> combination,
                                Integer start, Integer index, Integer r) {

        //Count of digits is consist with the expected one
        if (index.equals(r)) {
            Integer sum = 0;
            for(Integer number: tempCombination) sum += number;
            combination.add(sum);
            return;
        }

        for (int i=start; i<=(inputArray.length-1) && (inputArray.length-1)-i+1 >= r-index; i++) {
            tempCombination[index] = inputArray[i];
            moveCombination(inputArray, tempCombination, combination, i+1, index + 1, r);
        }


    }

    @Getter
    @Setter
    @Builder
    private static class Player {

        private String userName;

        private Date lastAction;

        private List<Move> moves;

        public void addMove(Move move) {
            if (moves == null) moves = new ArrayList<>();
            moves.add(move);
        }

        public Integer getCountOfMoves() {
            if (moves == null) return 0;
            return moves.size();
        }

        public Integer[] getHashMoves() {
            Integer[] hm = new Integer[getCountOfMoves()];
            if (hm.length != 0)
                for (int i=0; i<hm.length; i++)
                    hm[i] = moves.get(i).getHashMove();
            return hm;
        }

    }

    public enum Move {

        LT(1), CT(2), RT(3),
        LC(4), CC(5), RC(6),
        LB(7), CB(8), RB(9);

        private Integer hashMove;

        Move(Integer hashMove) {
            this.hashMove = hashMove;
        }

        public Integer getHashMove() {
            return hashMove;
        }

        public static Move find(String s) {
            for(Move m: Move.values()) {
                if(m.name().equals(s)) return m;
            }
            throw new RuntimeException("Move not found");
        }

    }

    @Scheduled(fixedRate = 500)
    public void refreshPlayerState() {
        if(inGame != null && inGame) {
            try {

                Long currSeconds = System.currentTimeMillis() / 1000l;

                Long pOLastMove = playerO.getLastAction().getTime() / 1000l;
                Long pXLastMove = playerX.getLastAction().getTime() / 1000l;

                TicTacToePlayerResponse playerResponse = new TicTacToePlayerResponse();

                playerResponse.setPlayerOName(playerO.getUserName());
                playerResponse.setPlayerOSecondsRemaining(((currSeconds - pOLastMove) / 60l));
                playerResponse.setPlayerOMinuteRemaining((currSeconds - pOLastMove) - ((currSeconds - pOLastMove) / 60l));

                playerResponse.setPlayerXName(playerX.getUserName());
                playerResponse.setPlayerXSecondsRemaining(((currSeconds - pXLastMove) / 60l));
                playerResponse.setPlayerXMinuteRemaining((currSeconds - pXLastMove) - ((currSeconds - pXLastMove) / 60l));

                observer.updatePlayerState(playerResponse);

            } catch (Throwable t) {
                finishGame();
            }

        }
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
