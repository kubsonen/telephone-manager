package pl.jj.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    public static final String DIC_ROWS_ON_PAGE_10 = "10";

    //CONDITION
    public static final Integer MAX_ROWS_ON_PAGES = 100;
    public static final Integer INIT_MESSAGES = 20;

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

    /**
     * Changes the date inputted in argument.
     * @param end - if parameter is set to true, time will be change to 23:59:59 otherwise to 00:00:00
     */
    public static final Date modifyTimeOfDate(Date date, boolean end){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, end ? 23 : 0);
        calendar.set(Calendar.MINUTE, end ? 59 : 0);
        calendar.set(Calendar.SECOND, end ? 59 : 0);
        return calendar.getTime();
    }

    /**
     * Find a start day of the week
     * @param date - input date
     * @return - date which will be a first date of the week
     */
    public static final Date getFirstDayOfTheWeek(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    /**
     * Return date which will be the first date of the month
     * @param date - input date
     * @return - first day of the month
     */
    public static final Date getFirstDayOfTheMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * Return date which will be the first date of the year
     * @param date - input date
     * @return - first date of the year
     */
    public static final Date getFirstDayOfTheYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * This method converts objects to the list of strings.
     * Developer indicate a fields or fields which will be in string.
     * @return - string list
     */
    public static final <T> List<String> convertDataToListString(List<T> list, IndicateData<T> data){
        List<String> strings = new ArrayList<>();
        for(T t: list){
            strings.add(data.getData(t));
        }
        return strings;
    }

    public interface IndicateData<T>{
        String getData(T t);
    }

    /**
     * While string is null or empty return true
     * @param s - string to check
     * @return - boolean value
     */
    public static boolean isBlank(String s){
        if(s == null){
            return true;
        }

        if(s.isEmpty()){
            return true;
        }

        return false;
    }

}
