package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendType;

import java.util.List;

public interface RecommendRepository {
	
	Recommend save(Recommend recommend);
	List<Recommend> findAll();
	int countByRecommendTypeAndContentSeqAndStatus(RecommendType recommendType, Long contentSeq, boolean status);
	int countByUserSeqAndStatus(Long userSeq, boolean b);
	Recommend findByUserSeqAndRecommendTypeAndContentSeq(Long userSeq, RecommendType type, Long contentSeq);
	List<Recommend> findAllByUserSeqAndRecommendTypeAndStatus(Long userSeq, RecommendType type, boolean status);

}
