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
public class CommentFormDTO {
	
	private String id;
	
	private String boardSeq;
	
    private String content;
	
    private String boardType;
    
}
