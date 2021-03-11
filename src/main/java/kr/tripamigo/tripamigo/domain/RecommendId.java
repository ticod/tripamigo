package kr.tripamigo.tripamigo.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Getter @Setter
@EqualsAndHashCode
public class RecommendId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @Enumerated
    @Column(name = "RECOMMEND_TYPE")
    private RecommendType recommendType;

    @Column(name = "RECOMMEND_SEQ")
    private int contentSeq;

}
