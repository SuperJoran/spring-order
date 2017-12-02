package be.jorandeboever.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Import({SampleConfiguration.class})
@SpringBootApplication
public class SecuredApplication extends SpringBootServletInitializer {

    @Configuration
    protected static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //TODO rememberme ?
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/register", "/login", "/home").permitAll()
//                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
                    .antMatchers("/event/**").authenticated().anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .permitAll();

        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SecuredApplication.class, args);
    }

}
