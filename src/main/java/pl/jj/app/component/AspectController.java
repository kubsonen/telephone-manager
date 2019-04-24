package pl.jj.app.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import pl.jj.app.data.ServiceDictionary;
import pl.jj.app.util.Const;
import pl.jj.app.util.InsertDictionary;
import pl.jj.app.util.ShowMode;

import java.lang.reflect.Field;

/**
 * @author JNartowicz
 */
@Aspect
@Component
public class AspectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

    @Autowired
    private ServiceDictionary serviceDictionary;

    @Before("@within(org.springframework.stereotype.Controller)")
    private void beforeController(JoinPoint joinPoint){

        Model model = getJointPointMode(joinPoint);
        if(model == null) return;

        //Get class which contains executed method
        Field[] fields = joinPoint.getTarget().getClass().getDeclaredFields();
        //Iterate for each field for find with expected prefix
        for(Field field: fields){
            field.setAccessible(true);

            String fieldName = field.getName();
            if(fieldName.startsWith(Const.PREFIX_CONST_TO_ADD_IN_MODEL)){
                try {
                    model.addAttribute(fieldName, field.get(null));
                } catch (IllegalAccessException e) {
                    LOGGER.warn("Cannot add attribute to the model.");
                }
            }
        }
    }

    @Before("@annotation(insertDictionary)")
    private void addDictionaries(JoinPoint joinPoint, InsertDictionary insertDictionary){

        Model model = getJointPointMode(joinPoint);
        if(model == null) return;

        String[] dicNames = insertDictionary.value();
        for(String s: dicNames){

            if(s.equals(ShowMode.SHOW_MODES)){
                model.addAttribute(s, ShowMode.values());
            } else {
                model.addAttribute(s, serviceDictionary.getDictionariesByParentName(s));
            }

        }
    }

    /**
     * Get spring model from join point args.
     * @param joinPoint - aspect join point
     * @return - model - spring interface
     */
    private Model getJointPointMode(JoinPoint joinPoint){
        Model model = null;

        //Check the model exists in method parameters
        Object[] objects = joinPoint.getArgs();
        for(Object o: objects){
            if(o instanceof Model){
                model = (Model) o;
            }
        }
        return model;
    }

}
