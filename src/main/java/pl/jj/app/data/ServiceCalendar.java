package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jj.app.model.CalendarCard;
import pl.jj.app.model.CalendarPosition;
import pl.jj.app.model.User;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
@Service
public class ServiceCalendar {

    @Autowired
    private RepositoryCalendar repositoryCalendar;

    @Autowired
    private ServiceUser serviceUser;

    public List<CalendarPosition> getCalendarEventForUserInMonth(User user, Date monthOfYear) {

        monthOfYear = CalendarCard.startDate(monthOfYear);

        List<CalendarPosition> calendarPositions =
                repositoryCalendar.findAllByCreatorAndStartDateGreaterThanEqualAndEndDateLessThanEqual(
                        user,
                        CalendarCard.firstDayMonth(monthOfYear),
                        CalendarCard.lastDayMonth(monthOfYear));

        return calendarPositions;
    }

    @Transactional
    public CalendarPosition createCalendarEvent(CalendarPosition calendarPosition) {
        User user = serviceUser.getLoginUser();
        calendarPosition.setCreateTime(new Date());
        calendarPosition.setCreator(user);
        return repositoryCalendar.save(calendarPosition);
    }

    @Transactional
    public void deleteCalendarEvent(Long id) {
        repositoryCalendar.deleteById(id);
    }

    @Transactional
    public CalendarPosition saveCalendatEvent(CalendarPosition calendarPosition) {
        CalendarPosition position = repositoryCalendar.findById(calendarPosition.getId()).get();
        position.setStartDate(calendarPosition.getStartDate());
        position.setEndDate(calendarPosition.getEndDate());
        position.setNote(calendarPosition.getNote());
        position.setType(calendarPosition.getType());
        return position;
    }

    @Transactional
    public CalendarPosition getById(Long id) {
        return repositoryCalendar.findById(id).get();
    }

}
