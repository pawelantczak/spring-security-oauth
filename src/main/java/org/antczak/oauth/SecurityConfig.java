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
            .antMatchers("/").permitAll()
            .antMatchers("/secured").authenticated();

        http
            .formLogin().loginPage("/login").and()
            .logout().logoutSuccessUrl("/");

        http.csrf().disable();

    }
}
