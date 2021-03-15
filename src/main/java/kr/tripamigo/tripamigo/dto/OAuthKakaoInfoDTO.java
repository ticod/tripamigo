package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class OAuthKakaoInfoDTO {

    private Integer id;
    private String nickname;

}
