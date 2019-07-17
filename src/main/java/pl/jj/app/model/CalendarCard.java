package pl.jj.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author JNartowicz
 */
@Getter
@Setter
public class CalendarCard {

    private Date calendarMonth;

    private List<CalendarPosition> calendarPositions;

    public CalendarCard(Date calendarMonth, List<CalendarPosition> calendarPositions) {
        this.calendarMonth = startDate(calendarMonth);
        this.calendarPositions = calendarPositions;
        this.prepareCalendar();
    }

    private void prepareCalendar() {

        //Create map with calendar dates keys
        //Key is a date without time
        Map<Date, List<CalendarPosition>> dateCalendarPositionMap = new HashMap<>();

        List<Date> datesInMonth =
                getDatesRange(firstDayMonth(calendarMonth), lastDayMonth(calendarMonth));

        //Go through all calendar position which was inserted in constructor
        for (CalendarPosition p : calendarPositions) {

            //Get all days where the event exists
            List<Date> calendarPositionEventsDates =
                    getDatesRange(p.getStartDate(), p.getEndDate());

            //Create a map which will contain dates with existed positions
            for (Date positionEventDate : calendarPositionEventsDates) {

                //Get position only in date range
                if (datesInMonth.contains(positionEventDate)) {
                    if (dateCalendarPositionMap.containsKey(positionEventDate)) {
                        List<CalendarPosition> calendarPositions = dateCalendarPositionMap.get(positionEventDate);
                        calendarPositions.add(p);
                    } else {
                        final List<CalendarPosition> calendarPositions = new ArrayList<>();
                        calendarPositions.add(p);
                        dateCalendarPositionMap.put(positionEventDate, calendarPositions);
                    }
                }
            }

        }

        //Fill the rest dates in map
        for (Date monthDate : datesInMonth) {
            if (!dateCalendarPositionMap.containsKey(monthDate)) {
                dateCalendarPositionMap.put(monthDate, null);
            }
        }

        //Construct weeks
        weeks = new ArrayList<>();
        final Week firstWeek = new Week();
        Week actualWeek = firstWeek;


        List<Date> datesInMap = new ArrayList<>(dateCalendarPositionMap.keySet());
        Collections.sort(datesInMap, Comparator.naturalOrder());

        //Create weeks, day and match events
        Iterator<Date> dateIterator = datesInMap.iterator();
        Date iDate = dateIterator.next();
        Integer prevDayInWeek = getDayNumInWeek(iDate);
        Integer actualDayInWeek;
        actualWeek.addDay(prevDayInWeek, constructDayBaseOnCalendarPositions(iDate, dateCalendarPositionMap.get(iDate)));
        weeks.add(firstWeek);

        while (dateIterator.hasNext()) {
            iDate = dateIterator.next();
            actualDayInWeek = getDayNumInWeek(iDate);

            if (actualDayInWeek < prevDayInWeek) {
                final Week nextWeek = new Week();
                actualWeek = nextWeek;
                actualWeek.addDay(actualDayInWeek, constructDayBaseOnCalendarPositions(iDate, dateCalendarPositionMap.get(iDate)));
                weeks.add(nextWeek);
            } else {
                actualWeek.addDay(actualDayInWeek, constructDayBaseOnCalendarPositions(iDate, dateCalendarPositionMap.get(iDate)));
            }

            prevDayInWeek = getDayNumInWeek(iDate);

        }

    }

    private List<Week> weeks;

    @Getter
    @Setter
    public class Week {

        private Map<String, Day> days;

        public void addDay(Integer dayInWeek, Day day) {
            if (days == null) days = new HashMap<>();
            days.put(dayInWeek.toString(), day);
        }

    }

    @Getter
    @Setter
    public class Day {

        public Day(Date time) {
            this.time = time;
            dayInMonth = getDayNumInMonth(this.time);
            if (startDate(this.time).equals(startDate(new Date()))) today = true;
        }

        private List<DayEvent> events;

        private Date time;

        private boolean today;

        private int dayInMonth;

        public void addEvent(DayEvent event) {
            if (events == null) events = new ArrayList<>();
            events.add(event);
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class DayEvent {

        private Long eventId;

        private String shortDesc;

        private Date start;

        private Date end;

    }

    private List<Date> getDatesRange(Date start, Date end) {
        //Check date range is correct
        if (end.getTime() < start.getTime()) throw new RuntimeException("Incorrect date range.");

        start = startDate(start);
        end = startDate(end);

        if (start.equals(end)) {
            return Arrays.asList(start);
        }

        List<Date> dates = new ArrayList<>();
        dates.add(start);
        while (!start.equals(end)) {
            start = nextDay(start);
            dates.add(start);
        }

        return dates;
    }

    public static Date startDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.clear(Calendar.MILLISECOND);
        return calendar.getTime();
    }

    private Date nextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date firstDayMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public static Date lastDayMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int actualMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, actualMonth + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return calendar.getTime();
    }

    private int getDayNumInWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return ((calendar.get(Calendar.DAY_OF_WEEK) + 12) % 7) + 1;
    }

    private int getDayNumInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_MONTH));
    }

    private Day constructDayBaseOnCalendarPositions(Date date, List<CalendarPosition> calendarPositions) {
        Day day = new Day(date);

        if(date.equals(startDate(new Date()))) {
            day.setToday(true);
        }

        if (calendarPositions != null && !calendarPositions.isEmpty()) {
            for (CalendarPosition position : calendarPositions) {
                day.addEvent(new DayEvent(position.getId(), position.getType().name(), position.getStartDate(), position.getEndDate()));
            }
        }
        return day;
    }

}
