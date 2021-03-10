package kr.tripamigo.tripamigo.domain.board;

import kr.tripamigo.tripamigo.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "INFO")
@Getter @Setter
public class Info {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INFO_SEQ")
    private Long infoSeq;

    @Column(name = "INFO_STATUS")
    private boolean infoStatus;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Column(name = "INFO_SUBJECT")
    private String infoSubject;

    @Column(name = "INFO_CONTENT")
    private String infoContent;

    @Column(name = "INFO_REGDATE")
    private Date infoRegdate;

    @Column(name = "INFO_HITS")
    private int infoHits;

    @Embedded
    private Area area;

}
