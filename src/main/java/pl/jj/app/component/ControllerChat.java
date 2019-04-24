package pl.jj.app.component;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import pl.jj.app.entity.ChatMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JNartowicz
 */
@Controller
public class ControllerChat {

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
