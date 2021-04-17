package com.momot.trakball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder() //zmienic to na jakis bcrypt albo cos zeby nie przechowwac jako plaintext
                .username("user")
                .password("user1")
                .roles("USER")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder() //zmienic to na jakis bcrypt albo cos zeby nie przechowwac jako plaintext
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin); //zapisuje w pamieci taka role
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/squads")
                .permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                //.loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable(); //ZAPOBIEGA na dostep do apki z poziomu postmana, to zabezpieczneie przed kradzieza ciasteczek
    }

    //TODO 1 obczaic https://www.youtube.com/watch?v=yoTohM2jYhs zeby wykorzystac silnik thymeleaf, jak to sie robi i wgle
    //TODO 2 zabezpieczyc przed kradzieza ciasteczek
}
