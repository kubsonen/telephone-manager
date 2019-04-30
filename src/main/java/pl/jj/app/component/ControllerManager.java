package pl.jj.app.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jj.app.data.ServiceTelephone;
import pl.jj.app.util.ChartModel;

import java.util.Set;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerManager.MANAGER_PATH)
public class ControllerManager {

    private static final Logger logger = LoggerFactory.getLogger(ControllerManager.class);
    public static final String MANAGER_PATH = "/manager";
    public static final String CHART_DATA_PATH = "/get-telephones-by-day";

    //Chart constants
    private static final String CHART_BG_COLOR = "rgb(255, 99, 132)";
    private static final String CHART_MAIN_LABEL = "Telephones by day";
    private static final String CHART_BORDER_COLOR = "rgb(255, 99, 132)";
    public static final Integer DAYS_IN_CHART =  10;

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

}
