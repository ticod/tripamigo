package kr.tripamigo.tripamigo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public enum UserIdOAuthType {
    GOOGLE("G"),
    KAKAO("K");

    private final String value;
}
