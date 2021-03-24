package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUserId(String name);
    List<User> findAll();

    List<User> findAllByUserStatus(boolean status);
    int countByUserStatus(boolean status);

    Optional<User> findByUserIdAndUserStatus(String userId, boolean status);
    Optional<User> findByUserEmailAndUserStatus(String userEmail, boolean status);

}
