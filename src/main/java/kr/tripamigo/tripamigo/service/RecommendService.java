package kr.tripamigo.tripamigo.service;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.Comment;
import kr.tripamigo.tripamigo.domain.Recommend;
import kr.tripamigo.tripamigo.domain.RecommendType;
import kr.tripamigo.tripamigo.domain.board.Info;
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
		
		public int countRecommend(RecommendType type, Long contentSeq) {
			return recommendRepository.countByRecommendTypeAndContentSeqAndStatus(type, contentSeq,true);
		}


		public Map<Info, Integer> countInfoRecommendList(List<Info> infoList) {
			
			Map<Info, Integer> infoRecommendMap = new TreeMap<Info, Integer>((o1, o2)-> o2.getInfoSeq().intValue()-o1.getInfoSeq().intValue());
			
			for(Info i : infoList) {
				int cnt = recommendRepository.countByRecommendTypeAndContentSeqAndStatus(RecommendType.INFO, i.getInfoSeq(),true);
				infoRecommendMap.put(i, cnt);
			}
			System.out.println(infoRecommendMap);
			
			return infoRecommendMap;
		}
		
		public Map<Comment, Integer> countCommentRecommend(List<Comment> commentList){
			Map<Comment, Integer> countCommentRecommend = new TreeMap<Comment, Integer>((o1,o2)->o2.getCommentSeq().intValue()-o1.getCommentSeq().intValue());
			for(Comment c : commentList) {
				int cnt = recommendRepository.countByRecommendTypeAndContentSeqAndStatus(RecommendType.COMMENT, c.getCommentSeq(), true);
				countCommentRecommend.put(c, cnt);
			}
			System.out.println("countCommentRecommend : " + countCommentRecommend);
			return countCommentRecommend;
		}
}
