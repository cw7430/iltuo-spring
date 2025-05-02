package kr.co.iltuo.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kr.co.iltuo.entity.auth.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {

    private final Key key;
    private final long accessTokenExpireTime;

    public JwtProvider(@Value("${jwt.secret}")String secretKey, @Value("${jwt.expiration}") long accessTokenExpireTime) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireTime = accessTokenExpireTime;
    }

    // ✅ 1. JWT 생성
    public String generateToken(User user) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenExpireTime);

        return Jwts.builder()
                .setSubject(user.getUserId())
                .claim("userPermissionsCode", user.getUserPermissionsCode())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 2. JWT 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // 여기서 오류나면 예외 발생
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ✅ 3. 클레임 추출
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        return getClaims(token).getSubject(); // subject는 userId
    }

    public String getUserPermissionCodeFromToken(String token) {
        return getClaims(token).get("userPermissionsCode", String.class);
    }

}
