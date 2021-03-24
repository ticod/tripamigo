package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.service.EmailService;
import kr.tripamigo.tripamigo.util.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("/email")
public class EmailAuthController {

    @Autowired
    private EmailService emailService;

    private final static String sessionSaltName = "emailAuthSalt";
    private final static String sessionCodeName = "emailAuthCode";
    private final static String sessionSeparater = "_";

    private final static String emailSubject = "Tripamigo 이메일 인증 번호";
    private final static String emailContent = "Tripamigo 인증 번호 \n";

    private String generateRandomCode() {
        return String.format("%05d", (int) (Math.random() * 100000));
    }

    @PostMapping("/send")
    @ResponseBody
    public boolean sendAuthCode(@RequestParam("email") String email, HttpSession session) {

        try {
            String orgCode = generateRandomCode();
            String salt = CipherUtil.generateSalt();
            String code = CipherUtil.hashEncoding(orgCode, salt);
            session.setAttribute(sessionSaltName, email + sessionSeparater + salt);
            session.setAttribute(sessionCodeName, email + sessionSeparater + code);
            emailService.sendMail(email, EmailService.DEFAULT_FROM_ADDRESS, emailSubject, emailContent + orgCode);
        } catch (Exception e) {
            session.invalidate();
            return false;
        }

        return true;
    }

    @PostMapping("/check")
    @ResponseBody
    public boolean checkAuthCode(@RequestParam("email") String email,
                                 @RequestParam("code") String code,
                                 HttpSession session) {
        String sessionSalt = (String) session.getAttribute(sessionSaltName);
        String sessionCode = (String) session.getAttribute(sessionCodeName);
        if (!sessionSalt.split(sessionSeparater)[0].equals(email)
                || !sessionCode.split(sessionSeparater)[0].equals(email)) {
            return false;
        }

        sessionSalt = sessionSalt.split(sessionSeparater)[1];
        sessionCode = sessionCode.split(sessionSeparater)[1];
        try {
            String encodedCode = CipherUtil.hashEncoding(code, sessionSalt);
            return encodedCode.equals(sessionCode);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

}
