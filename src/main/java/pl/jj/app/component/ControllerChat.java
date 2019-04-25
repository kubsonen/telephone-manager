package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jj.app.data.ServiceChat;
import pl.jj.app.entity.ChatMessage;
import pl.jj.app.util.Const;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
@Controller
@RequestMapping(ControllerChat.CHAT_PATH)
public class ControllerChat {

    public static final String CHAT_PATH = "/chat";
    public static final String CHAT_GET_LAST_MESSAGES = "/lastMessages";

    @Autowired
    private ServiceChat serviceChat;

    @ResponseBody
    @PostMapping(CHAT_GET_LAST_MESSAGES)
    public List<ChatMessage> loadLastMessages(){
        return serviceChat.findFirstRows(Const.INIT_MESSAGES);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/receiveMessage")
    public ChatMessage messageExchange(ChatMessage message,
                                       Principal principal){

        //Save object to database
        ChatMessage chatMessage = serviceChat.saveMessage(
                message,
                principal.getName());

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return chatMessage;
    }

}
