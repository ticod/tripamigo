package kr.tripamigo.tripamigo.domain.board;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Area {

    @Column(name = "INFO_AREA_BUDGET")
    private int budget;

    @Column(name = "INFO_AREA_RATING")
    private int rating;

    @Column(name = "INFO_AREA_NAME")
    private String name;

    @Column(name = "INFO_AREA_ADDRESS")
    private String address;

    @Column(name = "INFO_AREA_LAT")
    private double lat;

    @Column(name = "INFO_AREA_LNG")
    private double lng;

    /**
     * Setter를 의도적으로 막아놓음 (값 공유 방지)
     * 따라서 같은 Area 객체를 각각 다른 상위 객체에서 사용시
     * 아래 생성자를 통해 사용
     */
    public Area(Area area) {
        this.budget = budget;
        this.rating = rating;
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return getBudget() == area.getBudget()
                && getRating() == area.getRating()
                && Double.compare(area.getLat(), getLat()) == 0
                && Double.compare(area.getLng(), getLng()) == 0
                && Objects.equals(getName(), area.getName())
                && Objects.equals(getAddress(), area.getAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBudget(), getRating(), getName(),
                getAddress(), getLat(), getLng());
    }
}
