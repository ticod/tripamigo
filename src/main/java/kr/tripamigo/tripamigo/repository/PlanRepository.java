package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    Plan save(Plan plan);
    List<Plan> findAllByStatus(boolean status);
    int countByStatus(boolean status);

}
