package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.util.MailSendUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/email")
public class EmailAuthController {

    @PostMapping("/send")
    @ResponseBody
    public boolean sendAuthCode(HttpSession session) {
        return false;
    }

    @PostMapping("/check")
    @ResponseBody
    public boolean checkAuthCode(HttpSession session) {
        return true;
    }

}
