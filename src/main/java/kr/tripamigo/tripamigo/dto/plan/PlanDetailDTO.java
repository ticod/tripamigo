package kr.tripamigo.tripamigo.dto.plan;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlanDetailDTO {

    private LocalDateTime start;
    private LocalDateTime end;

    private Area area;
    private Traffic trafficInfoDTO;
    private String budget;
    private String memo;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    private static class Area {
        String address;
        String placeName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    private static class Traffic {
        String info;
        String org;
        String des;
    }

}
