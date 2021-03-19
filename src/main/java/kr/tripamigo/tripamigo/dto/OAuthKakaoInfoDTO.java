package kr.tripamigo.tripamigo.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
@Builder
public class OAuthKakaoInfoDTO extends OAuthInfoDTO {

    private Integer id;

}
