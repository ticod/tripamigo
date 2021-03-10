package kr.tripamigo.tripamigo.domain.board;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Area {

    @Column(name = "INFO_AREA_BUDGET")
    private int infoAreaBudget;

    @Column(name = "INFO_AREA_RATING")
    private int infoAreaRating;

    @Column(name = "INFO_AREA_NAME")
    private String infoAreaName;

    @Column(name = "INFO_AREA_ADDRESS")
    private String infoAreaAddress;

    @Column(name = "INFO_AREA_LAT")
    private double infoAreaLat;

    @Column(name = "INFO_AREA_LNG")
    private double infoAreaLng;

    protected Area() {}

}
