package pl.jj.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@Entity
@Table(name = "dictionary")
public class Dictionary extends CommonEntity{

    @Column(name = "name", unique = true)
    private String name;


    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Dictionary parent;

    @OneToMany(mappedBy = "parent")
    private Set<Dictionary> children;

}
