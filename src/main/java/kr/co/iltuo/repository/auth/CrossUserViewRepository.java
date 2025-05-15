package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.CrossUserView;
import org.springframework.data.jpa.repository.*;

public interface CrossUserViewRepository extends JpaRepository<CrossUserView, Long> {
}
