package com.naderi.phorest.salon.controller;

import com.naderi.phorest.salon.common.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends BaseController {
    @Value("${version:local}")
    protected String appVersion;

    @RequestMapping(value = {"/", "/index.html"})
    public String handleRequest(Model model) {
        model.addAttribute("appVersion", appVersion);
        model.addAttribute("profile", activeProfile);
        return "index";
    }
}
