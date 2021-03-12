package kr.tripamigo.tripamigo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	@NotEmpty(message="이메일 필수 입력")
	@Email(message="email형식으로 입력")
    private String email;
	
	@NotEmpty(message="년도 입력")
    private String year;
    
    @Size(min=1, max=12, message="1월부터 12월까지만 입력가능")
    private String month;
    
    @Size(min=1, max=31, message="1일부터 31일까지만 입력가능")
    private String day;
    
    @NotEmpty(message="별명 필수 입력")
    private String nickname;
    
    @NotNull(message="dfldkfj")
    private int gender;

}
