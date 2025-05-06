package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.SocialAuth;
import org.springframework.data.jpa.repository.*;

public interface SocialAuthRepository extends JpaRepository<SocialAuth, Long> {
}
