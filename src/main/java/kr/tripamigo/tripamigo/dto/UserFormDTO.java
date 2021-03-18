package kr.tripamigo.tripamigo.dto;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class UserFormDTO {

    @Length(max=16, message = "아이디 길이는 최대 16자까지 가능합니다")
    @Pattern(regexp = "^[a-z0-9]*", message = "영어 소문자, 숫자만 입력해주세요")
	@NotEmpty(message="아이디를 입력해주세요")
    private String id;
	
	@NotEmpty(message="비밀번호를 입력해주세요")
    private String password;
	
	@NotEmpty(message="이메일 필수 입력")
	@Email(message="이메일 형식으로 입력")
    private String email;
	
	@DecimalMax(value="2021", message = "2021 이하 입력")
	@Size(min=4, max=4, message="4자리로 입력")
	@NotEmpty(message="년도 입력")
    private String year;
    
	@DecimalMax(value="12", message = "12 이하 입력")
    @Size(min=2, max=2, message="2자리로 입력")
	@NotEmpty(message="월 입력")
    private String month;
    
	@DecimalMax(value="31", message = "31 이하 입력")
    @Size(min=2, max=2, message="2자리로 입력")
	@NotEmpty(message="일 입력")
    private String day;
    
    @NotEmpty(message="별명 필수 입력")
    private String nickname;
    
    @NotNull(message = "성별을 선택하세요")
    private int gender;

}
