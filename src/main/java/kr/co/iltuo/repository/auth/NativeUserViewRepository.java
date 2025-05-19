package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.NativeUserView;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
public interface NativeUserViewRepository extends JpaRepository<NativeUserView, Long> {
    Optional<NativeUserView> findByUserId(String userId);
}
