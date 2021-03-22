package kr.tripamigo.tripamigo.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class DiaryFormDTO {

	@NotBlank(message="제목을 입력하세요")
    private String subject;
	
	@NotBlank(message="내용을 입력하세요")
    private String content;
    
    private MultipartFile thumbnail;

}