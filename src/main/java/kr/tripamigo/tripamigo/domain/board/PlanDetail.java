package kr.tripamigo.tripamigo.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "PLAN_DETAIL")
@Getter @Setter
public class PlanDetail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DETAIL_PLAN_SEQ")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLAN_SEQ")
    private Plan plan;

    @Column(name = "DETAIL_PLAN_AREA")
    private String area;

    @Column(name = "DETAIL_PLAN_BUDGET")
    private int budget;

    @Column(name = "DETAIL_PLAN_MEMO")
    private String memo;

    @Embedded
    private TrafficInfo trafficInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "start",
                    column = @Column(name = "DETAIL_PLAN_START")
            ),
            @AttributeOverride(
                    name = "end",
                    column = @Column(name = "DETAIL_PLAN_END")
            )
    })
    private Period period;

}
