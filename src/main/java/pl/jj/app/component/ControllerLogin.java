package pl.jj.app.component;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping("/login")
public class ControllerLogin {

    @GetMapping
    public String showLogin() {
        return "login";
    }

}
