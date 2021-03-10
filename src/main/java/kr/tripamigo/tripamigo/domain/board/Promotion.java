package kr.tripamigo.tripamigo.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("2")
@Getter @Setter
public class Promotion extends Board {

    @Enumerated
    @Column(name = "BOARD_CATEGORY")
    private promotionCategory boardCategory;

    private enum promotionCategory {
        FOOD,
        FESTIVAL,
        ATTRACTIONS,
        LODGMENT,
        ETC
    }

}
