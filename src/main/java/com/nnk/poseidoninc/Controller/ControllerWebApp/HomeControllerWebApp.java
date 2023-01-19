package com.nnk.poseidoninc.Controller.ControllerWebApp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerWebApp {

    @GetMapping(value = "")
    public String voidCall(){
        return "redirect:/home";
    }

    @GetMapping(value = "/")
    public String badCall(){
        return "redirect:/home";
    }

    @GetMapping(value = "/home")
    public String homePage(){
        return "home";
    }
}
