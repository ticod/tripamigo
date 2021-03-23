package kr.tripamigo.tripamigo.domain.board;

import kr.tripamigo.tripamigo.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "INFO")
@Getter @Setter
public class Info {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INFO_SEQ")
    private Long infoSeq;

    @Column(name = "INFO_STATUS", insertable=false)
    private boolean infoStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "INFO_SUBJECT")
    private String infoSubject;

    @Column(name = "INFO_CONTENT")
    private String infoContent;

    @Column(name = "INFO_REGDATE", insertable=false)
    private LocalDateTime infoRegdate;

    @Column(name = "INFO_HITS", insertable=false)
    private int infoHits;

    @Embedded
    private Area area;

}
