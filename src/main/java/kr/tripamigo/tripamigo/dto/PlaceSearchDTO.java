package kr.tripamigo.tripamigo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class PlaceSearchDTO {

    private final String input;
    private final String fields;

}
