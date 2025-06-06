package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);
}
