package org.antczak.oauth.controllers;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.oauth.client.TwitterClient;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Paweł Antczak on 2014-05-23.
 */

@Controller
public class WebController {

    @Autowired
    Google2Client googleClient;

    @Autowired
    TwitterClient twitterClient;

    @ModelAttribute("googleOAuth")
    public String googleUrl(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) throws RequiresHttpAction {
        WebContext context = new J2EContext(httpServletRequest, httpServletResponse);
        return googleClient.getRedirectAction(context, false, false).getLocation();
    }

    @ModelAttribute("twitterOAuth")
    public String twitterUrl(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) throws RequiresHttpAction {
        WebContext context = new J2EContext(httpServletRequest, httpServletResponse);
        return twitterClient.getRedirectAction(context, false, false).getLocation();
    }

    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        if (auth != null && auth instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken token = (ClientAuthenticationToken) auth;
            UserProfile profile = token.getUserProfile();
            return profile.getAttribute("name").toString();
        }
        return "User";
    }

    @RequestMapping("/user")
    @ResponseBody UserProfile user() {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        UserProfile profile = null;
        if (auth != null && auth instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken token = (ClientAuthenticationToken) auth;
            profile = token.getUserProfile();
        }
        return profile;
    }

    @RequestMapping("/") String index() {
        return "index";
    }

    @RequestMapping("/secured") String secured() {
        return "secured";
    }

    @RequestMapping("/login") String login() {
        return "login";
    }

}
