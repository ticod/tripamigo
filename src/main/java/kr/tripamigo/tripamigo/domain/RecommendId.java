package kr.tripamigo.tripamigo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

//@Embeddable
@Getter @Setter
@EqualsAndHashCode
public class RecommendId implements Serializable {

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

}
