package pl.jj.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jj.app.data.ServiceTelephone;
import pl.jj.app.entity.Telephone;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(TelephoneController.TELEPHONE_PATH)
public class TelephoneController {

    public static final String TELEPHONE_PATH = "/telephone";
    public static final String TELEPHONE_DELETE_PATH = "/delete";
    public static final String TELEPHONES_ATTR = "telephones";

    @Autowired
    private ServiceTelephone serviceTelephone;

    @GetMapping
    public String showTelephonePage(Model model){
        model.addAttribute(TELEPHONES_ATTR, serviceTelephone.findTop10Telephones());
        return "telephone";
    }

    @PostMapping
    public String addTelephone(@ModelAttribute Telephone telephone,
                               @RequestHeader(value = "referer", required = false) final String referer){

        //Add action
        serviceTelephone.addTelephone(telephone);

        if(referer != null){
            return "redirect:" + referer;
        } else {
            return "redirect:" + TELEPHONE_PATH;
        }

    }

    @GetMapping(TELEPHONE_DELETE_PATH + "/{phoneId}")
    public String deleteTelephone(@PathVariable("phoneId") String telephoneId,
                                @RequestHeader(value = "referer", required = false) final String referer){

        try{
            //Delete action
            serviceTelephone.removeTelephone(Long.valueOf(telephoneId));
        } catch (Throwable t){
            t.printStackTrace();
        }


        if(referer != null){
            return "redirect:" + referer;
        } else {
            return "redirect:" + TELEPHONE_PATH;
        }

    }

}
