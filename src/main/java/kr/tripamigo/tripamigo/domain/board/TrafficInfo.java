package kr.tripamigo.tripamigo.domain.board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TrafficInfo {

    @Column(name = "DETAIL_PLAN_TRAFFIC")
    private String traffic;

    @Column(name = "DETAIL_PLAN_ORG")
    private String org;

    @Column(name = "DETAIL_PLAN_DES")
    private String des;

}
