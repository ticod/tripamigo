package kr.tripamigo.tripamigo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class UserFormDTO {

	@NotEmpty(message="아이디를 입력해주세요")
    private String id;
	
	@NotEmpty(message="비밀번호를 입력해주세요")
    private String password;
	
	
    private String email;
    private String year;
    private String month;
    private String day;
    private String nickname;
    private int gender;

}
