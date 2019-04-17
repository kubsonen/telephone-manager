package pl.jj.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@Entity
@Table(name = "telephone")
public class Telephone extends CommonEntity{

    @Column(name = "phone_date")
    private Date phoneDate;

    @Column(name = "note")
    private String note;

}
