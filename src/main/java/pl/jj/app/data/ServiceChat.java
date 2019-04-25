package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jj.app.entity.ChatMessage;
import pl.jj.app.entity.User;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ServiceChat {

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private RepositoryChat repositoryChat;

    @Transactional
    public List<ChatMessage> findFirstRows(Integer c){
        List<ChatMessage> messages = repositoryChat.getLastMessages(c);
        Collections.sort(messages, (o1, o2) -> {
            if(o1.getMessageDate().getTime() < o2.getMessageDate().getTime()){
                return -1;
            } else if(o1.getMessageDate().getTime() > o2.getMessageDate().getTime()){
                return 1;
            } else {
                return 0;
            }
        });
        return messages;
    }

    @Transactional
    public ChatMessage saveMessage(ChatMessage message, String username){
        if(message.getMessageDate() == null) message.setMessageDate(new Date());
        //Fill sender info
        if(!(username == null || username.isEmpty())){
            User user = serviceUser.findByUsername(username);
            message.setSender(user);
        }
        return repositoryChat.save(message);
    }

}
