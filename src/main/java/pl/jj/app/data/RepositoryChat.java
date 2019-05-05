package pl.jj.app.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.jj.app.model.ChatMessage;

import java.util.List;

public interface RepositoryChat extends CrudRepository<ChatMessage, Long> {

    @Query( nativeQuery = true,
            value = "select * from chat_message " +
                    "order by message_date desc " +
                    "FETCH FIRST :countRows ROWS ONLY")
    List<ChatMessage> getLastMessages(@Param("countRows") Integer countRows);

}
