package kr.tripamigo.tripamigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/home")
    String home(Model model) {
        return "home";
    }

    @RequestMapping("/login")
    String login(Model model) {
        return "login";
    }

}
