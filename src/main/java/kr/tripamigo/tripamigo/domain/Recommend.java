package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "RECOMMEND")
@Getter @Setter
@IdClass(RecommendId.class)
public class Recommend {

    @Id
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Id
    @Enumerated
    @Column(name = "RECOMMEND_TYPE")
    private RecommendType recommendType;

    @Id
    @Column(name = "RECOMMEND_SEQ")
    private Long contentSeq;

    @Column(name = "RECOMMEND_STATUS", insertable=false)
    private boolean status;

    @Column(name = "RECOMMEND_DATE", insertable=false)
    private LocalDateTime date;
    
    public void createRecommend(Long userSeq, RecommendType type, Long contentSeq) {
    	this.userSeq=userSeq;
    	this.recommendType = type;
    	this.contentSeq = contentSeq;
    }
}
