package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.NativeUserView;
import org.springframework.data.jpa.repository.*;

public interface NativeUserViewRepository extends JpaRepository<NativeUserView, Long> {
    NativeUserView findByUserId(String userId);
}
