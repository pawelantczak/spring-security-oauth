package org.antczak.oauth.controllers;

import org.pac4j.core.client.Client;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.oauth.client.Google2Client;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Paweł Antczak on 2014-05-23.
 */

@Controller
public class WebController {

    @Autowired
    Client google2Client;

    @ModelAttribute("googleOAuth2")
    public String google2Url(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse) throws RequiresHttpAction {
        WebContext context = new J2EContext(httpServletRequest, httpServletResponse);
        return ((Google2Client)google2Client).getRedirectAction(context, false, false).getLocation();
    }

    @ModelAttribute("userName")
    public String userName() {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        if (auth != null && auth instanceof ClientAuthenticationToken) {
            ClientAuthenticationToken token = (ClientAuthenticationToken) auth;
            UserProfile profile = token.getUserProfile();
            return profile.getAttribute("given_name").toString();
        }
        return "User";
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
