package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserId(String userId);
    int countByUserId(String userId);
    @Query(value = "SELECT COUNT(*) FROM `user` WHERE `user_id` = :userId AND `is_valid` = true", nativeQuery = true)
    int countValidUserByUserId(@Param("userId") String userId);
    @Query(value = "SELECT * FROM `user` WHERE `user_id` = :userId and `is_valid` = false", nativeQuery = true)
    User findCanceledUserByUserId(@Param("userId") String userId);
}
