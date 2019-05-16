package pl.jj.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import pl.jj.app.util.AppException;
import pl.jj.app.util.Const;
import pl.jj.app.util.TerminalMethod;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:authentication.properties")
public class ServiceTerminal {

    @Value("${terminal.password}")
    private String terminalPassword;

    @Autowired
    private ServiceUser serviceUser;

    public boolean isTerminalCommand(String command){
        if(command == null) return false;
        if(command.isEmpty()) return false;
        if(command.startsWith(Const.DOLLAR)) return true;
        return false;
    }

    public void executeCommand(String command){

        try{
            //Delete prefix which is a dollar char
            command = command.trim().substring(1, command.length());
            String[] commandParts = command.split(Const.SPACE);

            //Get the password
            String terminalPassword = commandParts[0];
            if(!(terminalPassword != null && terminalPassword.equals(terminalPassword))) throw new AppException("Incorrect terminal password.");

            //Get the command name
            String commandName = commandParts[1];

            //Search by command name
            for(Method method: this.getClass().getDeclaredMethods()){
                if(method.isAnnotationPresent(TerminalMethod.class)){
                    System.out.println("Jest adnotacja:" + method.getName());
                    TerminalMethod terminalMethod = method.getDeclaredAnnotation(TerminalMethod.class);
                    System.out.println(commandName);
                    System.out.println(terminalMethod.value());
                    if(commandName.equals(terminalMethod.value())){

                        List<Object> parametersInCommand = new ArrayList<>();
                        for(int i=2; i<commandParts.length; i++){
                            System.out.println("Dodano parametr: " + commandParts[i]);
                            parametersInCommand.add(commandParts[i]);
                        }
                        method.invoke(this, parametersInCommand.toArray());
                        return;
                    }
                }
            }

            throw new AppException("Command not found.");

        } catch (Throwable t){
            t.printStackTrace();
        }

    }

    @Transactional
    @TerminalMethod("addUserAuthority")
    public void addAuthorityForUser(String username, String authority) throws Throwable {
        serviceUser.addAuthorityForUser(username, authority);
    }


}
