package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class OAuthTokenDTO {

    private String accessToken;
    private String refreshToken;

}
