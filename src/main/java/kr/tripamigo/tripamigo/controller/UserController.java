package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.exception.LoginException;
import kr.tripamigo.tripamigo.service.UserService;
import kr.tripamigo.tripamigo.util.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CipherUtil cipherUtil;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/withdrawal")
    public String withdrawal(HttpSession session) {

        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new LoginException(
                    messageSource.getMessage("error.login.required",
                            null, Locale.getDefault()),
                    "/home");
        }

        if (loginUser.getUserId().startsWith("K")) {
            // 카카오 OAuth 탈퇴 처리
            return "redirect:/oauth/kakao_unlink";

        } else if (loginUser.getUserId().startsWith("G")) {
            // 구글 OAuth 탈퇴 처리
            /*
            TODO: 구글 OAuth 탈퇴 처리
             */
        } else {
            userService.withdrawal(loginUser);
        }

        session.invalidate();
        return "redirect:/home";

    }

}
