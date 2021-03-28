package kr.tripamigo.tripamigo.dto.plan;

import lombok.*;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PeriodDTO {

    @NotNull(message = "연도를 입력해주세요")
    private Integer startYear;
    @NotNull(message = "월을 입력해주세요")
    private Integer startMonth;
    @NotNull(message = "일을 입력해주세요")
    private Integer startDay;
    @NotNull(message = "시간을 입력해주세요")
    private Integer startTime;

    @NotNull(message = "연도를 입력해주세요")
    private Integer endYear;
    @NotNull(message = "월을 입력해주세요")
    private Integer endMonth;
    @NotNull(message = "일을 입력해주세요")
    private Integer endDay;
    @NotNull(message = "시간을 입력해주세요")
    private Integer endTime;

}
