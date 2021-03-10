package kr.tripamigo.tripamigo.repository;

import kr.tripamigo.tripamigo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends UserRepository, JpaRepository<User, Long> {


}
