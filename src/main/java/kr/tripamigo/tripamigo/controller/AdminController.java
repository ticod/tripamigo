package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private EmailService emailService;

    @RequestMapping("/mailtest")
    @ResponseBody
    public boolean testMail() {
        String msg = "hello world";
        try {
            emailService.sendMail("lours2011@gmail.com", EmailService.DEFAULT_FROM_ADDRESS, "tripamigo", msg);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
