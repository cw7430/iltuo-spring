package kr.co.iltuo.repository.auth;

import kr.co.iltuo.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
