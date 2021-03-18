package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PeriodDTO {

    private String startYear;
    private String startMonth;
    private String startDay;
    private String startTime;

    private String endYear;
    private String endMonth;
    private String endDay;
    private String endTime;

}
