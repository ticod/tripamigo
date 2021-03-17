package kr.tripamigo.tripamigo.dto;

import java.util.Date;

import javax.validation.constraints.DecimalMax;
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
	
	@DecimalMax(value="2021")
	@Size(min=4, max=4, message="4자리로 입력")
	@NotEmpty(message="년도 입력")
    private String year;
    
	@DecimalMax(value="12")
    @Size(min=2, max=2, message="두자리로 입력")
	@NotEmpty(message="월 입력")
    private String month;
    
	@DecimalMax(value="31")
    @Size(min=2, max=2, message="두자리로 입력")
	@NotEmpty(message="일 입력")
    private String day;
    
    @NotEmpty(message="별명 필수 입력")
    private String nickname;
    
    @NotNull(message="dfldkfj")
    private int gender;

}
