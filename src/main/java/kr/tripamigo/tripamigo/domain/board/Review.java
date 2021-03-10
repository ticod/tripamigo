package kr.tripamigo.tripamigo.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@DiscriminatorValue("0")
@Getter @Setter
public class Review extends Board {

    @ManyToOne
    @JoinColumn(name = "PLAN_SEQ")
    private Plan plan;

}
