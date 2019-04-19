package pl.jj.app.component;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import pl.jj.app.util.Const;

import java.lang.reflect.Field;

/**
 * @author JNartowicz
 */
@Aspect
@Component
public class AspectController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectController.class);

    @Before("@within(org.springframework.stereotype.Controller)")
    private void beforeController(JoinPoint joinPoint){

        Model model = null;

        //Check the model exists in method parameters
        Object[] objects = joinPoint.getArgs();
        for(Object o: objects){
            if(o instanceof Model){
                model = (Model) o;
            }
        }
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



}
