package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.OpenScope;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.dto.plan.PlanFormDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
public class PlanServiceTest {

    @Autowired
    PlanService planService;

    @Autowired
    UserService userService;

    @BeforeEach
    void beforeEach() {
        User user = userService.selectUserOne("test");

        Plan plan = new Plan();
        PlanFormDTO planFormDTO = new PlanFormDTO();

        planFormDTO.setSubject("테스트용 제목");
        planFormDTO.setUser(user);
        planFormDTO.setContent("내용 내용 내용 내용");
        planFormDTO.setOpen(OpenScope.PUBLIC);
        planFormDTO.setPeriodStart(LocalDateTime.now());
        planFormDTO.setPeriodEnd(LocalDateTime.now());

        planService.createAndReturn(planFormDTO);
    }

    @Test
    void list() {
        List<Plan> plans = planService.listAll();
        Assertions.assertThat(plans).hasSizeGreaterThan(0);
    }

}
