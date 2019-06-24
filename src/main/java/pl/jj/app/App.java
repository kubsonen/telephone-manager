package pl.jj.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import pl.jj.app.component.AspectController;

/**
 * @author JNartowicz
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public FreeMarkerViewResolver freemarkerViewResolver() {
//        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
//        resolver.setOrder(1);
//        resolver.setCache(false);
//        resolver.setPrefix("");
//        resolver.setSuffix(".ftl");
//        resolver.setRequestContextAttribute("rc");
//        return resolver;
//    }
//
//    @Bean
//    public FreeMarkerConfigurer freeMarkerConfigurer(){
//        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
//        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/templates"); //defines the classpath location of the freemarker templates
//        freeMarkerConfigurer.setDefaultEncoding("UTF-8"); // Default encoding of the template files
//        freeMarkerConfigurer.getConfiguration().setNumberFormat("c");
//        return freeMarkerConfigurer;
//    }

}
