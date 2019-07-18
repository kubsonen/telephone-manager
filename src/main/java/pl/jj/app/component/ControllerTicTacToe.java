package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jj.app.util.TicTacToeObserver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerTicTacToe.PATH)
public class ControllerTicTacToe implements TicTacToeObserver {

    public static final String PATH = "/tic-tac-toe";

    @Autowired
    private ComponentTicTacToeGame game;

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

    @Override
    public void update() {

    }

}
