package pl.jj.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.jj.app.data.ServiceChat;
import pl.jj.app.data.ServiceTerminal;
import pl.jj.app.model.ChatMessage;

import java.security.Principal;
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

    @Autowired
    private ServiceTerminal serviceTerminal;

    @ResponseBody
    @PostMapping(CHAT_GET_LAST_MESSAGES)
    public List<ChatMessage> loadLastMessages() {
//        return serviceChat.findFirstRows(Const.INIT_MESSAGES);
        return null;
    }

    @MessageMapping("/sendMessage")
//    @SendTo("/topic/receiveMessage")
    public ChatMessage messageExchange(ChatMessage message,
                                       Principal principal) {

        if (serviceTerminal.isTerminalCommand(message.getMessageContent())){
            serviceTerminal.executeCommand(message.getMessageContent());
            return null;
        }

        try {
            if (message.getMessageContent().isEmpty()) {
                return null;
            }
        } catch (Throwable t) {
            return null;
        }


        //Save object to database
        ChatMessage chatMessage = serviceChat.saveMessage(
                message,
                principal.getName());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return chatMessage;
    }

}
