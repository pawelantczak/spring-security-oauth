package org.antczak.oauth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Paweł Antczak on 2014-05-23.
 */

@Controller
public class HomeController {

    @RequestMapping("/") String index() {
        return "index";
    }
}
