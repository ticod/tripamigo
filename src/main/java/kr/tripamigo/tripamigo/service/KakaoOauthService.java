package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.dto.KakaoTokenDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class KakaoOauthService implements OAuthService {

    @Value("${kakao.restapi.key}")
    private String REST_API_KEY;

    @Value("${kakao.restapi.redirect-uri")
    private String REDIRECT_URI;

    private final String KAUTH_HOST = "https://kauth.kakao.com";
    private final String KAPI_HOST = "https://kapi.kakao.com";

    private final String P_URI_CLIENT_ID = "&client_id=" + REST_API_KEY;
    private final String P_REDIRECT_URI = "&redirect_uri=" + REDIRECT_URI;
    private final String AUTH_CODE_URI = "/oauth/authorize?response_type=code" + P_URI_CLIENT_ID + P_REDIRECT_URI;

    private final String P_AUTH_CODE = "&code=";
    private final String TOKEN_URI = "/oauth/token?grant_type=authorization_code" + P_URI_CLIENT_ID + P_REDIRECT_URI;

    public String getAuthCodeURI() {
        return KAUTH_HOST + AUTH_CODE_URI + P_URI_CLIENT_ID + P_REDIRECT_URI;
    }

    public String getTokenURI(String AuthCode) {
        return KAUTH_HOST + TOKEN_URI + P_URI_CLIENT_ID + P_REDIRECT_URI + P_AUTH_CODE + AuthCode;
    }

    @Override
    public String getAccessToken(String authCode) {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(getTokenURI(authCode));

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "authorization_code");
        params.set("client_id", REST_API_KEY);
        params.set("redirect_uri", REDIRECT_URI + "/login/oauth_kakao");
        params.set("code", authCode);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        if (responseBody == null) {
            throw new LoginException("서버 에러", "/home");
        }

        KakaoTokenDTO kakaoTokenDTO = KakaoTokenDTO.builder()
                .accessToken((String) responseBody.get("access_token"))
                .refreshToken((String) responseBody.get("refresh_token"))
                .build();
        return kakaoTokenDTO.getAccessToken();

    }

    @Override
    public String getUserId(String accessToken) {
        return null;
    }
}
