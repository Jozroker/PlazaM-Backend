package com.site.plazam.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController implements ErrorController {

    private static final String PATH = "/error";

    @GetMapping(value = PATH)
    public String error(ModelMap model) {
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @GetMapping("/access_denied")
    public String accessDenied(ModelMap model) {
        model.addAttribute("errorCode", 403);
        return "error";
    }
}
