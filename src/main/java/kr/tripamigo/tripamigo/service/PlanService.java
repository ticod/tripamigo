package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.board.Plan;
import kr.tripamigo.tripamigo.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public List<Plan> listAll() {
        return planRepository.findAllByStatus(true);
    }

    public int countAll() {
        return planRepository.countByStatus(true);
    }

}
