package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jj.app.data.RepositoryDictionary;
import pl.jj.app.data.ServiceDictionary;
import pl.jj.app.data.ServiceTelephone;
import pl.jj.app.entity.Dictionary;
import pl.jj.app.entity.Telephone;
import pl.jj.app.util.Const;
import pl.jj.app.util.InsertDictionary;
import pl.jj.app.util.ShowMode;

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

    private static final String ACTUAL_MODE_ATTRIBUTE = "ACTUAL_MODE";
    private static final String ACTUAL_ROWS_ON_PAGE_ATTRIBUTE = "ACTUAL_ROWS_ON_PAGE";

    @Autowired
    private ServiceTelephone serviceTelephone;

    @Autowired
    private ServiceDictionary serviceDictionary;

    @Autowired
    private RepositoryDictionary repositoryDictionary;

    @GetMapping
    @InsertDictionary({ShowMode.SHOW_MODES, Const.DEF_DIC_ROWS_ON_PAGE})
    public String showTelephonePage(Model model,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_COUNTER_MODE, required = false) String counterMode,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_ACTUAL_PAGE, required = false) String actualPage,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_ROWS_ON_PAGE, required = false) String rowsOnPage){

        //Initialize default dictionary values
        if(Const.dicNeedToCreate()) serviceDictionary.initializeDefaultDictionaries();
        //Set actual select values
        ShowMode showMode = resolveCounterMode(model, counterMode);
        Integer integer = resolveRowsOnPage(model, rowsOnPage);

        model.addAttribute(TELEPHONES_ATTR, serviceTelephone.findTop10Telephones());
        return "telephone";
    }

    private Integer resolveRowsOnPage(Model model, String rowsOnPage) {

        Integer val = Integer.valueOf(Const.DIC_ROWS_ON_PAGE_10);
        try{
            Integer parVal = Integer.valueOf(rowsOnPage);
            if(parVal > Const.MAX_ROWS_ON_PAGES){
                val = Const.MAX_ROWS_ON_PAGES;
            } else {
                val = parVal;
            }
        } catch (Exception e){
//            Fail
        }

        model.addAttribute(ACTUAL_ROWS_ON_PAGE_ATTRIBUTE, String.valueOf(val));
        return val;

    }

    private ShowMode resolveCounterMode(Model model, String counterMode) {
        ShowMode val = ShowMode.DAY;

        ShowMode fromPar = ShowMode.findByName(counterMode);
        if(fromPar != null){
            val = fromPar;
        }

        model.addAttribute(ACTUAL_MODE_ATTRIBUTE, val);
        return val;
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
            serviceTelephone.removeTelephone(Long.valueOf(telephoneId.replace(" ", "")));
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
