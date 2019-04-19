package pl.jj.app.util;

import com.sun.org.apache.bcel.internal.generic.FADD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
public final class Const {

    private static boolean tmDicCreated = true;
    public static final boolean dicNeedToCreate(){
        if(tmDicCreated){
            tmDicCreated = false;
            return true;
        } else {
            return false;
        }
    }

    private Const(){
        throw new ClassCastException();
    }
    private static final Logger logger = LoggerFactory.getLogger(Const.class);

    public static final String PREFIX_CONST_TO_ADD_IN_MODEL = "ADD_MODEL_";

    //DICTIONARIES
    public static final String DEFAULT_DIC_FIELD_PREFIX = "DEF_DIC_";
    public static final String DIC_VALUE_PREFIX = "DIC_";
    public static final String DEF_DIC_ROWS_ON_PAGE = "ROWS_ON_PAGE";
    public static final String DIC_ROWS_ON_PAGE_5 = "5";
    public static final String DIC_ROWS_ON_PAGE_10 = "10";
    public static final String DIC_ROWS_ON_PAGE_20 = "20";

    //CONDITION
    public static final Integer MAX_ROWS_ON_PAGES = 100;

    /**
     * Get the values based on field name.
     * @param prefix - field name prefix
     * @return - const field value
     */
    public static final List<String> getFieldValuesByPrefix(String prefix){
        List<String> fields = new ArrayList<>();
        for(Field field: Const.class.getDeclaredFields()){
            String fieldName = field.getName();
            if(fieldName.startsWith(prefix)){
                try {
                    fields.add((String) field.get(null));
                } catch (Exception e) {
                    logger.warn("Cannot get the value from field.");
                    e.printStackTrace();
                }
            }
        }
        return fields;
    }

}
