package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jj.app.data.ServiceUser;
import pl.jj.app.model.InviteLink;
import pl.jj.app.model.RegisterForm;
import pl.jj.app.util.AppException;

import javax.validation.Valid;

@Controller
@RequestMapping(ControllerRegister.REGISTER_PATH)
public class ControllerRegister {

    public static final String REGISTER_PATH = "/register";
    private static final String REGISTER_FORM_MODEL = "registerForm";
    private static final String REGISTER_ERROR = "registerError";
    private static final String REGISTER_ERROR_TOKEN_NOT_FOUND = "registerErrorTokenNotFound";

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public String showRegister(Model model, @RequestParam String token){

        System.out.println(token);
        InviteLink inviteLink = serviceUser.findByToken(token);

        if(inviteLink == null){
            model.addAttribute(REGISTER_ERROR_TOKEN_NOT_FOUND, true);
        } else {
            RegisterForm registerForm = new RegisterForm();
            registerForm.setRegisterToken(inviteLink.getRegisterToken());
            registerForm.setEmail(inviteLink.getEmail());
            model.addAttribute(REGISTER_FORM_MODEL, registerForm);
        }

        return "register";
    }

    @PostMapping
    public String postRegisterForm(Model model,
                                   @Valid RegisterForm registerForm,
                                   BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "register";
        } else {

            try {
                serviceUser.registerUser(registerForm);
            } catch (AppException a){
                model.addAttribute(REGISTER_ERROR, a.getMessage());
                return "register";
            } catch (Throwable t){
                model.addAttribute(REGISTER_ERROR, "Undefined error.");
                t.printStackTrace();
                return "register";
            }

            return "redirect:/";
        }

    }

}
