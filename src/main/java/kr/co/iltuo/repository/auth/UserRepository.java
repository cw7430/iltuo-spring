package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.User;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(String userId);
    int countByUserId(String userId);
    int countByUserIdAndIsValidTrue(String userId);
    User findByUserIdAndIsValidFalse(String userId);
}
