package kr.tripamigo.tripamigo.repository.plan;

import kr.tripamigo.tripamigo.domain.board.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDetailJpaRepository
        extends JpaRepository<PlanDetail, Long>, PlanDetailRepository {
}
