package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.SocialUserView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
public interface SocialUserViewRepository extends JpaRepository<SocialUserView, Long> {
    Optional<SocialUserView> findByUserId(String userId);
}
