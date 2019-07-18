package pl.jj.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.jj.app.data.ServiceUser;
import pl.jj.app.util.Const;

/**
 * @author JNartowicz
 */
@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    private ServiceUser serviceUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(serviceUser);
        dao.setPasswordEncoder(passwordEncoder);
        return dao;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/login.css").permitAll();
        http.authorizeRequests().antMatchers("/man/**").hasAnyAuthority(Const.AUTH_MANAGER);
        http.authorizeRequests().antMatchers("/resetPassword").permitAll();
        http.authorizeRequests().antMatchers("/tic-tac-toe").permitAll();
        http.authorizeRequests().antMatchers("/tic-tac-toe.css").permitAll();
        http.authorizeRequests().antMatchers("/tic-tac-toe.js").permitAll();
        http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
        http.logout().logoutSuccessUrl("/login");
    }

}
