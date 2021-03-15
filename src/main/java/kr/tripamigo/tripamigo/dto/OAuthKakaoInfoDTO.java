package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class OAuthKakaoInfoDTO extends OAuthInfoDTO {

    private Integer id;
    private String nickname;

}