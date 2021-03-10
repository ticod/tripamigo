package kr.tripamigo.tripamigo.service;

import kr.tripamigo.tripamigo.domain.User;
import kr.tripamigo.tripamigo.dto.UserFormDTO;
import kr.tripamigo.tripamigo.repository.UserJpaRepository;
import kr.tripamigo.tripamigo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private UserJpaRepository userJpaRepository;

    @Autowired
    public UserService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public void join(UserFormDTO userFormDTO) {
        User user = new User();
        userJpaRepository.save(user);
    }

}
