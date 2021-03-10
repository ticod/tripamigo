package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class UserFormDTO {

    private String id;
    private String password;
    private String email;
    private String year;
    private String month;
    private String day;
    private String nickname;
    private int gender;

}
