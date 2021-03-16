package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.dto.OAuthKakaoInfoDTO;
import kr.tripamigo.tripamigo.dto.OAuthTokenDTO;
import kr.tripamigo.tripamigo.exception.LoginException;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class KakaoOAuthService implements OAuthService<OAuthKakaoInfoDTO> {

    @Autowired
    private JSONParser jsonParser;

    @Value("${kakao.restapi.key}")
    public String REST_API_KEY;

    @Value("${kakao.restapi.redirect-uri}")
    private String REDIRECT_URI;

    @Value("${kakao.admin.key}")
    private String ADMIN_KEY;

    private final String KAUTH_HOST = "https://kauth.kakao.com";
    private final String KAPI_HOST = "https://kapi.kakao.com";

    private final String P_RESPONSE_TYPE = "?response_type=code";
    private final String P_URI_CLIENT_ID = "&client_id=";
    private final String P_REDIRECT_URI = "&redirect_uri=";

    private final String AUTH_CODE_URI = "/oauth/authorize";
    private final String TOKEN_URI = "/oauth/token";
    private final String USER_INFO_URI = "/v2/user/me";

    public String getAuthCodeURI() {
        return KAUTH_HOST + AUTH_CODE_URI + P_RESPONSE_TYPE + P_URI_CLIENT_ID + REST_API_KEY + P_REDIRECT_URI + REDIRECT_URI;
    }

    public String getTokenURI() {
        return KAUTH_HOST + TOKEN_URI;
    }

    public String getUserIdURI() {
        return KAPI_HOST + USER_INFO_URI;
    }

    @Override
    public OAuthTokenDTO getTokens(String authCode) {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(getTokenURI());

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "authorization_code");
        params.set("client_id", REST_API_KEY);
        params.set("redirect_uri", REDIRECT_URI);
        params.set("code", authCode);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);
        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        System.out.println(responseBody);

        if (responseBody == null) {
            throw new LoginException("서버 에러", "/home");
        }

        return OAuthTokenDTO.builder()
                .accessToken((String) responseBody.get("access_token"))
                .refreshToken((String) responseBody.get("refresh_token"))
                .build();

    }

    @Override
    public OAuthKakaoInfoDTO getUserId(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(getUserIdURI());

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        System.out.println(responseBody);

        if (responseBody == null) {
            throw new LoginException("서버 에러", "/home");
        }

        JSONObject kakaoAccount = null;
        LocalDateTime birth = null;
        String profileImage = null;
        String nickname = null;
        String email = null;

        try {
            kakaoAccount = (JSONObject) jsonParser.parse("kakao_account");
            email = (String) kakaoAccount.get("email");
            String birthday = (String) kakaoAccount.get("birthday");
            birth = LocalDate.parse(birthday).atStartOfDay();
            JSONObject profile = null;
            try {
                profile = (JSONObject) jsonParser.parse("kakao_account");
                profileImage = (String) profile.get("profile_image_url");
                nickname = (String) profile.get("nickname");
            } catch(ParseException e) {}
        } catch (ParseException e) {}

        return OAuthKakaoInfoDTO.builder()
                .id((Integer) responseBody.get("id"))
                .nickname(nickname != null ? nickname : "Tripamigo")
                .birth(birth != null ? birth : LocalDateTime.now())
                .email(email != null ? email : "kakao account")
                .profileImage(profileImage)
                .build();

    }

    public int logout(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(KAPI_HOST + "/v1/user/logout");

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", getUserId(accessToken).getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        if (responseBody == null) {
            throw new LoginException("서버 에러", "/home");
        }

        return (int) responseBody.get("id");

    }

    public void unlink(String accessToken) {

        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(KAPI_HOST + "/v1/user/unlink");

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", getUserId(accessToken).getId());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(params, headers);

        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);
        JSONObject responseBody = apiResponse.getBody();

        System.out.println(responseBody);

    }
}
