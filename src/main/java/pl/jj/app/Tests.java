package pl.jj.app;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jj.app.util.Const;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JNartowicz
 */
public class Tests {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void  testDateChanger(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        logger.info("Original: " + simpleDateFormat.format(date));
        logger.info("End of the day: " + simpleDateFormat.format(Const.modifyTimeOfDate(date, true)));
        logger.info("Start of the day : " + simpleDateFormat.format(Const.modifyTimeOfDate(date, false)));
    }

    @Test
    public void testGetterFirstDateOfTheWeek(){

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        logger.info("Original: " + simpleDateFormat.format(date));
        logger.info("First day of the week: " + simpleDateFormat.format(Const.getFirstDayOfTheWeek(date)));

    }


}
