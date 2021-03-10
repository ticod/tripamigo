package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "FOLLOW")
@Getter @Setter
public class Follow {

    @Id
//    @ManyToOne
    @Column(name = "USER_SEQ")
    private Long user;

//    @Id
//    @ManyToOne
    @Column(name = "FOLLOW_USER_SEQ")
    private int followUser;

    @Column(name = "FOLLOW_STATUS")
    private boolean followStatus;

    @Column(name = "FOLLOW_DATE")
    private LocalDateTime followDate;


}
