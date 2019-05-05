package pl.jj.app.model;

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

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Dictionary parent;

    @OneToMany(mappedBy = "parent",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private Set<Dictionary> children;

}
