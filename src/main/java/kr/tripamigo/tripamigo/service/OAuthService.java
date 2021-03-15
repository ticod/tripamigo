package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.dto.OAuthInfoDTO;
import kr.tripamigo.tripamigo.dto.OAuthTokenDTO;

public interface OAuthService<T extends OAuthInfoDTO> {

    OAuthTokenDTO getTokens(String authCode);
    T getUserId(String accessToken);

}
