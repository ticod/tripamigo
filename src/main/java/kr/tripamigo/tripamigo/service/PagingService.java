package kr.tripamigo.tripamigo.service;

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

    public PagingDTO getPlanPaging(Pageable pageable) {
        int count = planRepository.countByStatus(true);
        return PagingDTO.getPagingDTO(pageable, count, PagingDTO.BLOCK_SIZE_M);
    }

    public PagingDTO getInfoPaging(Pageable pageable) {
        int count = infoRepository.countByStatus(true);
        return PagingDTO.getPagingDTO(pageable, count, PagingDTO.BLOCK_SIZE_M);
    }

}
