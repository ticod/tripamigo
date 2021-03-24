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

    public final static String sessionAuthCheckName = "emailAuthCheck";

    private final static String sessionSaltName = "emailAuthSalt";
    private final static String sessionCodeName = "emailAuthCode";
    private final static String sessionSeparator = "_";

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
            session.setAttribute(sessionSaltName, email + sessionSeparator + salt);
            session.setAttribute(sessionCodeName, email + sessionSeparator + code);
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

        // 세션 값 확인
        String sessionSalt = null;
        String sessionCode = null;
        try {
            sessionSalt = (String) session.getAttribute(sessionSaltName);
            sessionCode = (String) session.getAttribute(sessionCodeName);
        } catch (Exception e) {
            return false;
        }

        // 세션의 이메일과 input 이메일이 같은지 검증
        if (!sessionSalt.split(sessionSeparator)[0].equals(email)
                || !sessionCode.split(sessionSeparator)[0].equals(email)) {
            return false;
        }

        // 세션 문자열에서 salt값과 code값만 추출
        // class 형식으로 바꾸는게 더 깔끔할 것 같음
        sessionSalt = sessionSalt.split(sessionSeparator)[1];
        sessionCode = sessionCode.split(sessionSeparator)[1];
        try {
            String encodedCode = CipherUtil.hashEncoding(code, sessionSalt);
            if (encodedCode.equals(sessionCode)) {
                session.removeAttribute(sessionSaltName);
                session.removeAttribute(sessionCodeName);
                session.setAttribute(sessionAuthCheckName, email);
                return true;
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

}
