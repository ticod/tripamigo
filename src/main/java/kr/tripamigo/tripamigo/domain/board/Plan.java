package kr.tripamigo.tripamigo.domain.board;

import kr.tripamigo.tripamigo.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PLAN")
@Getter @Setter
public class Plan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAN_SEQ")
    private Long planSeq;

    @Column(name = "PLAN_STATUS")
    private boolean planStatus;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "PLAN_SUBJECT")
    private String planSubject;

    @Column(name = "PLAN_CONTENT")
    private String planContent;

    @Column(name = "PLAN_REGDATE")
    private Date planRegdate;

    @Column(name = "PLAN_HITS")
    private int planHits;

    @Column(name = "PLAN_OPEN")
    private int planOpen;

    @Column(name = "PLAN_START")
    private Date planStart;

    @Column(name = "PLAN_END")
    private Date planEnd;


}
