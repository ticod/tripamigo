package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "RECOMMEND")
@Getter @Setter
public class Recommend {

    @Id
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "RECOMMEND_TYPE")
    private int contentType;

    @Column(name = "RECOMMEND_SEQ")
    private int contentSeq;

    @Column(name = "RECOMMEND_STATUS")
    private boolean status;

    @Column(name = "RECOMMEND_DATE")
    private LocalDateTime date;

}
