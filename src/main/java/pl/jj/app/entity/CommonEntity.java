package pl.jj.app.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author JNartowicz
 */


@Getter
@Setter
@MappedSuperclass
public abstract class CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Version
    @Column(name = "version", columnDefinition = "bigint default 0")
    protected Long version;

    @Column(name = "create_time")
    protected Date createTime;

}
