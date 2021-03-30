package kr.tripamigo.tripamigo.domain.board;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Period {

    private LocalDateTime start;
    private LocalDateTime end;

}
