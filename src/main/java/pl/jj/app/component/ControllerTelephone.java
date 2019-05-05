package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jj.app.data.RepositoryDictionary;
import pl.jj.app.data.ServiceDictionary;
import pl.jj.app.data.ServiceTelephone;
import pl.jj.app.model.Telephone;
import pl.jj.app.util.Const;
import pl.jj.app.util.InsertDictionary;
import pl.jj.app.util.ShowMode;

import java.util.Collections;
import java.util.List;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerTelephone.TELEPHONE_PATH)
public class ControllerTelephone {

    public static final String TELEPHONE_PATH = "";
    public static final String TELEPHONE_DELETE_PATH = "/delete";
    public static final String TELEPHONES_ATTR = "telephones";

    private static final String ADD_MODEL_TELEPHONE_ATTR_COUNTER_MODE = "cm";
    private static final String ADD_MODEL_TELEPHONE_ATTR_ACTUAL_PAGE = "page";
    private static final String ADD_MODEL_TELEPHONE_ATTR_ROWS_ON_PAGE = "pr";
    private static final String ADD_MODEL_TELEPHONE_ATTR_CHAT_VISIBLE = "chat";

    private static final String ACTUAL_MODE_ATTRIBUTE = "ACTUAL_MODE";
    private static final String ACTUAL_ROWS_ON_PAGE_ATTRIBUTE = "ACTUAL_ROWS_ON_PAGE";

    private static final String PAGE_NEXT_ATTRIBUTE = "PAGE_NEXT_ATTRIBUTE";
    private static final String PAGE_PREVIOUS_ATTRIBUTE = "PAGE_PREVIOUS_ATTRIBUTE";
    private static final String PAGE_ACTUAL_ATTRIBUTE = "PAGE_ACTUAL_ATTRIBUTE";
    private static final String PAGE_COUNT_ATTRIBUTE = "PAGE_COUNT_ATTRIBUTE";

    private static final String TELEPHONES_QUANTITY = "TELEPHONES_QUANTITY";

    private static final String CHAT_VISIBILITY = "CHAT_VISIBILITY";
    private static final String CHAT_VISIBLE_TRUE_VALUE = "ctrue";

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
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_ROWS_ON_PAGE, required = false) String rowsOnPage,
                                    @RequestParam(name = ADD_MODEL_TELEPHONE_ATTR_CHAT_VISIBLE, required = false) String chatVisible){

        //Initialize default dictionary values
        if(Const.dicNeedToCreate()) serviceDictionary.initializeDefaultDictionaries();
        //Set actual select values
        ShowMode showMode = resolveCounterMode(model, counterMode);
        Integer rop = resolveRowsOnPage(model, rowsOnPage);

        //Get an info about pages
        int countOfRowsGenerally = serviceTelephone.getCountOfFilteredTelephones(showMode);
        model.addAttribute(TELEPHONES_QUANTITY, countOfRowsGenerally);

        //Count pages
        int countOfPages = Double.valueOf(Math.ceil((countOfRowsGenerally + 0.0) / rop)).intValue();
        Integer actualPageNum = resolveActualPage(model, actualPage, countOfPages);

        Integer upLimit = actualPageNum * rop;
        Integer downLimit = ((actualPageNum - 1) * rop) + 1;

        //Count of pages
        model.addAttribute(PAGE_COUNT_ATTRIBUTE, countOfPages);
        //Object list
        List<Telephone> telephones = serviceTelephone.getFilteredTelephones(null, showMode, rop, downLimit, upLimit);
        Collections.sort(telephones, (o1, o2) -> {
            if(o1.getPhoneDate().getTime() > o2.getPhoneDate().getTime()){
                return -1;
            } else if(o1.getPhoneDate().getTime() < o2.getPhoneDate().getTime()){
                return 1;
            } else {
                return 0;
            }
        });

        //Chat visibility
        if(chatVisible != null){
            if(chatVisible.equals(CHAT_VISIBLE_TRUE_VALUE)){
                model.addAttribute(CHAT_VISIBILITY, CHAT_VISIBLE_TRUE_VALUE);
            }
        }

        //Fill ordinal numbers
        int startNum = countOfRowsGenerally - ((actualPageNum - 1) * rop);
        for(Telephone telephone: telephones){
            telephone.setOrdinalNumber(startNum);
            startNum--;
        }

        model.addAttribute(TELEPHONES_ATTR, telephones);
        return "telephone";
    }

    private Integer resolveActualPage(Model model, String actualPage, Integer countOfPages) {

        Integer actualPageNum;
        try{
            actualPageNum = Integer.valueOf(actualPage);
        } catch (Throwable t){
            actualPageNum = 1;
        }

        if(actualPageNum < 1){
            actualPageNum = 1;
        } else if(actualPageNum > countOfPages){
            actualPageNum = countOfPages;
        }

        //Add actual page to the model
        model.addAttribute(PAGE_ACTUAL_ATTRIBUTE, actualPageNum);

        //Add previous page attribute
        if(!(actualPageNum <= 1)){
            model.addAttribute(PAGE_PREVIOUS_ATTRIBUTE, actualPageNum - 1);
        }

        //Add next page attribute
        if(!(actualPageNum >= countOfPages)){
            model.addAttribute(PAGE_NEXT_ATTRIBUTE, actualPageNum + 1);
        }

        return actualPageNum;

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
                                    @PathVariable("phoneId") Long telephoneId,
                                    @RequestHeader(value = "referer", required = false) final String referer){

        try{
            //Delete action
            serviceTelephone.removeTelephone(telephoneId);
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
