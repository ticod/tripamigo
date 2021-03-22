package kr.tripamigo.tripamigo.controller;
/*
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.OAuthKakaoInfoDTO;
import kr.tripamigo.tripamigo.dto.OAuthTokenDTO;
import kr.tripamigo.tripamigo.dto.UserIdOAuthType;
import kr.tripamigo.tripamigo.service.KakaoOAuthService;
import kr.tripamigo.tripamigo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private KakaoOAuthService kakaoOauthService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/kakao_auth_code")
    public String kakaoAuthCode() {
        return kakaoOauthService.getAuthCodeURI();
    }

    @RequestMapping("/kakao_login")
    public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {
        OAuthTokenDTO tokens = kakaoOauthService.getTokens(code);
        OAuthKakaoInfoDTO infoDTO = kakaoOauthService.getUserId(tokens.getAccessToken());

        String userId = UserIdOAuthType.KAKAO.getValue() + infoDTO.getId();
        User findUser = userService.selectUserOne(userId);

        if (findUser == null) {
            // 회원 가입
            User user = userService.joinByKakao(tokens, infoDTO);
            session.setAttribute("loginUser", userService.selectUserOne(userId));
        } else {
            // 로그인
            findUser = userService.loginByKakao(findUser, tokens);
            session.setAttribute("loginUser", findUser);
        }


        return "redirect:/community/home";
    }

    @RequestMapping("/kakao_unlink")
    public String kakaoUnlink(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");

        kakaoOauthService.unlink(loginUser.getUserAccessToken());
        // 탈퇴 처리
        userService.withdrawal(loginUser);
        session.invalidate();
        return "redirect:/home";
    }

    @RequestMapping("/kakao_logout")
    public String kakaoLogout(HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        kakaoOauthService.logout(user.getUserId().replace(UserIdOAuthType.KAKAO.getValue(), ""),
                user.getUserAccessToken());
        session.invalidate();
        return "redirect:/home";
    }

}
*/