package org.antczak.oauth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Paweł Antczak on 2014-05-23.
 */

@Controller
public class WebController {

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
