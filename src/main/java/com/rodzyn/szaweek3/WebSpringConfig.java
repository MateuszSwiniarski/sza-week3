package com.rodzyn.szaweek3;

import com.rodzyn.szaweek3.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class WebSpringConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSpringConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User userAdmin = new User("Admin",
                getPasswordEncoder().encode("Admin123"),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

        auth.inMemoryAuthentication().withUser(userAdmin);
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forAdmin").hasRole("ADMIN")
                .antMatchers("/forUser").hasAnyRole("USER", "ADMIN")
                .antMatchers("/signup").permitAll()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/forAdmin").permitAll()
                .and()
                .logout().logoutSuccessUrl("/logout");
    }
}
