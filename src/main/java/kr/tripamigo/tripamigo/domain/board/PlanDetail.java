package kr.tripamigo.tripamigo.domain.board;

import kr.tripamigo.tripamigo.dto.plan.PlanDetailDTO;
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

    public PlanDetail createByDTO(Plan plan, PlanDetailDTO o) {
        System.out.println("[createByDTO] " + o);
        this.plan = plan;
        this.budget = (o.getBudget() != null) ? o.getBudget() : 0;
        this.memo = o.getMemo();
        this.period = Period.builder()
                .start(o.getStartDateTime())
                .end(o.getEndDateTime())
                .build();
        if (o.getArea() != null) {
            this.area = o.getArea().getAddress() + " / " + o.getArea().getPlaceName();
        }
        if (o.getTraffic() != null) {
            this.trafficInfo = TrafficInfo.builder()
                    .org(o.getTraffic().getOrg())
                    .des(o.getTraffic().getDes())
                    .traffic(o.getTraffic().getInfo())
                    .build();
        }
        return this;
    }
}
