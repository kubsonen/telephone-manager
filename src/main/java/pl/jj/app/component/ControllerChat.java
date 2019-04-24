package pl.jj.app.component;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jj.app.entity.ChatMessage;

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

    @ResponseBody
    @PostMapping(CHAT_GET_LAST_MESSAGES)
    public List<ChatMessage> loadLastMessages(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageContent("Elo");
        List<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(chatMessage);
        return chatMessages;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/receiveMessage")
    public List<ChatMessage> messageExchange(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
