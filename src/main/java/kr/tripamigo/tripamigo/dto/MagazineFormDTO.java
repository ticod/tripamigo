package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class MagazineFormDTO {

    private String subject;
    private String content;
    private String tags;
    private String thumbnail;

}
