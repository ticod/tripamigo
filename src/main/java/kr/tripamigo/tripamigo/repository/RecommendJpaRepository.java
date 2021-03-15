package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendJpaRepository
        extends JpaRepository<Recommend, RecommendId>, RecommendRepository {
}
