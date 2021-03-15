package kr.tripamigo.tripamigo.service;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

    
//    public int recommendCount(RecommendType type, int seq) {
//    	return recommendRepository.countByRecommendTypeAndContentSeq(type, seq);
//    }
}
