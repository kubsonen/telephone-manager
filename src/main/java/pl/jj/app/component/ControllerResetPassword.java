package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jj.app.data.ServiceUser;
import pl.jj.app.model.ResetPassword;
import pl.jj.app.model.User;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerResetPassword.RESET_CONTROLLER_PATH)
public class ControllerResetPassword {

    public static final String RESET_CONTROLLER_PATH = "/resetPassword";
    public static final String RESET_PASSWORD_ERROR = "resetPasswordError";
    public static final String RESET_PASSWORD_SUCCESS = "resetPasswordSuccess";

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public String showResetForm(
            Model model,
            @RequestParam(required = false) String token
    ) {

        try {
            if (token != null && !token.isEmpty()) {
                User user = serviceUser.applyResetPassword(token);
                model.addAttribute(RESET_PASSWORD_SUCCESS, "Reset password success for email: " + user.getEmail());
            }

        } catch (Throwable t) {
            t.printStackTrace();
            model.addAttribute(RESET_PASSWORD_ERROR, true);
        }


        return "reset";
    }

    @PostMapping
    public String postReset(Model model, @ModelAttribute ResetPassword resetPassword) {

        try {
            serviceUser.resetPasswordRequest(resetPassword);
            model.addAttribute(RESET_PASSWORD_SUCCESS, "Token was successfully sent on your email.");
        } catch (Throwable t) {
            t.printStackTrace();
            model.addAttribute(RESET_PASSWORD_ERROR, true);
        }

        return "reset";
    }

}
