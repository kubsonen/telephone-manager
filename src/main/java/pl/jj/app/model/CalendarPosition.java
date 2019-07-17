package pl.jj.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@Entity
@Table(name = "calendar_position")
public class CalendarPosition extends CommonEntity {

    @Enumerated(EnumType.STRING)
    private CalendarPositionType type;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+02:00")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+02:00")
    private Date endDate;

    private String note;

    public enum CalendarPositionType {
        WORK, OTHER;
    }

}
