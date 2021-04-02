package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.dto.BoardType;
import kr.tripamigo.tripamigo.dto.paging.PagingDTO;
import kr.tripamigo.tripamigo.repository.InfoRepository;
import kr.tripamigo.tripamigo.repository.plan.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PagingService {

    private final PlanRepository planRepository;
    private final InfoRepository infoRepository;

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
            // TODO
        } else if (t.equals(T.PROMOTION)) {
            // TODO
        } else if (t.equals(T.REVIEW)) {
            // TODO
        }
        return null;
    }

}
