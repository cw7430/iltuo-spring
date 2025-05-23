package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.User;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserId(String userId);
    int countByUserId(String userId);
}
