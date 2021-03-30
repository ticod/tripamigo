package kr.tripamigo.tripamigo.domain.board;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TrafficInfo {

    @Column(name = "DETAIL_PLAN_TRAFFIC")
    private String traffic;

    @Column(name = "DETAIL_PLAN_ORG")
    private String org;

    @Column(name = "DETAIL_PLAN_DES")
    private String des;

}
