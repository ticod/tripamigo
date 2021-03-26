package kr.tripamigo.tripamigo.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AreaSummaryDTO {
    private final double budgetAvg;
    private final double ratingAvg;
}
