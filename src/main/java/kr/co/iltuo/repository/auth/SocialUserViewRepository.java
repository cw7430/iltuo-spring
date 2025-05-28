package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.SocialUserView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface SocialUserViewRepository extends JpaRepository<SocialUserView, Long> {
    Optional<SocialUserView> findByUserId(String userId);
}
