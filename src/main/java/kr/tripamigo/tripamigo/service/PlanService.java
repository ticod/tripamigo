package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.domain.board.PlanDetail;
import kr.tripamigo.tripamigo.dto.plan.PlanDetailDTO;
import kr.tripamigo.tripamigo.repository.plan.PlanDetailRepository;
import kr.tripamigo.tripamigo.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final PlanDetailRepository planDetailRepository;

    public Plan createAndReturn(Plan plan) {
        return planRepository.save(plan);
    }

    public List<Plan> listAll() {
        return planRepository.findAllByStatus(true);
    }

    public int countAll() {
        return planRepository.countByStatus(true);
    }

    public Plan getPlanBy(Long planId) {
        return planRepository.findById(planId).orElseThrow();
    }

    public PlanDetail savePlanDetail(PlanDetailDTO planDetailDTO) {
        return null;
    }
}
