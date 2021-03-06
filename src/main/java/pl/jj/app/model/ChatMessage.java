package pl.jj.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author JNartowicz
 */
@Getter
@Setter
@Entity
@Table(name = "chat_message")
public class ChatMessage extends CommonEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "message_date")
    private Date messageDate;

    @Column(name = "message_content")
    private String messageContent;

}
