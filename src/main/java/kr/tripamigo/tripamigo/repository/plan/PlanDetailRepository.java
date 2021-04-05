package kr.tripamigo.tripamigo.repository.plan;

import kr.tripamigo.tripamigo.domain.board.PlanDetail;

import java.util.List;

public interface PlanDetailRepository {

    List<PlanDetail> findAll();
    PlanDetail save(PlanDetail planDetail);

}
