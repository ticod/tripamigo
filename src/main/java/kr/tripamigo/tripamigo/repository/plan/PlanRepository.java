package kr.tripamigo.tripamigo.repository.plan;

import kr.tripamigo.tripamigo.domain.OpenScope;
import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {

    Plan save(Plan plan);
    List<Plan> findAllByStatusAndOpen(boolean status, OpenScope openScope);
    int countByStatus(boolean status);
    Optional<Plan> findById(Long planId);
    List<Plan> findAllByUserAndStatusAndOpenOrderBySeqDesc(User user, boolean status, OpenScope openScope);
    List<Plan> findAllByUserAndStatusOrderBySeqDesc(User user, boolean status);
    Page<Plan> findAllByStatus(boolean status, Pageable pageable);

}
