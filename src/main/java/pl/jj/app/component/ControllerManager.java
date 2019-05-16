package pl.jj.app.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.jj.app.data.ServiceTelephone;
import pl.jj.app.data.ServiceUser;
import pl.jj.app.model.AjaxResponse;
import pl.jj.app.model.ChartModel;
import pl.jj.app.model.InviteMail;
import pl.jj.app.util.AjaxException;
import pl.jj.app.util.Const;

import java.util.Set;
import java.util.regex.Matcher;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerManager.MANAGER_PATH)
public class ControllerManager {

    private static final Logger logger = LoggerFactory.getLogger(ControllerManager.class);
    public static final String MANAGER_PATH = "/manager";
    public static final String CHART_DATA_PATH = "/telephones-stats";
    public static final String USER_MANAGER_PATH = "/users";
    public static final String USER_MANAGER_LOCK_USER_PATH = "/lock";
    public static final String USER_MANAGER_UNLOCK_USER_PATH = "/unlock";
    public static final String USER_MANAGER_INVITE_NEW_USER_PATH = "/invite";

    //Chart constants
    private static final String CHART_BG_COLOR = "rgb(255, 99, 132)";
    private static final String CHART_MAIN_LABEL = "Telephones by day";
    private static final String CHART_BORDER_COLOR = "rgb(255, 99, 132)";
    public static final Integer DAYS_IN_CHART =  10;

    //Model attributes
    private static final String USERS_ATTRIBUTES = "users";
    //Lock / Unlock actions
    private static final String USERS_LOCK_SUCCESS = "lockSuccess";
    private static final String USERS_UNLOCK_SUCCESS = "unlockSuccess";
    private static final String USERS_LOCK_FAIL = "lockFail";
    private static final String USERS_UNLOCK_FAIL = "unlockFail";

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private ServiceTelephone serviceTelephone;

    @GetMapping
    public String getDashBoard(Model model){
        return "manager";
    }

    /**
     * Get data to the line chart.
     * @return - chart model, which will converted to the JSON
     */
    @ResponseBody
    @PostMapping(CHART_DATA_PATH)
    public ChartModel getTelephonesByDays(){
        Set<Object[]> objects = serviceTelephone.getStatsTelephonesByDays(DAYS_IN_CHART);
        ChartModel chartModel = prepareTelephonesStats(objects);
        return chartModel;
    }

    /**
     * Create chart model,  based on data retrieved from database
     */
    public ChartModel prepareTelephonesStats(Set<Object[]> objects){
        //Check any object exists in args collection
        if(objects == null || objects.isEmpty()) return null;

        //Build chart model
        ChartModel.Builder builder = new ChartModel.Builder();
        builder.setBgColor(CHART_BG_COLOR).setBorderColor(CHART_BORDER_COLOR).setMainLabel(CHART_MAIN_LABEL);

        for(Object[] os: objects){
            try {
                String label = os[0].toString();
                Integer count;

                try {
                    count = Integer.valueOf(os[1].toString());
                } catch (Throwable t){
                    count = 0;
                }

                builder.addLabel(label);
                builder.addData(count);

            } catch (Throwable r){
                logger.info("Label not recognized.");
            }
        }
        return builder.build();
    }

    @GetMapping(USER_MANAGER_PATH)
    public String userPanel(Model model,
                            @RequestParam(name = USERS_LOCK_SUCCESS, required = false) String userLockSuccess,
                            @RequestParam(name = USERS_LOCK_FAIL, required = false) String userLockFail,
                            @RequestParam(name = USERS_UNLOCK_SUCCESS, required = false) String userUnlockSuccess,
                            @RequestParam(name = USERS_UNLOCK_FAIL, required = false) String userUnlockFail){

        if(!Const.isBlank(userLockSuccess)) model.addAttribute(USERS_LOCK_SUCCESS);
        if(!Const.isBlank(userLockFail)) model.addAttribute(USERS_LOCK_FAIL);
        if(!Const.isBlank(userUnlockSuccess)) model.addAttribute(USERS_UNLOCK_SUCCESS);
        if(!Const.isBlank(userUnlockFail)) model.addAttribute(USERS_UNLOCK_FAIL);

        model.addAttribute(USERS_ATTRIBUTES, serviceUser.getAllUsers());
        return "manager-user";
    }

    //Lock user
    @GetMapping(value = USER_MANAGER_PATH + USER_MANAGER_LOCK_USER_PATH + "/{userId}")
    public String lockUser(Model model, @PathVariable String userId, RedirectAttributes ra){

        try{
            serviceUser.lockUser(Long.valueOf(userId), false);
            ra.addAttribute(USERS_LOCK_SUCCESS, true);
        } catch (Throwable t){
            ra.addAttribute(USERS_LOCK_FAIL, true);
        }

        return "redirect:" + MANAGER_PATH + USER_MANAGER_PATH;
    }

    //Unlock user
    @GetMapping(value = USER_MANAGER_PATH + USER_MANAGER_UNLOCK_USER_PATH + "/{userId}")
    public String unLockUser(Model model, @PathVariable String userId, RedirectAttributes ra){

        try{
            serviceUser.lockUser(Long.valueOf(userId), true);
            ra.addAttribute(USERS_UNLOCK_SUCCESS, true);
        } catch (Throwable t){
            ra.addAttribute(USERS_UNLOCK_FAIL, true);
        }

        return "redirect:" + MANAGER_PATH + USER_MANAGER_PATH;
    }

    @ResponseBody
    @PostMapping(USER_MANAGER_PATH + USER_MANAGER_INVITE_NEW_USER_PATH)
    public AjaxResponse sendInviteLink(@RequestBody InviteMail mail) throws Throwable {

        if(mail.getMail().isEmpty()) throw new AjaxException("E-mail was not entered.");
        if(!Const.VALID_EMAIL_ADDRESS_REGEX.matcher(mail.getMail()).matches()) throw new AjaxException("Entered text is not an email address.");
        serviceUser.sendEmailInvite(mail.getMail());
        return AjaxResponse.responseSuccess("Invite link was sent.");

    }

}




















