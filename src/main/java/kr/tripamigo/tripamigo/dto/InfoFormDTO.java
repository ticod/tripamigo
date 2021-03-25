package kr.tripamigo.tripamigo.dto;

import javax.validation.constraints.NotBlank;

import kr.tripamigo.tripamigo.domain.board.Area;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class InfoFormDTO {

	@NotBlank(message="제목을 입력하세요")
    private String subject;
	
	@NotBlank(message="내용을 입력하세요")
    private String content;
	
	//area 객체 관련 수정 필요. 위도,경도,주소 구글맵 API
	private String rating;
	private String budget;
	private String name;
	private String address;
	private String lat;
	private String lng;
	

	
	

}
