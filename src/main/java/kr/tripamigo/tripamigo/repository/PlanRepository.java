package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Plan;

import java.util.List;

public interface PlanRepository {

    List<Plan> findAllByStatus(boolean status);
    int countByStatus(boolean status);

}
