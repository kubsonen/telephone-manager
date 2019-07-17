package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.jj.app.data.ServiceCalendar;
import pl.jj.app.data.ServiceUser;
import pl.jj.app.model.AjaxResponse;
import pl.jj.app.model.CalendarCard;
import pl.jj.app.model.CalendarPosition;
import pl.jj.app.util.AjaxException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerGraphic.GRAPHIC_PATH)
public class ControllerGraphic {

    public static final String GRAPHIC_PATH = "/graphic";
    public static final String GRAPHIC_GET_EVENT = "/getEvent";
    public static final String GRAPHIC_ADD_EVENT = "/addEvent";
    public static final String GRAPHIC_EDIT_EVENT = "/editEvent";
    public static final String GRAPHIC_DELETE_EVENT = "/deleteEvent";

    @Autowired
    private ServiceCalendar serviceCalendar;

    @Autowired
    private ServiceUser serviceUser;

    @GetMapping
    public String showGraphicTemplate(Model model) {

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        today = calendar.getTime();

        //Show graphic for current month
        List<CalendarPosition> calendarPositions = serviceCalendar.getCalendarEventForUserInMonth(serviceUser.getLoginUser(), today);
        CalendarCard calendarCard = new CalendarCard(today, calendarPositions);
        model.addAttribute("calendar", calendarCard);

        return "graphic";
    }

    @GetMapping(GRAPHIC_GET_EVENT + "/{eventId}")
    @ResponseBody
    public AjaxResponse getCalendarEvent(@PathVariable("eventId") Long eventId) throws AjaxException {
        return AjaxResponse.responseObject("Get done.", serviceCalendar.getById(eventId));

    }

    @PostMapping(GRAPHIC_ADD_EVENT)
    @ResponseBody
    public AjaxResponse addCalendarEvent(@RequestBody CalendarPosition calendarPosition) throws AjaxException {

        Date start = calendarPosition.getStartDate();
        Date end = calendarPosition.getEndDate();

        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(start));
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(end));

        if (start.getTime() > end.getTime()) throw new AjaxException("Start date is greater than earlier.");
        serviceCalendar.createCalendarEvent(calendarPosition);
        return AjaxResponse.responseSuccess("Add done.");

    }

    @PostMapping(GRAPHIC_EDIT_EVENT)
    @ResponseBody
    public AjaxResponse editCalendarEvent(@RequestBody CalendarPosition calendarPosition) throws AjaxException {

        Date start = calendarPosition.getStartDate();
        Date end = calendarPosition.getEndDate();

        if (start.getTime() > end.getTime()) throw new AjaxException("Start date is greater than earlier.");
        serviceCalendar.saveCalendatEvent(calendarPosition);
        return AjaxResponse.responseSuccess("Edit done.");

    }

    @PostMapping(GRAPHIC_DELETE_EVENT + "/{eventId}")
    @ResponseBody
    public AjaxResponse deleteCalendarEvent(@PathVariable Long eventId) throws AjaxException {

        serviceCalendar.deleteCalendarEvent(eventId);
        return AjaxResponse.responseSuccess("Delete successful.");

    }

}
