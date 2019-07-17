package pl.jj.app.data;

import org.springframework.data.repository.CrudRepository;
import pl.jj.app.model.CalendarPosition;
import pl.jj.app.model.User;

import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
public interface RepositoryCalendar extends CrudRepository<CalendarPosition, Long> {
    List<CalendarPosition> findAllByCreatorAndStartDateGreaterThanEqualAndEndDateLessThanEqual(User creator, Date start, Date end);
}
