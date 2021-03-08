package kr.tripamigo.tripamigo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @RequestMapping("/home")
    public String home(Model model) {
        return "community/home";
    }

}
