package pl.jj.app.component;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jj.app.data.ServiceTelephone;
import pl.jj.app.data.ServiceUser;
import pl.jj.app.entity.User;
import pl.jj.app.util.ChartModel;
import pl.jj.app.util.Const;
import pl.jj.app.util.UsersModel;

import java.util.List;
import java.util.Set;

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

    //Chart constants
    private static final String CHART_BG_COLOR = "rgb(255, 99, 132)";
    private static final String CHART_MAIN_LABEL = "Telephones by day";
    private static final String CHART_BORDER_COLOR = "rgb(255, 99, 132)";
    public static final Integer DAYS_IN_CHART =  10;

    //Model attributes
    private static final String USERS_ATTRIBUTES = "users";
    //Lock / Unlock actions
    private static final String USERS_LOCK_NICKNAMES = "USERS_LOCK_NICKNAMES";
    private static final String USERS_UNLOCK_NICKNAMES = "USERS_UNLOCK_NICKNAMES";
    private static final String USERS_LOCK_FAIL = "USERS_LOCK_FAIL";
    private static final String USERS_UNLOCK_FAIL = "USERS_UNLOCK_FAIL";

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
    public String userPanel(Model model){
        model.addAttribute(USERS_ATTRIBUTES, serviceUser.getAllUsers());
        return "manager-user";
    }

    //Lock user
    @PostMapping(value = USER_MANAGER_PATH)
    public String lockUser(Model model, @ModelAttribute("users") UsersModel users){

        if(users != null){
            System.out.println("Nie null 1");
        }

        if(users.getUserModels() != null){
            System.out.println("Nie null 2");
        }

        try{
            if(users != null && users.getUserModels() != null && (!users.getUserModels().isEmpty())) {
                System.out.println("Weszło.");
                //Locking users in database
//                List<Long> ids = users.getSelectedUserIds();
//                List<User> us = serviceUser.lockUsers(ids, false);
//                List<String> names = Const.convertDataToListString(us, User::getUsername);
//                model.addAttribute(USERS_LOCK_NICKNAMES, StringUtils.join(names, ", "));
            } else {
                System.out.println("Nie weszło");
            }
        } catch (Throwable t){
            model.addAttribute(USERS_LOCK_FAIL);
            t.printStackTrace();
        }

        model.addAttribute(USERS_ATTRIBUTES, serviceUser.getAllUsers());
        return "manager-user";
    }

//    //Unlock user
//    @PostMapping(params = "unlock", value = USER_MANAGER_PATH)
//    public String unlockUser(Model model, @ModelAttribute("users") UsersModel users){
//
//        try{
//            if(users != null && users.getUserModels() != null && (!users.getUserModels().isEmpty())) {
////                List<Long> ids = users.getSelectedUserIds();
////                List<User> us = serviceUser.lockUsers(ids, true);
////                List<String> names = Const.convertDataToListString(us, User::getUsername);
////                model.addAttribute(USERS_UNLOCK_NICKNAMES, StringUtils.join(names, ", "));
//            }
//        } catch (Throwable t){
//            model.addAttribute(USERS_UNLOCK_FAIL);
//            t.printStackTrace();
//        }
//
//        model.addAttribute(USERS_ATTRIBUTES, serviceUser.getAllUsers());
//        return "manager-user";
//    }

}




















