package kr.tripamigo.tripamigo.controller;

import kr.tripamigo.tripamigo.dto.OAuthKakaoInfoDTO;
import kr.tripamigo.tripamigo.dto.OAuthTokenDTO;
import kr.tripamigo.tripamigo.service.KakaoOAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private KakaoOAuthService kakaoOauthService;

    @ResponseBody
    @RequestMapping("/kakao_auth_code")
    public String kakaoAuthCode() {
        return kakaoOauthService.getAuthCodeURI();
    }

    @RequestMapping("/kakao_login")
    public String kakaoLogin(@RequestParam("code") String code) {
        OAuthTokenDTO tokens = kakaoOauthService.getTokens(code);
        OAuthKakaoInfoDTO infoDTO = kakaoOauthService.getUserId(tokens.getAccessToken());
        /*
        TODO: 로그인 or 회원가입 처리
         */
        return "redirect:/home";
    }

    @RequestMapping("/kakao_unlink")
    public String kakaoUnlink(String accessToken) {
        kakaoOauthService.unlink(accessToken);
        return "redirect:/home";
    }

}
