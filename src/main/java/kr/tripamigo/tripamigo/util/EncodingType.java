package kr.tripamigo.tripamigo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum EncodingType {

    HASH("SHA-256");

    private final String value;

}
