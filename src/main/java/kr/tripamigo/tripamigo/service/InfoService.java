package kr.tripamigo.tripamigo.service;

import java.util.List;
import java.util.Locale;

import kr.tripamigo.tripamigo.dto.AreaSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.domain.board.Area;
import kr.tripamigo.tripamigo.domain.board.Info;
import kr.tripamigo.tripamigo.domain.board.Magazine;
import kr.tripamigo.tripamigo.dto.InfoFormDTO;
import kr.tripamigo.tripamigo.exception.NoPageException;
import kr.tripamigo.tripamigo.repository.InfoRepository;
import lombok.RequiredArgsConstructor;

/**
 * 여행 후기, 매거진, 여행 홍보 게시글
 */
@Transactional
@RequiredArgsConstructor
public class InfoService {

    private final InfoRepository infoRepository;

    @Autowired
    private MessageSource messageSource;

//    public void writeMagazine(MagazineFormDTO magazineFormDTO, User user) {
//        Magazine magazine = new Magazine();
//        magazine.setUser(user);
//        magazine.setBoardSubject(magazineFormDTO.getSubject());
//        magazine.setBoardContent(magazineFormDTO.getContent());
//        magazine.setBoardThumbnail(magazineFormDTO.getThumbnail());
//        magazine.setBoardTag(magazineFormDTO.getTags());
//        
//        boardRepository.save(magazine);
//    }
//    
//    public List<Magazine> magazineList(){
//    	List<Magazine> magazineList;
//    	magazineList = magazineRepository.findAllByBoardStatusOrderByBoardSeqDesc(true);
//    	return magazineList;
//    } 
//    
//    public Magazine readMagazine(Long boardSeq) {
//    	Magazine magazine = magazineRepository
//                .findById(boardSeq)
//                .orElseGet(() -> {
//                    throw new NoPageException(messageSource
//                    .getMessage("error.404", null, Locale.getDefault()), "/community/home");
//                });
//    	
//    	return magazine;
//    	
//    	
//    }
//
//	public void delete(Board board) {
//		boardRepository.save(board);
//	}
//
//	public void boardHitsUp(Long boardSeq) {
//		boardRepository.boardHitsUp(boardSeq);
//	}
//
//	public void updateMagazine(Magazine dbMagazine) {
//		boardRepository.save(dbMagazine);
//		
//	}

	public List<Info> infoList() {
		List<Info> infoList;
    	infoList = infoRepository.findAllByInfoStatusOrderByInfoSeqDesc(true);
		return infoList;
	}

	public void writeInfo(InfoFormDTO infoFormDTO, Area area, User user) {
		Info info = new Info();
		info.setInfoSubject(infoFormDTO.getSubject());
		info.setInfoContent(infoFormDTO.getContent());
		info.setUser(user);
		info.setArea(area);
		System.out.println(info);
		infoRepository.save(info);
	}

	public Info readInfo(Long iSeq) {
		
		Info info = infoRepository
				.findById(iSeq)
				.orElseGet(()->{
					throw new NoPageException(messageSource
					.getMessage("error.404", null, Locale.getDefault()), "/community/info");
				});
		
		return info;
	}

	public void updateInfo(Info dbInfo) {
		infoRepository.save(dbInfo);
	}
	public AreaSummaryDTO getAreaDataBy(String areaAddress, String areaName) {

		List<Info> datas
				= infoRepository.findAllByInfoStatusAndAreaNameAndAreaAddress(true, areaName, areaAddress);

		double budgetAvg = 0;
		double ratingAvg = 0;

		for (Info data : datas) {
			budgetAvg += data.getArea().getBudget();
			ratingAvg += data.getArea().getRating();
		}

		budgetAvg /= datas.size();
		ratingAvg /= datas.size();

		return AreaSummaryDTO.builder()
				.budgetAvg(budgetAvg)
				.ratingAvg(ratingAvg)
				.build();
	}

	public void delete(Info info) {
		infoRepository.save(info);
	}
	
	public void hitsUp(Long infoSeq) {
		infoRepository.hitsUp(infoSeq);
	}
	
}
