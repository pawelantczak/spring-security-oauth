package org.antczak.oauth;

import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.pac4j.springframework.security.web.ClientAuthenticationEntryPoint;
import org.pac4j.springframework.security.web.ClientAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Paweł Antczak on 2014-05-23.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Google
    @Bean
    public Google2Client googleClient() {
        Google2Client google2Client = new Google2Client();
        google2Client
            .setKey("167480702619-0k2ikl9v3ph44u6i6hid1b160v4fggua.apps.googleusercontent.com");
        google2Client.setSecret("i1jYBya-bxIyEqkLw7MaJ12A");
        return google2Client;
    }

    @Bean
    public ClientAuthenticationEntryPoint googleEntryPoint() {
        ClientAuthenticationEntryPoint clientAuthenticationEntryPoint =
            new ClientAuthenticationEntryPoint();
        //noinspection unchecked
        clientAuthenticationEntryPoint.setClient((Client)googleClient());
        return clientAuthenticationEntryPoint;
    }

    // Twitter
    @Bean
    public TwitterClient twitterClient() {
        TwitterClient twitterClient = new TwitterClient();
        twitterClient
            .setKey("CoxUiYwQOSFDReZYdjigBA");
        twitterClient.setSecret("2kAzunH5Btc4gRSaMr7D7MkyoJ5u1VzbOOzE8rBofs");
        return twitterClient;
    }

    @Bean
    public ClientAuthenticationEntryPoint EntryPoint() {
        ClientAuthenticationEntryPoint clientAuthenticationEntryPoint =
            new ClientAuthenticationEntryPoint();
        //noinspection unchecked
        clientAuthenticationEntryPoint.setClient((Client)twitterClient());
        return clientAuthenticationEntryPoint;
    }

    // Common
    @Bean()
    public Clients clients() {
        Clients clients = new Clients();
        clients.setClients(googleClient(), twitterClient());
        clients.setCallbackUrl("http://localhost:8080/callback");
        return clients;
    }

    @Bean
    public ClientAuthenticationProvider clientProvider() {
        ClientAuthenticationProvider clientAuthenticationProvider =
            new ClientAuthenticationProvider();
        clientAuthenticationProvider.setClients(clients());
        return clientAuthenticationProvider;
    }

    @Bean
    public ClientAuthenticationFilter clientFilter() throws Exception {
        ClientAuthenticationFilter clientAuthenticationFilter =
            new ClientAuthenticationFilter("/callback");
        clientAuthenticationFilter.setClients(clients());
        clientAuthenticationFilter.setAuthenticationManager(authenticationManager());
        return clientAuthenticationFilter;
    }

    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(clientProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/secured").authenticated();

        http
            .formLogin().loginPage("/login").defaultSuccessUrl("/secured").and()
            .logout().logoutSuccessUrl("/");

        http.csrf().disable();

    }
}
