package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.board.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanJpaRepository extends JpaRepository<Plan, Long>, PlanRepository {
}
