package kr.tripamigo.tripamigo.dto;

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
    private boolean open;

    private LocalDateTime periodStart;
    private LocalDateTime periodEnd;

}
