package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.SocialUserView;
import org.springframework.data.jpa.repository.*;

public interface SocialUserViewRepository extends JpaRepository<SocialUserView, Long> {
}
