package kr.tripamigo.tripamigo.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.Recommend;
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
    
    	public void hitsRecommend(Recommend recommend) {
    		recommendRepository.save(recommend);
    	}


		public void cancelRecommend(Recommend recommend) {
			recommendRepository.save(recommend);
		}


		public Recommend readRecommend(Long userSeq, RecommendType type, Long contentSeq) {
			return recommendRepository.findByUserSeqAndRecommendTypeAndContentSeq(userSeq, type, contentSeq);
		}
		
		public List<Recommend> userRecommendList(Long userSeq, RecommendType type, boolean status){
			System.out.println("RecommendService::userRecommendList =>"+recommendRepository.findAllByUserSeqAndRecommendTypeAndStatus(userSeq, type, status));
			return recommendRepository.findAllByUserSeqAndRecommendTypeAndStatus(userSeq, type, status);
		}
}
