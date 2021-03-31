package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.OpenScope;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.domain.board.PlanDetail;
import kr.tripamigo.tripamigo.dto.plan.PlanDetailDTO;
import kr.tripamigo.tripamigo.repository.plan.PlanDetailRepository;
import kr.tripamigo.tripamigo.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanDetailRepository planDetailRepository;

    public Plan createAndReturn(Plan plan) {
        return planRepository.save(plan);
    }

    public List<Plan> listAll() {
        return planRepository.findAllByStatusAndOpen(true, OpenScope.PUBLIC);
    }

    public int countAll() {
        return planRepository.countByStatus(true);
    }

    public Plan getPlanBy(Long planId) {
        return planRepository.findById(planId).orElseThrow();
    }

    public void savePlanDetails(Plan plan, List<PlanDetailDTO> planDetailDTOs) {
        List<PlanDetail> planDetails = planDetailDTOs.stream()
                .map(o -> new PlanDetail().createByDTO(plan, o))
                .collect(Collectors.toList());
//        planDetailRepository.saveAll(planDetails);
    }

    public List<Plan> listMyPlan(User user) {
        return planRepository.findAllByUserAndStatusOrderBySeqDesc(user, true);
    }

}
