package pl.jj.app;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jj.app.component.ComponentTicTacToeGame;
import pl.jj.app.model.CalendarCard;
import pl.jj.app.util.Const;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author JNartowicz
 */
public class Tests {

    private static final Logger logger = LoggerFactory.getLogger(Test.class);
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Test
    public void testDateChanger() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        logger.info("Original: " + simpleDateFormat.format(date));
        logger.info("End of the day: " + simpleDateFormat.format(Const.modifyTimeOfDate(date, true)));
        logger.info("Start of the day : " + simpleDateFormat.format(Const.modifyTimeOfDate(date, false)));
    }

    @Test
    public void testGetterFirstDays() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        logger.info("Original: " + simpleDateFormat.format(date));
        logger.info("First day of the week: " + simpleDateFormat.format(Const.getFirstDayOfTheWeek(date)));
        logger.info("First day of the month: " + simpleDateFormat.format(Const.getFirstDayOfTheMonth(date)));
        logger.info("First day of the year: " + simpleDateFormat.format(Const.getFirstDayOfTheYear(date)));


    }

    @Test
    public void testDates() {
        Date date = new Date();
        System.out.println("Actual date: " + date);
        System.out.println("Date in change:" + changeDate(date));
        date = changeDate(date);
        System.out.println("Date after change: " + date);
    }

    public Date changeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    @Test
    public void testingDatesGetMonth() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for (int i = 0; i < 20; i++) {
            calendar.add(Calendar.DATE, 1);
            int m = calendar.get(Calendar.DAY_OF_WEEK);
            int value = ((m + 12) % 7) + 1;
            logger.info(new SimpleDateFormat("u").format(calendar.getTime()) + " ===> " + value);
        }
    }

    @Test
    public void testLastDayMonth() {
        logger.info(new SimpleDateFormat("dd-MM-yyyy").format(CalendarCard.lastDayMonth(new Date())));
    }

    @Test
    public void moveCombination() {
        List<Integer> integerList = Arrays.asList(1,2,3,4,5,6,7,8,9);
        List<Integer> sumList = new ArrayList<>();
        ComponentTicTacToeGame componentTicTacToeGame = new ComponentTicTacToeGame();
//        componentTicTacToeGame.moveCombination(integerList, new Integer[3], sumList, 0,0,3);
    }

}















