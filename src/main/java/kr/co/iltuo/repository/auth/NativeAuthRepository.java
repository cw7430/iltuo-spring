package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.NativeAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NativeAuthRepository extends JpaRepository<NativeAuth,Long> {
}
