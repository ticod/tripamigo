package kr.tripamigo.tripamigo.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class MagazineFormDTO {

	@NotBlank(message="제목을 입력하세요")
    private String subject;
	
	@NotBlank(message="내용을 입력하세요")
    private String content;
	
    private String tags;
    
    private String thumbnail;

}
