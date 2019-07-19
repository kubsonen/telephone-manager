package pl.jj.app.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jj.app.model.TicTacToeGameResponse;
import pl.jj.app.model.TicTacToePlayerResponse;
import pl.jj.app.util.TicTacToeObserver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.security.Principal;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerTicTacToe.PATH)
public class ControllerTicTacToe implements TicTacToeObserver {

    public static final String PATH = "/tic-tac-toe";

    @Autowired
    private ComponentTicTacToeGame game;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping
    public String main() {
        return "tic-tac-toe";
    }

    @PostConstruct
    public void init() {
        game.registerObserver(this);
    }

    @PreDestroy
    public void destroy() {
        game.unregisterObserver();
    }

    @MessageMapping("/choosePlayer")
    public void choosePlayer(Figure figure, Principal user) {
        try {
            if(figure.figure.trim().isEmpty()) return;
            String nickname = user.getName();
            game.setPlayer(nickname, figure.figure.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/move")
    public void catchMove(Move move, Principal user) {
        try {
            ComponentTicTacToeGame.Move m = ComponentTicTacToeGame.Move.find(move.move);
            String nickname = user.getName();
            game.move(nickname, m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateGameContent(TicTacToeGameResponse gameResponse) {
        if(gameResponse.getFinishGame() != null && gameResponse.getFinishGame()) {
            this.template.convertAndSend("/tic-tac-toe-topic/finishGame", "");
        } else {
            this.template.convertAndSend("/tic-tac-toe-topic/updateGameContent", gameResponse);
        }
    }

    @Override
    public void updatePlayerState(TicTacToePlayerResponse playerResponse) {
        this.template.convertAndSend("/tic-tac-toe-topic/updatePlayerState", playerResponse);
    }

    @Data
    public static class Figure{
        private String figure;
    }

    @Data
    public static class Move{
        private String move;
    }

}





















