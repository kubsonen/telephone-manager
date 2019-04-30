package pl.jj.app.component;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerUser.USER_PATH)
public class ControllerUser {

    public static final String USER_PATH = "/user";

}
