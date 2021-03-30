package kr.tripamigo.tripamigo.dto.plan;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlanDetailDTO {

//    private PeriodDTO periodDTO;
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

    private Area area;

    private Traffic traffic;
    private Integer budget;
    private String memo;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Area {
        String address;
        String placeName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class Traffic {
        private String org;
        private String info;
        private String des;
    }

    public LocalDateTime getStartDateTime() {
        return LocalDateTime.of(this.getStartYear(),
                this.getStartMonth(), this.getStartDay(),
                this.getStartTime(), 0);
    }

    public LocalDateTime getEndDateTime() {
        return LocalDateTime.of(this.getEndYear(),
                this.getEndMonth(), this.getEndDay(),
                this.getEndTime(), 0);
    }

}
