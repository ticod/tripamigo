package kr.tripamigo.tripamigo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class APIKey {

    public static String GOOGLE_MAP;
    public static String KAKAO_ADMIN;
    public static String KAKAO_RESTAPI;

    @Value("${google.map.key}")
    private void setGoogleMap(String googleMap) {
        GOOGLE_MAP = googleMap;
    }

    @Value("${kakao.admin.key}")
    private void setKakaoAdmin(String kakaoAdmin) {
        KAKAO_RESTAPI = kakaoAdmin;
    }

    @Value("${kakao.restapi.key}")
    private void setKakaoRestapi(String kakaoRestapi) {
        KAKAO_ADMIN = kakaoRestapi;
    }

}
