package xyz.aqlabs.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui/")
@SuppressWarnings("unused")
public class WebPageController {

    @GetMapping("login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("home")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("profile")
    public String getProfilePage(){
        return "profile";
    }

    @GetMapping("hr")
    public String getHrPage(){
        return "hr";
    }

    @GetMapping("timeclock")
    public String getTimeClock() {return "timeclock";}

    @GetMapping("mypay")
    public String getMyPayPage(){
        return "mypay";
    }

    @GetMapping("timecard")
    public String getTimeCardPage(){
        return "timecard";
    }

    @GetMapping("myschedule")
    public String getMySchedulePage(){
        return "myschedule";
    }

}
