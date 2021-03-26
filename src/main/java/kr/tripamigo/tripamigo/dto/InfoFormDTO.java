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
	@NotBlank(message="장소 정보(별점)을 입력하세요")
	private String rating;

	@NotBlank(message="장소 정보(소요비용)를 입력하세요")
	private String budget;
	
	@NotBlank(message="장소 정보(장소명)를 입력하세요")
	private String name;
	
	@NotBlank(message="장소 정보(장소주소)를 입력하세요")
	private String address;
	
	@NotBlank(message="장소 정보(위도)를 입력하세요")
	private String lat;
	
	@NotBlank(message="장소 정보(경도)를 입력하세요")
	private String lng;
	

	
	

}
