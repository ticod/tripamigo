package kr.tripamigo.tripamigo.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.tripamigo.tripamigo.dto.BoardType;
import kr.tripamigo.tripamigo.dto.paging.PagingDTO;
import kr.tripamigo.tripamigo.repository.InfoRepository;
import kr.tripamigo.tripamigo.repository.MagazineRepository;
import kr.tripamigo.tripamigo.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class PagingService {

    private final PlanRepository planRepository;
    private final InfoRepository infoRepository;
    private final MagazineRepository magazineRepository;

    public <T extends BoardType> PagingDTO getPaging(T t, Pageable pageable) {
        if (t.equals(T.PLAN)) {
            int count = planRepository.countByStatus(true);
            return PagingDTO.getPagingDTO(pageable, count, PagingDTO.BLOCK_SIZE_M);
        } else if (t.equals(T.INFO)) {
            int count = infoRepository.countByInfoStatus(true);
            return PagingDTO.getPagingDTO(pageable, count, PagingDTO.BLOCK_SIZE_M);
        } else if (t.equals(T.DIARY)) {
            // TODO
        } else if (t.equals(T.MAGAZINE)) {
            int count = magazineRepository.countAllByBoardStatus(true);
            return PagingDTO.getPagingDTO(pageable, count, 8);
        	
        	
        } else if (t.equals(T.PROMOTION)) {
            // TODO
        } else if (t.equals(T.REVIEW)) {
            // TODO
        }
        return null;
    }
/////////////
    public <T extends BoardType> PagingDTO getSearchPaging(T t, Pageable pageable, String findString) {
        if (t.equals(T.PLAN)) {
            int count = planRepository.countByStatus(true);
            return PagingDTO.getPagingDTO(pageable, count, PagingDTO.BLOCK_SIZE_M);
        } else if (t.equals(T.INFO)) {
            int count = infoRepository.countByInfoStatus(true);
            return PagingDTO.getPagingDTO(pageable, count, PagingDTO.BLOCK_SIZE_M);
        } else if (t.equals(T.DIARY)) {
            // TODO
        } else if (t.equals(T.MAGAZINE)) {
            int count = magazineRepository.countAllByBoardStatusAndBoardContentContainingOrBoardStatusAndBoardSubjectContainingOrderByBoardSeqDesc(true, findString, true, findString);
            return PagingDTO.getPagingDTO(pageable, count, 8);
        	
        } else if (t.equals(T.PROMOTION)) {
            // TODO
        } else if (t.equals(T.REVIEW)) {
            // TODO
        }
        return null;
    }
}
