package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.repository.RecommendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
public class RecommendService {

    private final RecommendRepository recommendRepository;

}
