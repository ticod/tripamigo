package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendType;

import java.util.List;

public interface RecommendRepository {
	
	Recommend save(Recommend recommend);
	List<Recommend> findAll();
	int countByRecommendTypeAndContentSeq(RecommendType recommendType, Long contentSeq);
	int countByUserSeqAndStatus(Long userSeq, boolean b);

}
