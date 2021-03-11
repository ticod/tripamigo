package kr.tripamigo.tripamigo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Table(name = "RECOMMEND")
@Getter @Setter
public class Recommend {

    @EmbeddedId
    private RecommendId recommendId;

    @Column(name = "RECOMMEND_STATUS")
    private boolean status;

    @Column(name = "RECOMMEND_DATE")
    private LocalDateTime date;

}
