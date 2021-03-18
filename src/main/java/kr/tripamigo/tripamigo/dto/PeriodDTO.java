package kr.tripamigo.tripamigo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PeriodDTO {

    private int startYear;
    private int startMonth;
    private int startDay;
    private int startTime;

    private int endYear;
    private int endMonth;
    private int endDay;
    private int endTime;

}
