package kr.tripamigo.tripamigo.service;

public interface OAuthService {

    String getAccessToken(String authCode);
    String getUserId(String accessToken);

}
