package pl.jj.app.component;

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
@RequestMapping(ControllerTelephone.TELEPHONE_PATH)
public class ControllerTelephone {

    public static final String TELEPHONE_PATH = "/telephone";
    public static final String TELEPHONE_DELETE_PATH = "/delete";
    public static final String TELEPHONES_ATTR = "telephones";

    private static final String ADD_MODEL_TELEPHONE_ATTR_COUNTER_MODE = "cm";
    private static final String ADD_MODEL_TELEPHONE_ATTR_ACTUAL_PAGE = "page";
    private static final String ADD_MODEL_TELEPHONE_ATTR_ROWS_ON_PAGE = "pr";

    @Autowired
    private ServiceTelephone serviceTelephone;

    @GetMapping
    public String showTelephonePage(Model model,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_COUNTER_MODE, required = false) String counterMode,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_ACTUAL_PAGE, required = false) String actualPage,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_ROWS_ON_PAGE, required = false) String rowsOnPage){



        model.addAttribute(TELEPHONES_ATTR, serviceTelephone.findTop10Telephones());
        return "telephone";
    }

    @PostMapping
    public String addTelephone( Model model,
                                @ModelAttribute Telephone telephone,
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
    public String deleteTelephone(  Model model,
                                    @PathVariable("phoneId") String telephoneId,
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
