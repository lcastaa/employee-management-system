package xyz.aqlabs.cookbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/")
public class WebPageController {

    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("home")
    public String getHomePage(){
        return "home";
    }

}
