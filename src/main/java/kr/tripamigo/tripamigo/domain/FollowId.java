package kr.tripamigo.tripamigo.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.io.Serializable;

@EqualsAndHashCode
public class FollowId implements Serializable {

    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "FOLLOW_USER_SEQ")
    private Long followUserSeq;

}
