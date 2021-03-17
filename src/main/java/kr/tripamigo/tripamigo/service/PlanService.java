package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

}
