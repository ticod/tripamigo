package kr.tripamigo.tripamigo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PeriodDTO {

    @NotBlank(message = "연도를 입력해주세요")
    private int startYear;
    @NotBlank(message = "월을 입력해주세요")
    private int startMonth;
    @NotBlank(message = "일을 입력해주세요")
    private int startDay;
    @NotBlank(message = "시간을 입력해주세요")
    private int startTime;

    @NotBlank(message = "연도를 입력해주세요")
    private int endYear;
    @NotBlank(message = "월을 입력해주세요")
    private int endMonth;
    @NotBlank(message = "일을 입력해주세요")
    private int endDay;
    @NotBlank(message = "시간을 입력해주세요")
    private int endTime;

}
