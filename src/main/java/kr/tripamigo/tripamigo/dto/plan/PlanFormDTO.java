package kr.tripamigo.tripamigo.dto.plan;

import kr.tripamigo.tripamigo.domain.OpenScope;
import kr.tripamigo.tripamigo.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PlanFormDTO {

    private String subject;
    private User user;
    private String content;
    private OpenScope open;

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;

}
