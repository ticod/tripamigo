package kr.tripamigo.tripamigo.domain.board;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.PlanFormDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PLAN")
@Getter @Setter
public class Plan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAN_SEQ")
    private Long seq;

    @Column(name = "PLAN_STATUS", insertable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "PLAN_SUBJECT")
    private String subject;

    @Column(name = "PLAN_CONTENT")
    private String content;

    @Column(name = "PLAN_REGDATE", insertable = false)
    private LocalDateTime regdate;

    @Column(name = "PLAN_HITS", insertable = false)
    private int hits;

    @Column(name = "PLAN_OPEN")
    private boolean open;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "start",
                    column = @Column(name = "PLAN_START")
            ),
            @AttributeOverride(
                    name = "end",
                    column = @Column(name = "PLAN_END")
            )
    })
    private Period period;
    
    public void createFrom(PlanFormDTO planFormDTO) {
        this.subject = planFormDTO.getSubject();
        this.user = planFormDTO.getUser();
        this.content = planFormDTO.getContent();
        this.open = planFormDTO.isOpen();
    }

}
