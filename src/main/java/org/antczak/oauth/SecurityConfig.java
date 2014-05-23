package org.antczak.oauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Paweł Antczak on 2014-05-23.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/", "/login", "/logout", "/oauth2callback", "/css/**", "/img/**",
                "/js/**", "/webjars/**")
            .permitAll()
            .anyRequest().authenticated();

        http.formLogin().loginPage("/login").defaultSuccessUrl("/");

        http.csrf().disable();

    }
}
